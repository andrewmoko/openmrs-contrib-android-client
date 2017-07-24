package org.openmrs.mobile.data.rest;

import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.models.BaseOpenmrsEntity;
import org.openmrs.mobile.models.Results;

import retrofit2.Call;

public interface EntityRestService<E extends BaseOpenmrsEntity> extends RestService<E> {
	Call<Results<E>> getByPatient(String patientUuid, QueryOptions options, PagingInfo pagingInfo);
}
