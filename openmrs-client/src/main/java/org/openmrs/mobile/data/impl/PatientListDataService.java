package org.openmrs.mobile.data.impl;

import org.openmrs.mobile.data.BaseMetadataDataService;
import org.openmrs.mobile.data.MetadataDataService;
import org.openmrs.mobile.data.db.impl.PatientListDbService;
import org.openmrs.mobile.data.rest.impl.PatientListRestServiceImpl;
import org.openmrs.mobile.models.PatientList;

import javax.inject.Inject;

public class PatientListDataService
		extends BaseMetadataDataService<PatientList, PatientListDbService, PatientListRestServiceImpl>
		implements MetadataDataService<PatientList> {
	@Inject
	public PatientListDataService() { }
}
