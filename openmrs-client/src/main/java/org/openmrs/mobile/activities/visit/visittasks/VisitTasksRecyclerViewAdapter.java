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

package org.openmrs.mobile.activities.visit.visittasks;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import org.openmrs.mobile.R;
import org.openmrs.mobile.activities.visit.VisitContract;
import org.openmrs.mobile.models.Visit;
import org.openmrs.mobile.models.VisitTask;
import org.openmrs.mobile.models.VisitTaskStatus;

import java.util.List;

import static org.openmrs.mobile.R.color.black;

class VisitTasksRecyclerViewAdapter extends RecyclerView.Adapter<VisitTasksRecyclerViewAdapter.FetchedVisitTasksHolder> {

	private Activity mContext;
	private List<VisitTask> visitTaskList;
	private VisitContract.VisitTasksView view;
	private Visit visit;

	VisitTasksRecyclerViewAdapter(Activity context,
			List<VisitTask> visitTaskList, Visit visit, VisitContract.VisitTasksView view) {
		this.mContext = context;
		this.visitTaskList = visitTaskList;
		this.view = view;
		this.visit = visit;
	}

	@Override
	public FetchedVisitTasksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.visit_tasks_row, parent, false);
		return new FetchedVisitTasksHolder(itemView);
	}

	@Override
	public void onBindViewHolder(FetchedVisitTasksHolder holder, int position) {
		final VisitTask visitTask = visitTaskList.get(position);

		try {
			holder.visitTasks.setText(visitTask.getName());
			if (visitTask.getStatus() == VisitTaskStatus.CLOSED) {
				holder.visitTasks.setChecked(true);
				holder.visitTasks.setPaintFlags(holder.visitTasks.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			}
			if (visit.getStopDatetime() != null) {
				holder.visitTasks.setEnabled(false);
				holder.visitTasks.setTextColor(black);
			} else {
				holder.visitTasks.setEnabled(true);
			}

		} catch (Exception e) {
			holder.visitTasks.setText("");
			holder.visitTasks.setEnabled(false);
		}

		holder.visitTasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton visitTaskCheckBox, boolean isChecked) {
				if (visitTaskCheckBox.isChecked()) {
					visitTaskCheckBox.setPaintFlags(visitTaskCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
					view.setSelectedVisitTask(visitTask);
				} else {
					visitTaskCheckBox.setPaintFlags(visitTaskCheckBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
					view.setUnSelectedVisitTask(visitTask);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return visitTaskList == null ? 0 : visitTaskList.size();
	}

	class FetchedVisitTasksHolder extends RecyclerView.ViewHolder {
		private LinearLayout mRowLayout;
		private CheckBox visitTasks;

		FetchedVisitTasksHolder(View itemView) {
			super(itemView);
			mRowLayout = (LinearLayout)itemView;
			visitTasks = (CheckBox)itemView.findViewById(R.id.visitTasksCheckBox);
		}
	}
}