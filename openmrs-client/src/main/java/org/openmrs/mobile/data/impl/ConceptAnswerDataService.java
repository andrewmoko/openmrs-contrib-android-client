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
package org.openmrs.mobile.data.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.openmrs.mobile.data.BaseDataService;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.db.impl.ConceptAnswerDbService;
import org.openmrs.mobile.data.rest.impl.ConceptAnswerRestServiceImpl;
import org.openmrs.mobile.models.ConceptAnswer;

import java.util.List;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class ConceptAnswerDataService
		extends BaseDataService<ConceptAnswer, ConceptAnswerDbService, ConceptAnswerRestServiceImpl> {
	@Inject
	public ConceptAnswerDataService() { }

	public void getByConceptUuid(@NonNull String conceptUuid, @Nullable QueryOptions options,
			@NonNull GetCallback<List<ConceptAnswer>> callback) {
		checkNotNull(conceptUuid);
		checkNotNull(callback);

		executeMultipleCallback(callback, options, null,
				() -> dbService.getByConceptUuid(conceptUuid, options),
				() -> restService.getByConceptUuid(conceptUuid, options));
	}
}
