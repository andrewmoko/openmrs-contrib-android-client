package org.openmrs.mobile.activities;

import android.util.Log;

import org.openmrs.mobile.dagger.DaggerDataAccess;
import org.openmrs.mobile.dagger.DataAccess;
import org.openmrs.mobile.data.DataService;
import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.impl.ConceptSearchDataService;
import org.openmrs.mobile.data.impl.ObsDataService;
import org.openmrs.mobile.data.impl.VisitNoteDataService;
import org.openmrs.mobile.models.ConceptSearchResult;
import org.openmrs.mobile.models.Encounter;
import org.openmrs.mobile.models.Observation;
import org.openmrs.mobile.models.VisitNote;

import java.util.ArrayList;
import java.util.List;

public class BaseDiagnosisPresenter {

	private ConceptSearchDataService conceptSearchDataService;
	private ObsDataService obsDataService;
	private VisitNoteDataService visitNoteDataService;
	private int page = 0;
	private int limit = 20;
	private List<String> obsUuids = new ArrayList<>();
	private DataAccess dataAccess;

	public BaseDiagnosisPresenter() {
		dataAccess = DaggerDataAccess.create();

		this.conceptSearchDataService = dataAccess.conceptSearch();
		this.obsDataService = dataAccess.obs();
		this.visitNoteDataService = dataAccess.visitNote();
	}

	public void findConcept(String searchQuery, IBaseDiagnosisFragment base) {
		PagingInfo pagingInfo = new PagingInfo(page, limit);
		conceptSearchDataService.search(searchQuery, pagingInfo, new DataService.GetCallback<List<ConceptSearchResult>>() {

			@Override
			public void onCompleted(List<ConceptSearchResult> entities) {
				if (entities.isEmpty()) {
					ConceptSearchResult nonCodedDiagnosis = new ConceptSearchResult();
					nonCodedDiagnosis.setDisplay(searchQuery);
					nonCodedDiagnosis.setValue("Non-Coded:" + searchQuery);
					entities.add(nonCodedDiagnosis);
				}
				base.getBaseDiagnosisView().setSearchDiagnoses(entities);
			}

			@Override
			public void onError(Throwable t) {
				Log.e("error", t.getLocalizedMessage());
			}
		});
	}

	public void loadObs(Encounter encounter, IBaseDiagnosisFragment base) {
		obsUuids.clear();
		for (Observation obs : encounter.getObs()) {
			getObservation(obs, encounter, base);
		}
	}

	private void getObservation(Observation obs, Encounter encounter, IBaseDiagnosisFragment base) {
		obsDataService
				.getByUuid(obs.getUuid(), QueryOptions.LOAD_RELATED_OBJECTS, new DataService.GetCallback<Observation>() {
					@Override
					public void onCompleted(Observation entity) {
						obsUuids.add(entity.getUuid());
						base.getBaseDiagnosisView().createEncounterDiagnosis(entity, entity.getDisplay(),
								entity.getValueCodedName(), obsUuids.size() == encounter.getObs().size());
					}

					@Override
					public void onError(Throwable t) {
						base.getBaseDiagnosisView().showTabSpinner(false);
					}
				});
	}

	public void saveVisitNote(VisitNote visitNote, IBaseDiagnosisFragment base) {
		visitNoteDataService.save(visitNote, new DataService.GetCallback<VisitNote>() {
			@Override
			public void onCompleted(VisitNote visitNote) {
				base.setEncounterUuid(visitNote.getEncounterId());
			}

			@Override
			public void onError(Throwable t) {
			}
		});
	}
}
