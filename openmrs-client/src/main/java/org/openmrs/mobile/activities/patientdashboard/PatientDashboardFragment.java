/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.mobile.activities.patientdashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import org.openmrs.mobile.R;
import org.openmrs.mobile.activities.ACBaseFragment;
import org.openmrs.mobile.activities.addeditpatient.AddEditPatientActivity;
import org.openmrs.mobile.activities.addeditvisit.AddEditVisitActivity;
import org.openmrs.mobile.application.OpenMRS;
import org.openmrs.mobile.models.Location;
import org.openmrs.mobile.models.Patient;
import org.openmrs.mobile.models.PersonAttribute;
import org.openmrs.mobile.models.Visit;
import org.openmrs.mobile.utilities.ApplicationConstants;
import org.openmrs.mobile.utilities.FontsUtil;
import org.openmrs.mobile.utilities.StringUtils;

import java.util.HashMap;
import java.util.List;

import static org.openmrs.mobile.utilities.ApplicationConstants.BundleKeys.LOCATION_UUID_BUNDLE;
import static org.openmrs.mobile.utilities.ApplicationConstants.BundleKeys.PATIENT_UUID_BUNDLE;
import static org.openmrs.mobile.utilities.ApplicationConstants.entityName.COUNTY;
import static org.openmrs.mobile.utilities.ApplicationConstants.entityName.SUBCOUNTY;
import static org.openmrs.mobile.utilities.ApplicationConstants.entityName.TELEPHONE;

