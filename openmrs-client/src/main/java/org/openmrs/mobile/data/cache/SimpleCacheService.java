package org.openmrs.mobile.data.cache;

import android.util.LruCache;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SimpleCacheService implements CacheService {
	private static final int CACHE_SIZE = 1024*10; // 10 MB

	private static SimpleCacheService instance;

	public static synchronized CacheService getInstance() {
		if (instance == null) {
			instance = new SimpleCacheService();
		}

		return instance;
	}

	private LruCache<String, Object> cache;

	@Inject
	public SimpleCacheService() {
		cache = new LruCache<>(CACHE_SIZE);
	}

	@Override
	public Object get(String key) {
		return cache.get(key);
	}

	@Override
	public void set(String key, Object value) {
		cache.put(key, value);
	}

	@Override
	public void clear() {
		cache = new LruCache<>(CACHE_SIZE);
	}
}

