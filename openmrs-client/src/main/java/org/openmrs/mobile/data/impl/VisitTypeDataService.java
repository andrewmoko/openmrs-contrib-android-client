package org.openmrs.mobile.data.impl;

import org.openmrs.mobile.data.BaseMetadataDataService;
import org.openmrs.mobile.data.MetadataDataService;
import org.openmrs.mobile.data.db.impl.VisitTypeDbService;
import org.openmrs.mobile.data.rest.impl.VisitTypeRestServiceImpl;
import org.openmrs.mobile.models.VisitType;

import javax.inject.Inject;

public class VisitTypeDataService extends BaseMetadataDataService<VisitType, VisitTypeDbService, VisitTypeRestServiceImpl>
		implements MetadataDataService<VisitType> {
	@Inject
	public VisitTypeDataService() { }
}
