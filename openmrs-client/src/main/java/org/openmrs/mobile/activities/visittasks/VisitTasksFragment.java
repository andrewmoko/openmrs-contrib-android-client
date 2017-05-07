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

package org.openmrs.mobile.activities.visittasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.openmrs.mobile.R;
import org.openmrs.mobile.activities.ACBaseFragment;
import org.openmrs.mobile.models.VisitTasks;
import org.openmrs.mobile.utilities.FontsUtil;
import org.openmrs.mobile.utilities.ToastUtil;

import java.util.List;

public class VisitTasksFragment extends ACBaseFragment<VisitTasksContract.Presenter> implements VisitTasksContract.View {

	private View mRootView;
	private RecyclerView viewTasksRecyclerView;
	private LinearLayoutManager layoutManager;
	private RecyclerView visitTasksRecyclerViewAdapter;

	public static VisitTasksFragment newInstance() {
		return new VisitTasksFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_visit_tasks, container, false);
		resolveViews(mRootView);

		//Adding the Recycler view
		layoutManager = new LinearLayoutManager(this.getActivity());
		visitTasksRecyclerViewAdapter = (RecyclerView)mRootView.findViewById(R.id.visitTasksRecyclerView);
		visitTasksRecyclerViewAdapter.setLayoutManager(layoutManager);

		// Font config
		FontsUtil.setFont((ViewGroup)this.getActivity().findViewById(android.R.id.content));
		mPresenter.getPredefinedTasks();
		mPresenter.getVisitTasks();

		return mRootView;
	}

	private void resolveViews(View v) {
		viewTasksRecyclerView = (RecyclerView)v.findViewById(R.id.visitTasksRecyclerView);
	}

	@Override
	public void showToast(String message, ToastUtil.ToastType toastType) {
		ToastUtil.showShortToast(getContext(), toastType, message);
	}

	@Override
	public void getVisitTasks(List<VisitTasks> visitTasksList) {
		VisitTasksRecyclerViewAdapter adapter = new VisitTasksRecyclerViewAdapter(this.getActivity(), visitTasksList, this);
		visitTasksRecyclerViewAdapter.setAdapter(adapter);
		//visitTasksRecyclerViewAdapter.addOnScrollListener(recyclerViewOnScrollListener);
	}
}
