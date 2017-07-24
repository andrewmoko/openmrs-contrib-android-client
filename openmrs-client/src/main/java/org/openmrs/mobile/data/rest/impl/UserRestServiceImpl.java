package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.retrofit.UserRestService;
import org.openmrs.mobile.models.Results;
import org.openmrs.mobile.models.User;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

import retrofit2.Call;

public class UserRestServiceImpl extends BaseRestService<User, UserRestService> {
	@Inject
	public UserRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V1;
	}

	@Override
	protected String getEntityName() {
		return "user";
	}

	public Call<Results<User>> getByUsername(String username, QueryOptions options, PagingInfo pagingInfo) {
		return restService.getByUsername(buildRestRequestPath(), username,
				QueryOptions.getRepresentation(options), QueryOptions.getIncludeInactive(options),
				PagingInfo.getLimit(pagingInfo), PagingInfo.getStartIndex(pagingInfo));
	}
}
