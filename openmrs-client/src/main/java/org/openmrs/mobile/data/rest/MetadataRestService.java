package org.openmrs.mobile.data.rest;

import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.models.BaseOpenmrsMetadata;
import org.openmrs.mobile.models.Results;

import retrofit2.Call;

public interface MetadataRestService<E extends BaseOpenmrsMetadata> extends RestService<E> {
	Call<Results<E>> getByNameFragment(String name, QueryOptions options, PagingInfo pagingInfo);
}