public class PatientDashboardFragment extends ACBaseFragment<PatientDashboardContract.Presenter>
		implements PatientDashboardContract.View {

	private View fragmentView, borderLine;
	private FloatingActionButton startVisitButton, editPatient;
	private Patient patient;
	private OpenMRS instance = OpenMRS.getInstance();
	private SharedPreferences sharedPreferences = instance.getOpenMRSSharedPreferences();
	private Intent intent;
	private NestedScrollView scrollView;
	private View patientContactInfo;
	private String providerUuid;
	private Location location;
	private ProgressBar savingProgressBar;
	private RelativeLayout dashboardProgressBar, dashboardScreen;
	private TextView noVisitNoteLabel;
	private TextView patientAddress, patientPhonenumber;
	private String patientUuid;

	public static PatientDashboardFragment newInstance() {
		return new PatientDashboardFragment();
	}

	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.fragment_patient_dashboard, container, false);
		patientUuid = getActivity().getIntent().getStringExtra(ApplicationConstants.BundleKeys.PATIENT_UUID_BUNDLE);
		initViewFields();
		initializeListeners(startVisitButton, editPatient);
		//We start by fetching by location, required for creating encounters
		String locationUuid = "";
		if (!instance.getLocation().equalsIgnoreCase(null)) {
			locationUuid = instance.getLocation();
		}

		mPresenter.fetchLocation(locationUuid);
		mPresenter.fetchPatientData(patientUuid);
		FontsUtil.setFont((ViewGroup)this.getActivity().findViewById(android.R.id.content));
		return fragmentView;
	}

	private void initializeListeners(FloatingActionButton... params) {
		for (FloatingActionButton patientActionButtons : params) {
			patientActionButtons.setOnClickListener(
					view -> startSelectedPatientDashboardActivity(patientActionButtons.getId()));
		}
	}

	private void startSelectedPatientDashboardActivity(int selectedId) {
		switch (selectedId) {
			case R.id.start_visit:
				intent = new Intent(getContext(), AddEditVisitActivity.class);
				intent.putExtra(ApplicationConstants.BundleKeys.PATIENT_UUID_BUNDLE, sharedPreferences.getString
						(ApplicationConstants.BundleKeys.PATIENT_UUID_BUNDLE, ApplicationConstants.EMPTY_STRING));
				startActivity(intent);
				break;
			case R.id.edit_Patient:
				intent = new Intent(getContext(), AddEditPatientActivity.class);
				intent.putExtra(ApplicationConstants.BundleKeys.PATIENT_UUID_BUNDLE, instance.getPatientUuid());
				startActivity(intent);

				break;
		}
	}

	private void initViewFields() {
		startVisitButton = (FloatingActionButton)fragmentView.findViewById(R.id.start_visit);
		editPatient = (FloatingActionButton)fragmentView.findViewById(R.id.edit_Patient);
		scrollView = (NestedScrollView)fragmentView.findViewById(R.id.scrollView);

		borderLine = fragmentView.findViewById(R.id.borderLine);
		savingProgressBar = (ProgressBar)fragmentView.findViewById(R.id.savingProgressBar);
		dashboardScreen = (RelativeLayout)fragmentView.findViewById(R.id.dashboardScreen);
		dashboardProgressBar = (RelativeLayout)fragmentView.findViewById(R.id.dashboardProgressBar);
		noVisitNoteLabel = (TextView)fragmentView.findViewById(R.id.noVisitNoteLabel);

		//Contact address header
		patientContactInfo = fragmentView.findViewById(R.id.container_patient_address_info);
		patientAddress = (TextView)patientContactInfo.findViewById(R.id.patientAddress);
		patientPhonenumber = (TextView)patientContactInfo.findViewById(R.id.patientPhonenumber);
	}

	@Override
	public void updateContactCard(Patient patient) {
		showPageSpinner(true);
		this.patient = patient;
		setPatientUuid(patient);
		String county, subCounty, address, phone;
		county = subCounty = address = phone = "";

		for (PersonAttribute personAttribute : patient.getPerson().getAttributes()) {
			if (personAttribute.getDisplay() != null) {
				String displayName = personAttribute.getDisplay().replaceAll("\\s+", "");
				if (displayName.toLowerCase().startsWith(SUBCOUNTY)) {
					subCounty = displayName.split("=")[1];
				} else if (displayName.toLowerCase().startsWith(COUNTY)) {
					county = displayName.split("=")[1];
				} else if (displayName.toLowerCase().startsWith(TELEPHONE)) {
					phone = displayName.split("=")[1];
				}
			}
		}

		if (!subCounty.equalsIgnoreCase("")) {
			address += subCounty;
		}

		if (!address.equalsIgnoreCase("")) {
			address += ", " + county;
		} else {
			address += county;
		}

		patientAddress.setText(address);
		patientPhonenumber.setText(phone);
		patientContactInfo.setVisibility(View.VISIBLE);
	}

	@Override
	public void updateActiveVisitCard(List<Visit> visits) {
		showPageSpinner(true);
		for (Visit visit : visits) {
			if (!StringUtils.notNull(visit.getStopDatetime())) {
				startVisitButton.setVisibility(View.GONE);
				setVisitUuid(visit);
				break;
			}
		}

		final Rect scrollBounds = new Rect();
		scrollView.getHitRect(scrollBounds);

		PatientDashboardActivity patientDashboardActivity = (PatientDashboardActivity)getActivity();

		scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
			@Override
			public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

				if (patientContactInfo != null) {

					if (patientContactInfo.getLocalVisibleRect(scrollBounds)) {
						if (!patientContactInfo.getLocalVisibleRect(scrollBounds)
								|| scrollBounds.height() < patientContactInfo.getHeight()) {
							//contact info is now being showed parcially, do nothing
						} else {
							//contact info is now being showed fully, hide shadow, show line
							borderLine.setVisibility(View.VISIBLE);
							patientDashboardActivity.updateHeaderShadowLine(false);
						}
					} else {
						//contact info is not being shown, show shadow
						borderLine.setVisibility(View.GONE);
						patientDashboardActivity.updateHeaderShadowLine(true);
					}
				}

			}
		});

		HashMap<String, String> uuidsHashmap = new HashMap<>();

		uuidsHashmap.put(PATIENT_UUID_BUNDLE, patient.getUuid());
		uuidsHashmap.put(LOCATION_UUID_BUNDLE, location.getUuid());

		RecyclerView pastVisitsRecyclerView = (RecyclerView)fragmentView.findViewById(R.id.pastVisits);
		pastVisitsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		VisitsRecyclerAdapter visitsRecyclerAdapter = new VisitsRecyclerAdapter(
				pastVisitsRecyclerView,
				visits, getActivity(), uuidsHashmap
		);
		pastVisitsRecyclerView.setAdapter(visitsRecyclerAdapter);
		visitsRecyclerAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				//visitsRecyclerAdapter.notifyItemRemoved();
				// Load more here
				//ConsoleLogger.dump("Loading more");
				//visitsRecyclerAdapter.notifyDataSetChanged();
				//visitsRecyclerAdapter.setLoaded();
				//visitsRecyclerAdapter.notifyDataSetChanged();
			}
		});
		showPageSpinner(false);
	}

	@Override
	public Patient getPatient() {
		return patient;
	}

	public void setPatientUuid(Patient patient) {
		SharedPreferences.Editor editor = instance.getOpenMRSSharedPreferences().edit();
		editor.putString(ApplicationConstants.BundleKeys.PATIENT_UUID_BUNDLE, patient.getPerson().getUuid());
		editor.commit();
	}

	public void setVisitUuid(Visit visit) {
		SharedPreferences.Editor editor = instance.getOpenMRSSharedPreferences().edit();
		editor.putString(ApplicationConstants.BundleKeys.VISIT_UUID_BUNDLE, visit.getUuid());
		editor.commit();
	}

	@Override
	public void setProviderUuid(String providerUuid) {
		this.providerUuid = providerUuid;
		if (StringUtils.isBlank(providerUuid))
			return;
		SharedPreferences.Editor editor = instance.getOpenMRSSharedPreferences().edit();
		editor.putString(ApplicationConstants.BundleKeys.PROVIDER_UUID_BUNDLE, providerUuid);
		editor.commit();
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public void upDateProgressBar(boolean show) {
		//savingProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	@Override
	public void showPageSpinner(boolean visibility) {
		if (visibility) {
			dashboardProgressBar.setVisibility(View.VISIBLE);
			dashboardScreen.setVisibility(View.GONE);
		} else {
			dashboardProgressBar.setVisibility(View.GONE);
			dashboardScreen.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void showNoVisits(boolean visibility) {
		if (visibility) {
			noVisitNoteLabel.setVisibility(View.VISIBLE);
		} else {
			noVisitNoteLabel.setVisibility(View.GONE);
		}
	}

}