package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.retrofit.PersonAttributeTypeRestService;
import org.openmrs.mobile.models.PersonAttributeType;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

public class PersonAttributeTypeRestServiceImpl extends BaseRestService<PersonAttributeType, PersonAttributeTypeRestService> {
	@Inject
	public PersonAttributeTypeRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V1;
	}

	@Override
	protected String getEntityName() {
		return "personattributetype";
	}
}
