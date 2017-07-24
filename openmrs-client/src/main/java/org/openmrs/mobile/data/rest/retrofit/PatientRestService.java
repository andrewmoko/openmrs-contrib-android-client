package org.openmrs.mobile.data.rest.retrofit;

import org.openmrs.mobile.data.rest.RestConstants;
import org.openmrs.mobile.models.Patient;
import org.openmrs.mobile.models.Results;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PatientRestService {
	@GET(RestConstants.GET_BY_UUID)
	Call<Patient> getByUuid(@Path(value = "restPath", encoded = true) String restPath,
			@Path("uuid") String uuid,
			@Query("v") String representation,
			@Query("includeAll") Boolean includeAll);

	@POST(RestConstants.CREATE)
	Call<Patient> create(@Path(value = "restPath", encoded = true) String restPath, @Body Patient entity);

	@POST(RestConstants.UPDATE)
	Call<Patient> update(@Path(value = "restPath", encoded = true) String restPath,
			@Path("uuid") String uuid, @Body Patient entity);

	@DELETE(RestConstants.PURGE)
	Call<Patient> purge(@Path(value = "restPath", encoded = true) String restPath,
			@Path("uuid") @Body String uuid);

	@GET(RestConstants.REST_PATH)
	Call<Results<Patient>> getByName(@Path(value = "restPath", encoded = true) String restPath,
			@Query("q") String name,
			@Query("v") String representation,
			@Query("includeAll") Boolean includeAll,
			@Query("limit") Integer limit,
			@Query("startIndex") Integer startIndex);

	@GET(RestConstants.REST_PATH)
	Call<Results<Patient>> getByIdentifier(@Path(value = "restPath", encoded = true) String restPath,
			@Query("identifier") String identifier,
			@Query("v") String representation,
			@Query("includeAll") Boolean includeAll,
			@Query("limit") Integer limit,
			@Query("startIndex") Integer startIndex);

	@GET(RestConstants.REST_PATH)
	Call<Results<Patient>> getByNameOrIdentifier(@Path(value = "restPath", encoded = true) String restPath,
			@Query("q") String name,
			@Query("identifier") String identifier,
			@Query("v") String representation,
			@Query("includeAll") Boolean includeAll,
			@Query("limit") Integer limit,
			@Query("startIndex") Integer startIndex);

	@GET(RestConstants.REST_PATH)
	Call<Results<Patient>> getLastViewed(@Path(value = "restPath", encoded = true) String restPath,
			@Query("lastviewed") String lastviewed,
			@Query("v") String representation,
			@Query("includeAll") Boolean includeAll,
			@Query("limit") Integer limit,
			@Query("startIndex") Integer startIndex);
}
