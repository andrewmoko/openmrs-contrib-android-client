package org.openmrs.mobile.data.impl;

import org.openmrs.mobile.data.BaseMetadataDataService;
import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.db.impl.LocationDbService;
import org.openmrs.mobile.data.rest.impl.LocationRestServiceImpl;
import org.openmrs.mobile.data.rest.retrofit.LocationRestService;
import org.openmrs.mobile.data.rest.retrofit.RestServiceBuilder;
import org.openmrs.mobile.models.Location;
import org.openmrs.mobile.models.Results;
import org.openmrs.mobile.utilities.ApplicationConstants;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class LocationDataService extends BaseMetadataDataService<Location, LocationDbService, LocationRestServiceImpl> {
	@Inject
	public LocationDataService() { }

	public void getLoginLocations(String url, GetCallback<List<Location>> callback) {
		executeMultipleCallback(callback, null, null,
				() -> dbService.getAll(null, null),
				() -> restService.getLoginLocations(url)
		);

	}
}
