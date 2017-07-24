package org.openmrs.mobile.data.sync;

import org.openmrs.mobile.models.SyncLog;

public interface SyncProvider {
	void sync(SyncLog record);
}
