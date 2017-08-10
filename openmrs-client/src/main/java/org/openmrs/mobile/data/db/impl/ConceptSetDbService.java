package org.openmrs.mobile.data.db.impl;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.openmrs.mobile.data.db.BaseDbService;
import org.openmrs.mobile.data.db.DbService;
import org.openmrs.mobile.models.Concept;
import org.openmrs.mobile.models.ConceptSet;
import org.openmrs.mobile.models.ConceptSet_Concept;
import org.openmrs.mobile.models.ConceptSet_Concept_Table;
import org.openmrs.mobile.models.ConceptSet_Table;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.raizlabs.android.dbflow.sql.language.Method.count;

public class ConceptSetDbService extends BaseDbService<ConceptSet> implements DbService<ConceptSet> {
	@Inject
	public ConceptSetDbService() { }

	@Override
	protected ModelAdapter<ConceptSet> getEntityTable() {
		return (ConceptSet_Table)FlowManager.getInstanceAdapter(ConceptSet.class);
	}

	protected ConceptSet_Concept_Table getConceptJoinTable() {
		return (ConceptSet_Concept_Table)FlowManager.getInstanceAdapter(ConceptSet_Concept.class);
	}

	public long getSetMemberCount(String setUuid) {
		checkNotNull(setUuid);

		ConceptSet_Concept_Table table = getConceptJoinTable();
		return SQLite.select(count(table.getProperty("concept")))
				.from(ConceptSet_Concept.class)
				.where(ConceptSet_Concept_Table.conceptSet_uuid.eq(setUuid))
				.count();
	}

	public void save(@NonNull ConceptSet set, @NonNull List<Concept> setMembers) {
		checkNotNull(set);
		checkNotNull(setMembers);

		// First save the set
		super.save(set);

		// Clear all current join table records
		ConceptSet_Concept_Table table = getConceptJoinTable();
		SQLite.delete(ConceptSet_Concept.class).execute();

		// Next, create all the join table records
		List<ConceptSet_Concept> records = new ArrayList<>(setMembers.size());
		for (Concept concept : setMembers) {
			ConceptSet_Concept record = new ConceptSet_Concept();
			record.setConceptSet(set);
			record.setConcept(concept);

			records.add(record);
		}

		// Now save the set member concepts
		table.saveAll(records);
	}
}
