package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.retrofit.ConceptSearchRestService;
import org.openmrs.mobile.models.ConceptSearchResult;
import org.openmrs.mobile.models.Results;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

import retrofit2.Call;

public class ConceptSearchRestServiceImpl extends BaseRestService<ConceptSearchResult, ConceptSearchRestService> {
	@Inject
	public ConceptSearchRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V2 + "/custom/";
	}

	@Override
	protected String getEntityName() {
		return "diagnoses";
	}

	public Call<Results<ConceptSearchResult>> search(String term, PagingInfo pagingInfo) {
		return restService.search(buildRestRequestPath(), term, PagingInfo.getLimit(pagingInfo),
				PagingInfo.getStartIndex(pagingInfo));
	}
}
