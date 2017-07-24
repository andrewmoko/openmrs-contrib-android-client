package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.RestConstants;
import org.openmrs.mobile.data.rest.retrofit.ObsRestService;
import org.openmrs.mobile.models.Observation;
import org.openmrs.mobile.models.Results;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

import retrofit2.Call;

public class ObsRestServiceImpl extends BaseRestService<Observation, ObsRestService> {
	public static final String VISIT_DOCUMENT_UUID = "7cac8397-53cd-4f00-a6fe-028e8d743f8e,42ed45fd-f3f6-44b6-bfc2-8bde1bb41e00";

	@Inject
	public ObsRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V1;
	}

	@Override
	protected String getEntityName() {
		return "obs";
	}

	public Call<Results<Observation>> getVisitDocumentsObsByPatientAndConceptList(String patientUuid, QueryOptions options) {
		return restService.getVisitDocumentsObsByPatientAndConceptList(buildRestRequestPath(), patientUuid,
				VISIT_DOCUMENT_UUID, RestConstants.Representations.FULL);
	}
}
