/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.mobile.api;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.activeandroid.query.Select;

import org.openmrs.mobile.api.retrofit.VisitApi;
import org.openmrs.mobile.dao.PatientDAO;
import org.openmrs.mobile.dao.VisitDAO;
import org.openmrs.mobile.listeners.retrofit.DefaultResponseCallbackListener;
import org.openmrs.mobile.listeners.retrofit.StartVisitResponseListenerCallback;
import org.openmrs.mobile.models.Encounter;
import org.openmrs.mobile.models.EncounterType;
import org.openmrs.mobile.models.EncounterCreate;
import org.openmrs.mobile.utilities.NetworkUtils;
import org.openmrs.mobile.utilities.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;

public class EncounterService extends IntentService {
	
	private final RestApi apiService = RestServiceBuilder.createService(RestApi.class);
	
	public EncounterService() {
		super("Save Encounter");
	}
	
	public void addEncounter(final EncounterCreate encounterCreate, @Nullable DefaultResponseCallbackListener callbackListener) {
		
		if (NetworkUtils.isOnline()) {
			new VisitDAO().getActiveVisitByPatientId(encounterCreate.getPatientId())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(visit -> {
						if (visit != null) {
							encounterCreate.setVisit(visit.getUuid());
							if (callbackListener != null) {
								syncEncounter(encounterCreate, callbackListener);
							} else {
								syncEncounter(encounterCreate);
							}
						} else {
							
							startNewVisitForEncounter(encounterCreate);
						}
					});
		} else
			ToastUtil.error("No internet connection. Form data is saved locally " +
			                "and will sync when internet connection is restored. ");
	}
	
	public void addEncounter(final EncounterCreate encounterCreate) {
		addEncounter(encounterCreate, null);
	}
	
	private void startNewVisitForEncounter(final EncounterCreate encounterCreate, @Nullable final DefaultResponseCallbackListener callbackListener) {
		new VisitApi().startVisit(new PatientDAO().findPatientByUUID(encounterCreate.getPatient()),
				new StartVisitResponseListenerCallback() {
					@Override
					public void onStartVisitResponse(long id) {
						new VisitDAO().getVisitByID(id)
								.observeOn(AndroidSchedulers.mainThread())
								.subscribe(visit -> {
									encounterCreate.setVisit(visit.getUuid());
									if (callbackListener != null) {
										syncEncounter(encounterCreate, callbackListener);
									} else {
										syncEncounter(encounterCreate);
									}
								});
					}
					
					@Override
					public void onResponse() {
						// This method is intentionally empty
					}
					
					@Override
					public void onErrorResponse(String errorMessage) {
						ToastUtil.error(errorMessage);
					}
				});
	}
	
	public void startNewVisitForEncounter(final EncounterCreate encounterCreate) {
		startNewVisitForEncounter(encounterCreate, null);
	}
	
	public void syncEncounter(final EncounterCreate encounterCreate, @Nullable final DefaultResponseCallbackListener callbackListener) {
		
		if (NetworkUtils.isOnline()) {
			
			encounterCreate.pullObslist();
			Call<Encounter> call = apiService.createEncounter(encounterCreate);
			call.enqueue(new Callback<Encounter>() {
				@Override
				public void onResponse(Call<Encounter> call, Response<Encounter> response) {
					if (response.isSuccessful()) {
						Encounter encounter = response.body();
						linkvisit(encounterCreate.getPatientId(), encounterCreate.getFormname(), encounter, encounterCreate);
						encounterCreate.setSynced(true);
						encounterCreate.save();
						new VisitApi().syncLastVitals(encounterCreate.getPatient());
						if (callbackListener != null) {
							callbackListener.onResponse();
						}
					} else {
						if (callbackListener != null) {
							callbackListener.onErrorResponse(response.errorBody().toString());
						}
					}
				}
				
				@Override
				public void onFailure(Call<Encounter> call, Throwable t) {
					if (callbackListener != null) {
						callbackListener.onErrorResponse(t.getLocalizedMessage());
					}
				}
			});
			
		} else {
			ToastUtil.error("Sync is off. Turn on sync to save form data.");
		}
		
	}
	
	public void syncEncounter(final EncounterCreate encounterCreate) {
		syncEncounter(encounterCreate, null);
	}
	
	private void linkvisit(Long patientid, String formname, Encounter encounter, EncounterCreate encounterCreate) {
		VisitDAO visitDAO = new VisitDAO();
		visitDAO.getVisitByUuid(encounter.getVisit().getUuid())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(visit -> {
					encounter.setEncounterType(new EncounterType(formname));
					for (int i = 0; i < encounterCreate.getObservations().size(); i++) {
						encounter.getObservations().get(i).setDisplayValue
								(encounterCreate.getObservations().get(i).getValue());
					}
					List<Encounter> encounterList = visit.getEncounters();
					encounterList.add(encounter);
					visitDAO.saveOrUpdate(visit, patientid)
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(id ->
									ToastUtil.success(formname + " data saved successfully"));
				});
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		if (NetworkUtils.isOnline()) {
			
			List<EncounterCreate> encountercreatelist = new Select()
					.from(EncounterCreate.class)
					.execute();
			
			for (final EncounterCreate encounterCreate : encountercreatelist) {
				if (!encounterCreate.getSynced() &&
				    new PatientDAO().findPatientByID(Long.toString(encounterCreate.getPatientId())).isSynced()) {
					new VisitDAO().getActiveVisitByPatientId(encounterCreate.getPatientId())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(visit -> {
								if (visit != null) {
									encounterCreate.setVisit(visit.getUuid());
									syncEncounter(encounterCreate);
									
								} else {
									startNewVisitForEncounter(encounterCreate);
								}
							});
				}
			}
			
			
		} else {
			ToastUtil.error("No internet connection. Form data is saved locally " +
			                "and will sync when internet connection is restored. ");
		}
	}
	
}