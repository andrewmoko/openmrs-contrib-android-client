package org.openmrs.mobile.data.rest.retrofit;

import org.openmrs.mobile.models.VisitNote;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VisitNoteRestService {
	@FormUrlEncoded
	@POST("{restPath}")
	Call<VisitNote> save(@Path(value = "restPath", encoded = true) String restPath,
			@FieldMap Map<String, String> params);
}
