package org.openmrs.mobile.data.impl;

import android.support.annotation.NonNull;

import org.openmrs.mobile.data.BaseDataService;
import org.openmrs.mobile.data.DataService;
import org.openmrs.mobile.data.db.impl.VisitPhotoDbService;
import org.openmrs.mobile.data.rest.impl.VisitPhotoRestServiceImpl;
import org.openmrs.mobile.models.VisitPhoto;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public class VisitPhotoDataService
		extends BaseDataService<VisitPhoto, VisitPhotoDbService, VisitPhotoRestServiceImpl>
		implements DataService<VisitPhoto> {
	@Inject
	public VisitPhotoDataService() { }

	public void uploadPhoto(VisitPhoto visitPhoto, @NonNull GetCallback<VisitPhoto> callback) {
		executeSingleCallback(callback, null,
				() -> dbService.save(visitPhoto),
				() -> restService.uploadPhoto(visitPhoto));
	}

	public void downloadPhoto(String obsUuid, String view, @NonNull GetCallback<VisitPhoto> callback) {
		executeSingleCallback(callback, null,
				() -> dbService.getByUuid(obsUuid, null),
				() -> restService.downloadPhoto(obsUuid, view),
				(ResponseBody body) -> {
					try {
						VisitPhoto photo = new VisitPhoto();
						photo.setDownloadedImage(body.bytes());
						return photo;
					} catch (IOException ex) {
						return null;
					}
				},
				(e) -> dbService.save(e)
		);	}
}
