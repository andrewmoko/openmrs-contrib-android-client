package org.openmrs.mobile.data.sync;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Join;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.openmrs.mobile.data.db.DbService;
import org.openmrs.mobile.data.rest.RestService;
import org.openmrs.mobile.models.BaseOpenmrsAuditableObject;
import org.openmrs.mobile.models.PullSubscription;
import org.openmrs.mobile.models.RecordInfo;
import org.openmrs.mobile.models.RecordInfo_Table;
import org.openmrs.mobile.models.Results;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public abstract class AdaptiveSubscriptionProvider<E extends BaseOpenmrsAuditableObject,
		DS extends DbService<E>, RS extends RestService<E>> extends BaseSubscriptionProvider {
	@Inject
	protected DS dbService;

	@Inject
	protected DbService<RecordInfo> recordInfoDbService;

	@Inject
	protected RS restService;

	private Class<E> entityClass;

	@Override
	public void pull(PullSubscription subscription) {
		long recordCount = getRecordCountDb();

		if (recordCount == 0) {
			// If table is empty then do a table pull
			pullTable(subscription);
		} else {
			pullIncremental(subscription);
		}
	}

	protected void pullTable(PullSubscription subscription) {
		// Get all records via rest
		List<E> table = getAllRest();

		// Filter the list for records updated since the last sync
		List<E> updated = new ArrayList<>();
		if (subscription.getLastSync() != null) {
			for (E record : table) {
				if (record.getDateChanged().compareTo(subscription.getLastSync()) > 0) {
					updated.add(record);
				}
			}
		} else {
			updated = table;
		}

		// Update/Create local records
		saveAllDb(updated);
	}

	protected void pullIncremental(PullSubscription subscription) {
		// Get the record info (UUID, DateUpdated) for each record via REST
		List<RecordInfo> tableInfo = getRecordInfoRest();

		List<String> updates;
		List<String> inserts;

		try {
			// Insert record info into temp table
			recordInfoDbService.saveAll(tableInfo);

			// Delete local records that are not in record info
			deleteIncremental();

			// Calculate local records to update and create
			updates = calculateIncrementalUpdates(subscription.getLastSync());
			inserts = calculateIncrementalInserts();
		} finally {
			// Ensure that the record info table is cleared
			recordInfoDbService.deleteAll();
		}

		// If update+new count is greater than max incremental update then do table pull
		if (subscription.getMaximumIncrementalCount() != null &&
				(updates.size() + inserts.size()) > subscription.getMaximumIncrementalCount()) {
			pullTable(subscription);
		} else {
			// Else pull each record via rest and save to the local db
			if (updates != null && updates.size() > 0) {
				processIncremental(updates);
			}

			if (inserts != null && inserts.size() > 0) {
				processIncremental(inserts);
			}
		}
	}

	protected List<String> calculateIncrementalUpdates(Date since) {
		Property entityUuidProperty = getModelTable(getEntityClass()).getProperty("uuid");

		return SQLite.select(entityUuidProperty)
				.from(getEntityClass())
				.innerJoin(RecordInfo.class)
				.on(entityUuidProperty.withTable().eq(RecordInfo_Table.uuid.withTable()))
				.where(RecordInfo_Table.dateChanged.greaterThan(since))
				.queryCustomList(String.class);
	}

	protected List<String> calculateIncrementalInserts() {
		Property entityUuidProperty = getModelTable(getEntityClass()).getProperty("uuid");

		return SQLite.select(RecordInfo_Table.uuid)
				.from(RecordInfo.class)
				.leftOuterJoin(getEntityClass())
				.on(RecordInfo_Table.uuid.withTable().eq(entityUuidProperty.withTable()))
				.where(entityUuidProperty.withTable().isNull())
				.queryCustomList(String.class);
	}

	protected void deleteIncremental() {
		SQLite.delete(getEntityClass()).as("E")
				.join(RecordInfo.class, Join.JoinType.LEFT_OUTER).as("R")
				.on(
						getModelTable(getEntityClass()).getProperty("uuid").withTable(NameAlias.of("E"))
								.eq(RecordInfo_Table.uuid.withTable(NameAlias.of("R"))))
				.where(RecordInfo_Table.uuid.withTable(NameAlias.of("R")).isNull())
				.execute();
	}

	protected void processIncremental(List<String> records) {
		List<E> entities = new ArrayList<>(records.size());

		// Get all required entities via rest
		for (String uuid : records) {
			entities.add(getByUuidRest(uuid));
		}

		// Save the entities to the db
		saveAllDb(entities);
	}

	protected long getRecordCountDb() {
		return dbService.getCount(null);
	}

	protected List<E> getAllRest() {
		return getCallListValue(restService.getAll(null, null));
	}

	protected void saveAllDb(List<E> entities) {
		dbService.saveAll(entities);
	}

	protected E getByUuidRest(String uuid) {
		return getCallValue(restService.getByUuid(uuid, null));
	}

	protected List<RecordInfo> getRecordInfoRest() {
		return getCallListValue(restService.getRecordInfo(null));
	}

	protected <T> List<T> getCallListValue(Call<Results<T>> call) {
		if (call == null) {
			return null;
		}

		Response<Results<T>> response;
		try {
			response = call.execute();
		} catch (Exception ex) {
			response = null;
		}

		List<T> result = null;
		if (response != null) {
			Results<T> temp = response.body();

			if (temp != null) {
				result = temp.getResults();
			}
		}

		return result;
	}

	protected <T> T getCallValue(Call<T> call) {
		if (call == null) {
			return null;
		}

		Response<T> response;
		try {
			response = call.execute();
		} catch (Exception ex) {
			response = null;
		}

		T result = null;
		if (response != null) {
			result = response.body();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	protected <T> ModelAdapter<T> getModelTable(Class<T> cls) {
		return (ModelAdapter<T>)FlowManager.getInstanceAdapter(cls);
	}

	/**
	 * Gets a usable instance of the actual class of the generic type E defined by the implementing sub-class.
	 * @return The class object for the entity.
	 */
	@SuppressWarnings("unchecked")
	protected Class<E> getEntityClass() {
		if (entityClass == null) {
			ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();

			entityClass = (Class<E>)parameterizedType.getActualTypeArguments()[0];
		}

		return entityClass;
	}
}
