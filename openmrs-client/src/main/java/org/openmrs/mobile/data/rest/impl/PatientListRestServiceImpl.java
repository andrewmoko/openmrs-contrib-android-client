package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.rest.BaseMetadataRestService;
import org.openmrs.mobile.data.rest.retrofit.PatientListRestService;
import org.openmrs.mobile.models.PatientList;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

public class PatientListRestServiceImpl extends BaseMetadataRestService<PatientList, PatientListRestService> {
	@Inject
	public PatientListRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V2;
	}

	@Override
	protected String getEntityName() {
		return "patientlist/list";
	}

}
