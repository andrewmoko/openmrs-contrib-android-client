package org.openmrs.mobile.dagger;

import org.openmrs.mobile.data.cache.CacheService;
import org.openmrs.mobile.data.cache.SimpleCacheService;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {
	@Provides
	static CacheService provideCacheService() {
		return new SimpleCacheService();
	}
}
