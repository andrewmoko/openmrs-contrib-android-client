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

import org.openmrs.mobile.data.BaseMetadataDataService;
import org.openmrs.mobile.data.MetadataDataService;
import org.openmrs.mobile.data.db.impl.VisitPredefinedTaskDbService;
import org.openmrs.mobile.data.rest.impl.VisitPredefinedTaskRestServiceImpl;
import org.openmrs.mobile.models.VisitPredefinedTask;

import javax.inject.Inject;

public class VisitPredefinedTaskDataService
		extends BaseMetadataDataService<VisitPredefinedTask, VisitPredefinedTaskDbService,
		VisitPredefinedTaskRestServiceImpl>
		implements MetadataDataService<VisitPredefinedTask> {
	@Inject
	public VisitPredefinedTaskDataService() { }
}
