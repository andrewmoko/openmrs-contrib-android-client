package org.openmrs.mobile.data.rest.impl;

import org.openmrs.mobile.data.rest.BaseRestService;
import org.openmrs.mobile.data.rest.retrofit.VisitPhotoRestService;
import org.openmrs.mobile.models.VisitPhoto;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class VisitPhotoRestServiceImpl extends BaseRestService<VisitPhoto, VisitPhotoRestService> {
	@Inject
	public VisitPhotoRestServiceImpl() { }

	@Override
	protected String getRestPath() {
		return ApplicationConstants.API.REST_ENDPOINT_V2 + "custom";
	}

	@Override
	protected String getEntityName() {
		return "photos";
	}

	public Call<VisitPhoto> uploadPhoto(VisitPhoto visitPhoto) {
		RequestBody patient =
				RequestBody.create(MediaType.parse("text/plain"), visitPhoto.getPatient().getUuid());
		RequestBody visit = RequestBody.create(MediaType.parse("text/plain"), visitPhoto.getVisit().getUuid());
		RequestBody provider =
				RequestBody.create(MediaType.parse("text/plain"), visitPhoto.getProvider().getUuid());
		RequestBody fileCaption = RequestBody.create(MediaType.parse("text/plain"), visitPhoto.getFileCaption());

		return restService.uploadVisitPhoto(buildRestRequestPath(), patient, visit,
				provider, fileCaption, visitPhoto.getRequestImage());
	}

	public Call<ResponseBody> downloadPhoto(String obsUuid, String view) {
		return restService.downloadVisitPhoto(buildRestRequestPath(), obsUuid, view);
	}
}
