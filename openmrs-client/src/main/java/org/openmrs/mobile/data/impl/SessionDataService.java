package org.openmrs.mobile.data.impl;

import org.openmrs.mobile.data.BaseMetadataDataService;
import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.db.impl.SessionDbService;
import org.openmrs.mobile.data.rest.impl.SessionRestServiceImpl;
import org.openmrs.mobile.data.rest.retrofit.RestServiceBuilder;
import org.openmrs.mobile.data.rest.retrofit.SessionRestService;
import org.openmrs.mobile.models.Results;
import org.openmrs.mobile.models.Session;
import org.openmrs.mobile.utilities.ApplicationConstants;

import javax.inject.Inject;

import retrofit2.Call;

public class SessionDataService extends BaseMetadataDataService<Session, SessionDbService, SessionRestServiceImpl> {
	@Inject
	public SessionDataService() { }

	public void getSession(String serverURl, String username, String password, GetCallback<Session> callback) {
		executeSingleCallback(callback, null,
				() -> null,
				() -> restService.getSession(serverURl, username, password));
	}
}
