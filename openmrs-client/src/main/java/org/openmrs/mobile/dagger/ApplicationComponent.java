package org.openmrs.mobile.dagger;

import javax.inject.Singleton;

import dagger.Component;
import org.greenrobot.eventbus.EventBus;
import org.openmrs.mobile.data.DatabaseHelper;
import org.openmrs.mobile.net.AuthorizationManager;
import org.openmrs.mobile.net.NetworkManager;
import org.openmrs.mobile.sync.SyncManager;
import org.openmrs.mobile.utilities.NetworkUtils;

@Singleton
@Component(modules = { ApplicationModule.class, DbModule.class, SyncModule.class })
public interface ApplicationComponent {
	SyncManager syncManager();

	NetworkUtils networkUtils();

	AuthorizationManager authorizationManager();

	DatabaseHelper databaseHelper();

	NetworkManager networkManager();

	EventBus eventBus();
}
