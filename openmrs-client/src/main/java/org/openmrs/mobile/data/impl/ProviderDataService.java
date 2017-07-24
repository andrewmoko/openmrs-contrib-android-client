package org.openmrs.mobile.data.impl;

import org.openmrs.mobile.data.BaseMetadataDataService;
import org.openmrs.mobile.data.db.impl.ProviderDbService;
import org.openmrs.mobile.data.rest.impl.ProviderRestServiceImpl;
import org.openmrs.mobile.models.Provider;

import javax.inject.Inject;

public class ProviderDataService extends BaseMetadataDataService<Provider, ProviderDbService, ProviderRestServiceImpl> {
	@Inject
	public ProviderDataService() { }
}
