package org.openmrs.mobile.data.rest.retrofit;

import org.openmrs.mobile.data.DataService;
import org.openmrs.mobile.data.rest.RestConstants;
import org.openmrs.mobile.models.Session;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SessionRestService {
	@GET(RestConstants.REST_PATH)
	Call<Session> getSession(@Path(value = "restPath", encoded = true) String restPath,
			DataService.GetCallback<Session> sessionGetCallback);

	@GET("session")
	Call<Session> getSession();
}
