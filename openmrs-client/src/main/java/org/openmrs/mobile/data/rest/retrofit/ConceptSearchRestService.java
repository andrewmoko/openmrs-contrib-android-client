package org.openmrs.mobile.data.rest.retrofit;

import org.openmrs.mobile.data.rest.RestConstants;
import org.openmrs.mobile.models.ConceptSearchResult;
import org.openmrs.mobile.models.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConceptSearchRestService {
	@GET(RestConstants.REST_PATH)
	Call<Results<ConceptSearchResult>> search(@Path(value = "restPath", encoded = true) String restPath,
			@Query("term") String term,
			@Query("limit") Integer limit,
			@Query("startIndex") Integer startIndex);
}
