package org.openmrs.mobile.data.db.impl;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.db.BaseDbService;
import org.openmrs.mobile.data.db.DbService;
import org.openmrs.mobile.models.Encounter;
import org.openmrs.mobile.models.Encounter_Table;
import org.openmrs.mobile.models.Observation;

import java.util.List;

import javax.inject.Inject;

public class EncounterDbService extends BaseDbService<Encounter> implements DbService<Encounter> {
	@Inject
	public EncounterDbService() { }

	@Override
	protected ModelAdapter<Encounter> getEntityTable() {
		return (Encounter_Table)FlowManager.getInstanceAdapter(Encounter.class);
	}

	public List<Encounter> getByEncounter(Encounter encounter, QueryOptions options, PagingInfo pagingInfo) {
		return null;
	}
}