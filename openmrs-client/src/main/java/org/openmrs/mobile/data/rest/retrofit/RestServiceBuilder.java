package org.openmrs.mobile.data.rest.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.openmrs.mobile.application.OpenMRS;
import org.openmrs.mobile.models.Observation;
import org.openmrs.mobile.models.Resource;
import org.openmrs.mobile.utilities.ApplicationConstants;
import org.openmrs.mobile.utilities.ObservationDeserializer;
import org.openmrs.mobile.utilities.ResourceSerializer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

public class RestServiceBuilder {
	protected static final OpenMRS app = OpenMRS.getInstance();
	private static final String REST_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private static Retrofit.Builder builder;
	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
	private static String API_BASE_URL = OpenMRS.getInstance().getServerUrl();

	static {
		applyDefaultBaseUrl();
	}

	private static GsonConverterFactory buildGsonConverter() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson myGson = gsonBuilder
				.excludeFieldsWithoutExposeAnnotation()
				.setDateFormat(REST_DATE_FORMAT)
				.registerTypeHierarchyAdapter(Resource.class, new ResourceSerializer())
				.registerTypeHierarchyAdapter(Observation.class, new ObservationDeserializer())
				.create();

		return GsonConverterFactory.create(myGson);
	}

	public static <S> S createService(@NonNull Class<S> serviceClass, @Nullable String host,
			@NonNull String username, @NonNull String password) {
		checkNotNull(serviceClass);
		checkNotNull(username);
		checkNotNull(password);

		String credentials = username + ":" + password;
		final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
		httpClient.addInterceptor(chain -> {
			Request original = chain.request();

            /* Inject the host
			if (StringUtils.notEmpty(host)) {
                HttpUrl newUrl = original.url().newBuilder()
                        .host(host)
                        .build();
                original = original.newBuilder()
                        .url(newUrl)
                        .build();
            }
            //*/

			// Inject the credentials into the request
			Request.Builder requestBuilder = original.newBuilder()
					.header("Authorization", basic)
					.header("Accept", "application/json")
					.method(original.method(), original.body());

			Request request = requestBuilder.build();
			return chain.proceed(request);
		});

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		httpClient.addInterceptor(logging);

		OkHttpClient client = httpClient.build();

		Retrofit retrofit = builder.client(client).build();

		return retrofit.create(serviceClass);
	}

	public static <S> S createService(Class<S> serviceClass) {
		return createService(serviceClass, app.getServerUrl(), app.getUsername(), app.getPassword());
	}

	public static void applyDefaultBaseUrl() {

		API_BASE_URL = OpenMRS.getInstance().getServerUrl();

		builder = new Retrofit.Builder()
				.baseUrl(API_BASE_URL)
				.addConverterFactory(buildGsonConverter());
	}

	public static void setBaseUrl(String url) {

		API_BASE_URL = url;

		builder = new Retrofit.Builder()
				.baseUrl(API_BASE_URL)
				.addConverterFactory(buildGsonConverter());
	}

	public static void setloginUrl(String url) {
		API_BASE_URL = url + ApplicationConstants.API.REST_ENDPOINT_V1;
		builder = new Retrofit.Builder()
				.baseUrl(API_BASE_URL)
				.addConverterFactory(buildGsonConverter());
	}
}
