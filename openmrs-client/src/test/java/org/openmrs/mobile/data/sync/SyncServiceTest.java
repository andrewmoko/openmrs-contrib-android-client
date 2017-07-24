package org.openmrs.mobile.data.sync;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openmrs.mobile.BuildConfig;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SyncServiceTest {
	@Test
	public void sync_shouldPushThenPull() throws Exception {

	}

	@Test
	public void sync_shouldOnlyPullIfPushedSuccessfully() throws Exception {

	}

	@Test
	public void sync_shouldPushChangesInSyncLog() throws Exception {

	}

	@Test
	public void sync_shouldDeleteSyncLogRecordWhenPushed() throws Exception {

	}

	@Test
	public void sync_shouldPullEachSubscription() throws Exception {

	}

	@Test
	public void sync_shouldUpdateSubscriptionTimeWhenPulled() throws Exception {

	}

	@Test
	public void sync_shouldPullCorrectPatientInformation() throws Exception {

	}

	@Test
	public void sync_shouldSyncReadOnlyData() throws Exception {

	}

	@Test
	public void sync_shouldRemoveOldReadOnlyData() throws Exception {

	}
}
