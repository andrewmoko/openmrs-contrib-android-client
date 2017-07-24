package org.openmrs.mobile.data.sync;

import org.openmrs.mobile.data.db.DbService;
import org.openmrs.mobile.models.Patient;
import org.openmrs.mobile.models.SyncLog;

import javax.inject.Inject;

public class PushSyncProvider implements SyncProvider {
	@Inject
	DbService<Patient> patientDbService;

	@Inject
	public PushSyncProvider() { }

	@Override
	public void sync(SyncLog record) {

	}
}

