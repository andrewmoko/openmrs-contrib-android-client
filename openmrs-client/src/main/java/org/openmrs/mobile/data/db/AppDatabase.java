package org.openmrs.mobile.data.db;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import org.openmrs.mobile.data.sync.impl.ConceptAnswerSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.ConceptClassSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.ConceptSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.DiagnosisConceptSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.EncounterTypeSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.LocationSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.PatientIdentifierTypeSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.PatientListSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.PersonAttributeTypeSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.VisitAttributeTypeSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.VisitPredefinedTaskSubscriptionProvider;
import org.openmrs.mobile.data.sync.impl.VisitTypeSubscriptionProvider;
import org.openmrs.mobile.models.EntitySyncInfo;
import org.openmrs.mobile.models.Patient;
import org.openmrs.mobile.models.PullSubscription;
import org.openmrs.mobile.utilities.TimeConstants;

import java.util.ArrayList;
import java.util.List;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
	public static final String NAME = "BandaHealth"; // Will get added with a .db extension

	public static final int VERSION = 4;

	@Migration(version = 2, database = AppDatabase.class, priority = 10)
	public static class RecreateDbMigration extends BaseMigration {
		@Override
		public void migrate(@NonNull DatabaseWrapper database) {
			for (Class<?> cls : FlowManager.getDatabase(AppDatabase.class).getModelClasses()) {
				ModelAdapter modelAdapter = FlowManager.getModelAdapter(cls);
				database.execSQL("DROP TABLE IF EXISTS " + modelAdapter.getTableName());

				database.execSQL(modelAdapter.getCreationQuery());
			}
		}
	}

	@Migration(version = 2, database = AppDatabase.class, priority = 0)
	public static class CreateSubscriptionsMigration extends BaseMigration {
		@Override
		public void migrate(@NonNull DatabaseWrapper database) {
			Initialization.initializePullSubscriptions(database);
		}
	}

	@Migration(version = 0, database = AppDatabase.class)
	public static class Initialization extends BaseMigration {
		private final static Integer INT_SECONDS_PER_DAY = (int)TimeConstants.SECONDS_PER_DAY;
		private final static Integer MAX_INCREMENTAL_COUNT = 25;

		public static void initializePullSubscriptions(@NonNull DatabaseWrapper database) {
			List<PullSubscription> pullSubscriptions = new ArrayList<>();

			pullSubscriptions.add(
					newPullSub(ConceptClassSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(DiagnosisConceptSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(EncounterTypeSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(LocationSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(PatientIdentifierTypeSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(PatientListSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(PersonAttributeTypeSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(VisitAttributeTypeSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(VisitPredefinedTaskSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(VisitTypeSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY)
			);
			pullSubscriptions.add(
					newPullSub(ConceptAnswerSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY));
			pullSubscriptions.add(
					newPullSub(ConceptSubscriptionProvider.class.getSimpleName(), MAX_INCREMENTAL_COUNT,
							INT_SECONDS_PER_DAY));

			FlowManager.getModelAdapter(PullSubscription.class).saveAll(pullSubscriptions, database);
		}

		@Override
		public void migrate(@NonNull DatabaseWrapper database) {
			initializePullSubscriptions(database);
		}

		private static PullSubscription newPullSub(String cls, Integer maxIncCount, Integer minInterval) {
			PullSubscription sub = new PullSubscription();
			sub.setForceSyncAfterPush(false);
			sub.setSubscriptionClass(cls);
			sub.setSubscriptionKey(null);
			sub.setMaximumIncrementalCount(maxIncCount);
			sub.setMinimumInterval(minInterval);

			return sub;
		}
	}

	@Migration(version = 3, database = AppDatabase.class)
	public static class AddEntitySyncTables extends BaseMigration {
		@Override
		public void migrate(@NonNull DatabaseWrapper database) {
			ModelAdapter modelAdapter = FlowManager.getModelAdapter(EntitySyncInfo.class);
			database.execSQL("DROP TABLE IF EXISTS " + modelAdapter.getTableName());

			database.execSQL(modelAdapter.getCreationQuery());
		}
	}

	@Migration(version = 4, database = AppDatabase.class)
	public static class AlterPatientTable extends BaseMigration {
		@Override
		public void migrate(@NonNull DatabaseWrapper database) {
			// add lastdatetimeseen column
			ModelAdapter modelAdapter = FlowManager.getModelAdapter(Patient.class);
			database.execSQL("DROP TABLE IF EXISTS " + modelAdapter.getTableName());

			database.execSQL(modelAdapter.getCreationQuery());
		}
	}
}