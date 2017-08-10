package org.openmrs.mobile.data.sync.impl;

import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.db.impl.ConceptDbService;
import org.openmrs.mobile.data.db.impl.ConceptSetDbService;
import org.openmrs.mobile.data.rest.RestConstants;
import org.openmrs.mobile.data.rest.impl.ConceptRestServiceImpl;
import org.openmrs.mobile.data.sync.AdaptiveSubscriptionProvider;
import org.openmrs.mobile.models.Concept;
import org.openmrs.mobile.models.ConceptSet;
import org.openmrs.mobile.models.PullSubscription;
import org.openmrs.mobile.models.RecordInfo;
import org.openmrs.mobile.utilities.ApplicationConstants;

import java.util.List;

import javax.inject.Inject;

public class DiagnosisConceptSubscriptionProvider extends AdaptiveSubscriptionProvider<Concept,
		ConceptDbService, ConceptRestServiceImpl> {
	@Inject
	protected ConceptSetDbService conceptSetDbService;

	private ConceptSet icpcDiagnosesSet;

	@Inject
	public DiagnosisConceptSubscriptionProvider() { }

	@Override
	public void initialize(PullSubscription subscription) {
		super.initialize(subscription);

		icpcDiagnosesSet = conceptSetDbService.getByUuid(ApplicationConstants.ConceptSets.ICPC_DIAGNOSES, null);
		if (icpcDiagnosesSet == null) {
			Concept concept = getCallValue(restService.getByUuid(ApplicationConstants.ConceptSets.ICPC_DIAGNOSES, null));
			if (concept != null) {
				icpcDiagnosesSet = new ConceptSet();
				icpcDiagnosesSet.setUuid(concept.getUuid());
				icpcDiagnosesSet.setConcept(concept);

				conceptSetDbService.save(icpcDiagnosesSet);
			}
		}
	}

	@Override
	protected long getRecordCountDb() {
		return conceptSetDbService.getSetMemberCount(ApplicationConstants.ConceptSets.ICPC_DIAGNOSES);
	}

	@Override
	protected void saveAllDb(List<Concept> entities) {
		// First save the concepts to the concept table
		super.saveAllDb(entities);

		if (icpcDiagnosesSet != null) {
			// Now save the concepts as set members of the ICPC Diagnoses set
			conceptSetDbService.save(icpcDiagnosesSet, entities);
		}
	}

	@Override
	protected List<Concept> getAllRest() {
		QueryOptions options = new QueryOptions();
		options.setCustomRepresentation(RestConstants.Representations.DIAGNOSIS_CONCEPT);

		return getCallListValue(restService.getSetConcepts(ApplicationConstants.ConceptSets.ICPC_DIAGNOSES, options));
	}

	@Override
	protected List<RecordInfo> getRecordInfoRest() {
		return getCallListValue(restService.getSetConceptRecordInfo(ApplicationConstants.ConceptSets.ICPC_DIAGNOSES));
	}
}
