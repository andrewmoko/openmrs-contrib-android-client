package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.RestService;
import org.openmrs.mobile.data.rest.retrofit.PatientRestService;
import org.openmrs.mobile.models.Patient;
import org.openmrs.mobile.models.Results;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

import retrofit2.Call;

public class PatientRestServiceImpl extends BaseRestService<Patient, PatientRestService> implements RestService<Patient> {
	@Inject
	public PatientRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V1;
	}

	@Override
	protected String getEntityName() {
		return "patient";
	}

	public Call<Results<Patient>> getByName(String name, QueryOptions options, PagingInfo pagingInfo) {
		return restService.getByName(buildRestRequestPath(), name, QueryOptions.getRepresentation(options),
				QueryOptions.getIncludeInactive(options), PagingInfo.getLimit(pagingInfo),
				PagingInfo.getStartIndex(pagingInfo));
	}

	public Call<Results<Patient>> getByIdentifier(String id, QueryOptions options, PagingInfo pagingInfo) {
		return restService.getByIdentifier(buildRestRequestPath(), id,
				QueryOptions.getRepresentation(options), QueryOptions.getIncludeInactive(options),
				PagingInfo.getLimit(pagingInfo), PagingInfo.getStartIndex(pagingInfo));
	}

	public Call<Results<Patient>> getByNameOrIdentifier(String query, QueryOptions options, PagingInfo pagingInfo) {
		return restService.getByNameOrIdentifier(buildRestRequestPath(), query, query,
				QueryOptions.getRepresentation(options), QueryOptions.getIncludeInactive(options),
				PagingInfo.getLimit(pagingInfo), PagingInfo.getStartIndex(pagingInfo));
	}

	public Call<Results<Patient>> getLastViewed(String lastViewed, QueryOptions options, PagingInfo pagingInfo) {
		return restService.getLastViewed(buildRestRequestPath(), lastViewed,
				QueryOptions.getRepresentation(options), QueryOptions.getIncludeInactive(options),
				PagingInfo.getLimit(pagingInfo), PagingInfo.getStartIndex(pagingInfo));
	}
}

