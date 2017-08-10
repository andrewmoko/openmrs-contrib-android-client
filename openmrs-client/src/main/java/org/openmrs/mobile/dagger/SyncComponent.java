package org.openmrs.mobile.dagger;

import org.openmrs.mobile.data.sync.PushSyncProvider;
import org.openmrs.mobile.sync.AndroidSyncService;
import org.openmrs.mobile.sync.SyncAdapter;

import org.openmrs.mobile.data.sync.SyncService;


import javax.inject.Singleton;


import dagger.Component;


@Singleton
@Component(modules = SyncModule.class)
public interface SyncComponent {
	void inject(SyncAdapter syncAdapter);
	void inject(SyncService syncService);
	void inject(PushSyncProvider pushSyncProvider);
	void inject(AndroidSyncService androidSyncService);
}
