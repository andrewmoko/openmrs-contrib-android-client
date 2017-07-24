package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.retrofit.EncounterRestService;
import org.openmrs.mobile.models.Encounter;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

public class EncounterRestServiceImpl extends BaseRestService<Encounter, EncounterRestService> {
	@Inject
	public EncounterRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V1;
	}

	@Override
	protected String getEntityName() {
		return "encounter";
	}
}
