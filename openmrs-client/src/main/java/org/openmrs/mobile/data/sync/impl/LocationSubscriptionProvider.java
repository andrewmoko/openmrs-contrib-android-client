package org.openmrs.mobile.data.sync.impl;

import org.openmrs.mobile.data.db.impl.LocationDbService;
import org.openmrs.mobile.data.rest.impl.LocationRestServiceImpl;
import org.openmrs.mobile.data.rest.retrofit.LocationRestService;
import org.openmrs.mobile.data.sync.AdaptiveSubscriptionProvider;
import org.openmrs.mobile.models.Location;

import javax.inject.Inject;

public class LocationSubscriptionProvider extends AdaptiveSubscriptionProvider<Location, LocationDbService,
		LocationRestServiceImpl> {
	@Inject
	public LocationSubscriptionProvider() { }
}
