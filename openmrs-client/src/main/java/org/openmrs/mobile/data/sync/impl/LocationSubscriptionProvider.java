package org.openmrs.mobile.data.sync.impl;

import org.openmrs.mobile.data.sync.AdaptiveSubscriptionProvider;
import org.openmrs.mobile.models.Location;

import javax.inject.Inject;

public class LocationSubscriptionProvider extends AdaptiveSubscriptionProvider<Location> {
	@Inject
	public LocationSubscriptionProvider() { }
}
