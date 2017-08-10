package org.openmrs.mobile.models;

import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.Table;

import org.openmrs.mobile.data.db.AppDatabase;

@Table(database = AppDatabase.class)
@ManyToMany(referencedTable = Concept.class)
public class ConceptSet extends BaseOpenmrsObject {
	@ForeignKey(stubbedRelationship = true)
	private Concept concept;

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}
}
