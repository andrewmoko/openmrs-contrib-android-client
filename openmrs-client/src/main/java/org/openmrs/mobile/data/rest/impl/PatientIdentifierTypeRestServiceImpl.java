package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.rest.BaseMetadataRestService;
import org.openmrs.mobile.data.rest.retrofit.PatientIdentifierTypeRestService;
import org.openmrs.mobile.models.PatientIdentifierType;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

public class PatientIdentifierTypeRestServiceImpl
		extends BaseMetadataRestService<PatientIdentifierType, PatientIdentifierTypeRestService> {
	@Inject
	public PatientIdentifierTypeRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V1;
	}

	@Override
	protected String getEntityName() {
		return "patientidentifiertype";
	}
}
