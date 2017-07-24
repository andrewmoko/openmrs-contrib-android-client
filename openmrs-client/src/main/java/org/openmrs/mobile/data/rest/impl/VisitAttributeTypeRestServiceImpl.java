package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.retrofit.VisitAttributeTypeRestService;
import org.openmrs.mobile.models.VisitAttributeType;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

public class VisitAttributeTypeRestServiceImpl extends BaseRestService<VisitAttributeType, VisitAttributeTypeRestService> {
	@Inject
	public VisitAttributeTypeRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V1;
	}

	@Override
	protected String getEntityName() {
		return "visitattributetype";
	}


}
