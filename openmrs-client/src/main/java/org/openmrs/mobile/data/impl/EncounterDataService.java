package org.openmrs.mobile.data.impl;

import org.openmrs.mobile.data.BaseDataService;
import org.openmrs.mobile.data.db.impl.EncounterDbService;
import org.openmrs.mobile.data.rest.impl.EncounterRestServiceImpl;
import org.openmrs.mobile.models.Encounter;

import javax.inject.Inject;

public class EncounterDataService extends BaseDataService<Encounter, EncounterDbService, EncounterRestServiceImpl> {
	@Inject
	public EncounterDataService() {	}
}
