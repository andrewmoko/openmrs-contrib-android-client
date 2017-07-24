package org.openmrs.mobile.data.rest.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.retrofit.ConceptAnswerRestService;
import org.openmrs.mobile.models.ConceptAnswer;
import org.openmrs.mobile.models.Results;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

import retrofit2.Call;

public class ConceptAnswerRestServiceImpl extends BaseRestService<ConceptAnswer, ConceptAnswerRestService> {
	@Inject
	public ConceptAnswerRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V2 + "custom";
	}

	@Override
	protected String getEntityName() {
		return "conceptanswer";
	}

	public Call<Results<ConceptAnswer>> getByConceptUuid(@NonNull String conceptUuid, @Nullable QueryOptions options) {
		return restService.getByConceptUuid(buildRestRequestPath(), conceptUuid);
	}
}
