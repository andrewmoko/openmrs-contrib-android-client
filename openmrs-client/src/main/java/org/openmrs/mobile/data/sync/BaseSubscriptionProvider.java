package org.openmrs.mobile.data.sync;

import org.openmrs.mobile.models.PullSubscription;

public abstract class BaseSubscriptionProvider implements SubscriptionProvider {
	@Override
	public void initialize(PullSubscription subscription) {

	}

	@Override
	public abstract void pull(PullSubscription subscription);
}
