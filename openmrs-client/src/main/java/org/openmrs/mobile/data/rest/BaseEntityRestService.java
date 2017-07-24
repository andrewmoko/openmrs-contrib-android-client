package org.openmrs.mobile.data.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.models.BaseOpenmrsEntity;
import org.openmrs.mobile.models.Results;

import java.lang.reflect.Method;

import retrofit2.Call;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BaseEntityRestService<E extends BaseOpenmrsEntity, RS> extends BaseRestService<E, RS>
		implements EntityRestService<E> {
	public static final String GET_BY_PATIENT_METHOD_NAME = "getByPatient";

	private Method getByPatientMethod;

	protected BaseEntityRestService() {
		super();

		initializeRestMethods();
	}

	@Override
	public Call<Results<E>> getByPatient(@NonNull String patientUuid, @Nullable QueryOptions options,
			@Nullable PagingInfo pagingInfo) {
		checkNotNull(patientUuid);

		if (getByPatientMethod == null) {
			Log.w("Rest Service", "Attempt to call 'getByPatient' REST method but REST service method could not be found for "
					+ "entity '" + entityClass.getName() + "'");

			return null;
		}

		Call<Results<E>> call = null;

		try {
			Object result = getByPatientMethod.invoke(restService, buildRestRequestPath(), patientUuid,
					QueryOptions.getRepresentation(options), QueryOptions.getIncludeInactive(options),
					PagingInfo.getLimit(pagingInfo), PagingInfo.getStartIndex(pagingInfo));

			if (result != null) {
				call = (Call<Results<E>>)result;
			}
		} catch (Exception nex) {
			Log.e("Rest Service", "Exception executing REST getByPatient method", nex);

			call = null;
		}

		return call;
	}

	private void initializeRestMethods() {
		Method[] methods = restService.getClass().getMethods();

		getByPatientMethod = findMethod(methods, GET_BY_PATIENT_METHOD_NAME);
	}
}

