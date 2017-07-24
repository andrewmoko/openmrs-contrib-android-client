package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.rest.BaseMetadataRestService;
import org.openmrs.mobile.data.rest.retrofit.VisitTypeRestService;
import org.openmrs.mobile.models.VisitType;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

public class VisitTypeRestServiceImpl extends BaseMetadataRestService<VisitType, VisitTypeRestService> {
	@Inject
	public VisitTypeRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V1;
	}

	@Override
	protected String getEntityName() {
		return "visittype";
	}


}
