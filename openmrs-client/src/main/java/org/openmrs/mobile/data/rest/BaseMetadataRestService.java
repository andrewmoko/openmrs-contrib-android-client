package org.openmrs.mobile.data.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.models.BaseOpenmrsMetadata;
import org.openmrs.mobile.models.Results;

import java.lang.reflect.Method;

import retrofit2.Call;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BaseMetadataRestService<E extends BaseOpenmrsMetadata, RS> extends BaseRestService<E, RS>
		implements MetadataRestService<E> {
	public static final String GET_BY_NAME_FRAGMENT_METHOD_NAME = "getByNameFragment";

	private Method getByNameFragmentMethod;

	protected BaseMetadataRestService() {
		super();

		initializeRestMethods();
	}

	@Override
	public Call<Results<E>> getByNameFragment(@NonNull String name, @Nullable QueryOptions options,
			@Nullable PagingInfo pagingInfo) {
		checkNotNull(name);

		if (getByNameFragmentMethod == null) {
			Log.w("Rest Service", "Attempt to call 'getByNameFragment' REST method but REST service method could not be "
					+ "found for entity '" + entityClass.getName() + "'");

			return null;
		}

		Call<Results<E>> call = null;

		try {
			Object result = getByNameFragmentMethod.invoke(restService, buildRestRequestPath(), name,
					QueryOptions.getRepresentation(options), QueryOptions.getIncludeInactive(options),
					PagingInfo.getLimit(pagingInfo), PagingInfo.getStartIndex(pagingInfo));

			if (result != null) {
				call = (Call<Results<E>>)result;
			}
		} catch (Exception nex) {
			Log.e("Rest Service", "Exception executing REST getByNameFragment method", nex);

			call = null;
		}

		return call;
	}

	private void initializeRestMethods() {
		Method[] methods = restService.getClass().getMethods();

		getByNameFragmentMethod = findMethod(methods, GET_BY_NAME_FRAGMENT_METHOD_NAME);
	}
}
