package org.openmrs.mobile.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.openmrs.mobile.models.BaseOpenmrsObject;

import java.util.List;

/**
 * Represents classes that provide data services for {@link BaseOpenmrsObject} objects.
 * @param <E> The entity class
 */
public interface DataService<E extends BaseOpenmrsObject> {
	/**
	 * Gets a single entity with the specified UUID.
	 * @param uuid     The entity UUID
	 * @param callback
	 */
	void getByUuid(@NonNull String uuid, @Nullable QueryOptions options, @NonNull GetCallback<E> callback);

	/**
	 * Gets all entities.
	 * @param options    The {@link QueryOptions} settings to use for this operation
	 * @param pagingInfo The paging information or null to exclude paging
	 * @param callback
	 */
	void getAll(@Nullable QueryOptions options, @Nullable PagingInfo pagingInfo,
			@NonNull GetCallback<List<E>> callback);

	/**
	 * Saves a newly created entity.
	 * @param entity   The new entity to save
	 * @param callback
	 */
	void create(@NonNull E entity, @NonNull GetCallback<E> callback);

	/**
	 * Saves an updated entity.
	 * @param entity   The entity to save
	 * @param callback
	 */
	void update(@NonNull E entity, @NonNull GetCallback<E> callback);

	/**
	 * Completely deletes the entity.
	 * @param entity   The entity to purge
	 * @param callback
	 */
	void purge(@NonNull E entity, @NonNull VoidCallback callback);

	/**
	 * Callback interface for operations with no return value.
	 */
	interface VoidCallback {
		/**
		 * Called if the operation completes successfully.
		 */
		void onCompleted();

		/**
		 * Called if the operation fails.
		 * @param t The exception information
		 */
		void onError(Throwable t);
	}

	/**
	 * Callback interface for data service methods
	 * @param <TResult> The result of the function execution
	 */
	interface GetCallback<TResult> {
		/**
		 * Called if the operation completes successfully.
		 * @param result The returned result
		 */
		void onCompleted(TResult result);

		/**
		 * Called if the operations fails.
		 * @param t The exception information
		 */
		void onError(Throwable t);
	}
}

