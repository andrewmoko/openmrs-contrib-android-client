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

package org.openmrs.mobile.activities.visit;

import android.graphics.Bitmap;
import android.widget.TextView;

import org.openmrs.mobile.activities.IBaseDiagnosisView;
import org.openmrs.mobile.activities.BasePresenterContract;
import org.openmrs.mobile.activities.BaseView;
import org.openmrs.mobile.data.DataService;
import org.openmrs.mobile.models.Concept;
import org.openmrs.mobile.models.Observation;
import org.openmrs.mobile.models.Visit;
import org.openmrs.mobile.models.VisitAttributeType;
import org.openmrs.mobile.models.VisitPhoto;
import org.openmrs.mobile.models.VisitPredefinedTask;
import org.openmrs.mobile.models.VisitTask;
import org.openmrs.mobile.utilities.ToastUtil;

import java.util.List;

public interface VisitContract {
	interface ViewVisitDetailsMain extends BaseView<VisitDetailsMainPresenter> {
	}

	interface VisitTasksView extends ViewVisitDetailsMain {
		void showToast(String message, ToastUtil.ToastType toastType);

		void setOpenVisitTasks(List<VisitTask> visitTaskList);

		void setClosedVisitTasks(List<VisitTask> visitTaskList);

		void setPredefinedTasks(List<VisitPredefinedTask> predefinedTasks);

		void setSelectedVisitTask(VisitTask visitTask);

		void setUnSelectedVisitTask(VisitTask visitTask);

		void refresh();

		void setVisit(Visit visit);

		void clearTextField();

		void showTabSpinner(boolean visibility);

	}

	interface VisitDetailsView extends ViewVisitDetailsMain, IBaseDiagnosisView {
		void showToast(String message, ToastUtil.ToastType toastType);

		void setVisit(Visit visit);

		void setPatientUUID(String uuid);

		void setVisitUUID(String uuid);

		void setProviderUUID(String uuid);

		void setVisitStopDate(String visitStopDate);

		void setConcept(Concept concept);

		void setAttributeTypes(List<VisitAttributeType> visitAttributeTypes);

		void showTabSpinner(boolean visibility);

	}

	interface VisitPhotoView extends ViewVisitDetailsMain {
		void updateVisitImageMetadata(List<VisitPhoto> visitPhotos);

		void deleteImage(VisitPhoto visitPhoto);

		void refresh();

		void showNoVisitPhoto();

		String formatVisitImageDescription(String description, String uploadedOn, String uploadedBy);

		void showTabSpinner(boolean visibility);
	}

	/*
	* Presenters
	*/
	interface VisitDetailsMainPresenter extends BasePresenterContract {

	}

	interface VisitTasksPresenter extends VisitDetailsMainPresenter {
		void getPredefinedTasks();

		void getVisitTasks();

		void addVisitTasks(VisitTask visitTasks);

		void updateVisitTask(VisitTask visitTask);

		void createVisitTasksObject(String visitTask);

		void getVisit();
	}

	interface VisitPhotoPresenter extends VisitDetailsMainPresenter {
		boolean isLoading();

		void setLoading(boolean loading);

		void uploadImage();

		VisitPhoto getVisitPhoto();

		void deleteImage(VisitPhoto visitPhoto);
	}

	interface VisitDetailsPresenter extends VisitDetailsMainPresenter {
		void getVisit();

		void getConcept(String name);

		void getPatientUUID();

		void getVisitUUID();

		void getProviderUUID();

		void getVisitStopDate();

		void getConceptAnswer(String uuid, String searchValue, TextView textView);

	}
}
