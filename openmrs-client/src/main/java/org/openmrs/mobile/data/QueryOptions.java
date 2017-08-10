package org.openmrs.mobile.data;

import android.support.annotation.Nullable;

import org.openmrs.mobile.data.rest.RestConstants;
import org.openmrs.mobile.utilities.StringUtils;

public class QueryOptions {
	private static final boolean DEFAULT_INCLUDE_INACTIVE = false;
	private static final boolean DEFAULT_LOAD_RELATED_OBJECTS = false;

	public static final QueryOptions DEFAULT = new QueryOptions(false, false);
	public static final QueryOptions INCLUDE_INACTIVE = new QueryOptions(true, false);
	public static final QueryOptions LOAD_RELATED_OBJECTS = new QueryOptions(false, true);

	private String cacheKey;
	private boolean includeInactive = DEFAULT_INCLUDE_INACTIVE;
	private boolean loadRelatedObjects = DEFAULT_LOAD_RELATED_OBJECTS;
	private String customRepresentation;

	public QueryOptions() {
		this(DEFAULT_INCLUDE_INACTIVE, DEFAULT_LOAD_RELATED_OBJECTS);
	}

	public QueryOptions(boolean includeInactive, boolean loadRelatedObjects) {
		this.includeInactive = includeInactive;
		this.loadRelatedObjects = loadRelatedObjects;
	}

	public QueryOptions(String cacheKey, boolean loadRelatedObjects) {
		this.cacheKey = cacheKey;
		this.loadRelatedObjects = loadRelatedObjects;
	}

	public static String getCacheKey(@Nullable QueryOptions options) {
		return options == null ? null : options.getCacheKey();
	}

	public static boolean getIncludeInactive(@Nullable QueryOptions options) {
		return options == null ? DEFAULT_INCLUDE_INACTIVE : options.includeInactive();
	}

	public static boolean getLoadRelatedObjects(@Nullable QueryOptions options) {
		return options == null ? DEFAULT_LOAD_RELATED_OBJECTS : options.loadRelatedObjects();
	}

	public static String getRepresentation(@Nullable QueryOptions options) {
		String result;
		if (getLoadRelatedObjects(options)) {
			result = RestConstants.Representations.FULL;
		} else {
			if (options != null && StringUtils.notEmpty(options.getCustomRepresentation())) {
				result = options.getCustomRepresentation();
			} else {
				result = RestConstants.Representations.DEFAULT;
			}
		}

		return result;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public boolean includeInactive() {
		return includeInactive;
	}

	public void setIncludeInactive(boolean includeInactive) {
		this.includeInactive = includeInactive;
	}

	public boolean loadRelatedObjects() {
		return loadRelatedObjects;
	}

	public void setLoadRelatedObjects(boolean loadRelatedObjects) {
		this.loadRelatedObjects = loadRelatedObjects;
	}

	public String getCustomRepresentation() {
		return customRepresentation;
	}

	public void setCustomRepresentation(String customRepresentation) {
		this.customRepresentation = customRepresentation;
	}
}
