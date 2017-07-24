package org.openmrs.mobile.data.impl;

import org.openmrs.mobile.data.BaseDataService;
import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.db.impl.PatientDbService;
import org.openmrs.mobile.data.rest.impl.PatientRestServiceImpl;
import org.openmrs.mobile.models.Patient;

import java.util.List;

import javax.inject.Inject;

public class PatientDataService extends BaseDataService<Patient, PatientDbService, PatientRestServiceImpl> {
	@Inject
	public PatientDataService() { }

	public void getByName(String name, QueryOptions options, PagingInfo pagingInfo, GetCallback<List<Patient>> callback) {
		executeMultipleCallback(callback, options, pagingInfo,
				() -> dbService.getByName(name, options, pagingInfo),
				() -> restService.getByName(name, options, pagingInfo)
		);
	}

	public void getByIdentifier(String id, QueryOptions options, PagingInfo pagingInfo,
			GetCallback<List<Patient>> callback) {
		executeMultipleCallback(callback, options, pagingInfo,
				() -> dbService.getByIdentifier(id, options, pagingInfo),
				() -> restService.getByIdentifier(id, options, pagingInfo)
		);
	}

	public void getByNameOrIdentifier(String query, QueryOptions options, PagingInfo pagingInfo,
			GetCallback<List<Patient>> callback) {
		executeMultipleCallback(callback, options, pagingInfo,
				() -> dbService.getByNameOrIdentifier(query, query, options, pagingInfo),
				() -> restService.getByNameOrIdentifier(query, options, pagingInfo)
		);
	}

	public void getLastViewed(String lastViewed, QueryOptions options, PagingInfo pagingInfo,
			GetCallback<List<Patient>> callback) {
		executeMultipleCallback(callback, options, pagingInfo,
				() -> dbService.getLastViewed(options, pagingInfo),
				() -> restService.getLastViewed(lastViewed, options, pagingInfo)
		);
	}
}
