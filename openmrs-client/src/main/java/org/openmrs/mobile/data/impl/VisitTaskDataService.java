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

import org.openmrs.mobile.data.BaseEntityDataService;
import org.openmrs.mobile.data.EntityDataService;
import org.openmrs.mobile.data.PagingInfo;
import org.openmrs.mobile.data.QueryOptions;
import org.openmrs.mobile.data.db.impl.VisitTaskDbService;
import org.openmrs.mobile.data.rest.impl.VisitTaskRestServiceImpl;
import org.openmrs.mobile.models.VisitTask;

import java.util.List;

import javax.inject.Inject;

public class VisitTaskDataService
		extends BaseEntityDataService<VisitTask, VisitTaskDbService, VisitTaskRestServiceImpl>
		implements EntityDataService<VisitTask> {
	@Inject
	public VisitTaskDataService() { }

	public void getAll(String status, String patient_uuid, String visit_uuid,
			QueryOptions options, PagingInfo pagingInfo, GetCallback<List<VisitTask>> callback) {
		executeMultipleCallback(callback, options, pagingInfo,
				() -> dbService.getAll(status, patient_uuid, visit_uuid, options, pagingInfo),
				() -> restService.getAll(status, patient_uuid, visit_uuid, options, pagingInfo)
		);
	}

}
