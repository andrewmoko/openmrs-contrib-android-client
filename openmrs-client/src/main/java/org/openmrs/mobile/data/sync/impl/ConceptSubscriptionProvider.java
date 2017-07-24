package org.openmrs.mobile.data.sync.impl;

import org.openmrs.mobile.data.sync.AdaptiveSubscriptionProvider;
import org.openmrs.mobile.models.Concept;
import org.openmrs.mobile.models.PullSubscription;

import javax.inject.Inject;

public class ConceptSubscriptionProvider extends AdaptiveSubscriptionProvider<Concept> {
	@Inject
	public ConceptSubscriptionProvider() { }

	@Override
	public void initialize(PullSubscription subscription) {
		super.initialize(subscription);
	}
}
