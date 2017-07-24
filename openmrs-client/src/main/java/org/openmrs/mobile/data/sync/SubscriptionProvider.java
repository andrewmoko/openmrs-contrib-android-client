package org.openmrs.mobile.data.sync;

import org.openmrs.mobile.models.PullSubscription;

public interface SubscriptionProvider {
	void initialize(PullSubscription subscription);
	void pull(PullSubscription subscription);
}

