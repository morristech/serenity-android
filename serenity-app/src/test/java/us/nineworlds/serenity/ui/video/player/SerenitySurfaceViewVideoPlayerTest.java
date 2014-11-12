/**
 * The MIT License (MIT)
 * Copyright (c) 2012 David Carver
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
 * OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package us.nineworlds.serenity.ui.video.player;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import us.nineworlds.serenity.SerenityRobolectricTestRunner;
import us.nineworlds.serenity.injection.modules.AndroidModule;
import us.nineworlds.serenity.injection.modules.SerenityModule;
import us.nineworlds.serenity.test.InjectingTest;
import us.nineworlds.serenity.ui.video.player.SerenitySurfaceViewVideoActivity.VideoPlayerOnCompletionListener;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import dagger.Module;
import dagger.Provides;

@RunWith(SerenityRobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class SerenitySurfaceViewVideoPlayerTest extends InjectingTest {

	@Mock
	KeyEvent keyEvent;

	@Mock
	MediaPlayer mockMediaPlayer;

	@Mock
	SurfaceHolder mockSurfaceHolder;

	@Mock
	ActionBar mockActionBar;

	SerenitySurfaceViewVideoActivity activity;

	@Override
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		super.setUp();
		ShadowApplication shadowApplication = Robolectric
				.shadowOf(Robolectric.application);
		shadowApplication
		.declareActionUnbindable("com.google.android.gms.analytics.service.START");
		activity = Robolectric
				.buildActivity(SerenitySurfaceViewVideoActivity.class).create()
				.get();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void onBackPressedIsCalledWhenKeyCodeBackIsReceived() {
		SerenitySurfaceViewVideoActivity spyActivity = spy(activity);
		doNothing().when(spyActivity).onBackPressed();
		spyActivity.onKeyDown(KeyEvent.KEYCODE_BACK, keyEvent);
		verify(spyActivity).onBackPressed();
	}

	@Test
	public void onKeyDownReturnsTrueWhenKeyCodeBackIsReceived() {
		SerenitySurfaceViewVideoActivity spyActivity = spy(activity);
		doNothing().when(spyActivity).onBackPressed();
		boolean result = spyActivity.onKeyDown(KeyEvent.KEYCODE_BACK, keyEvent);
		assertThat(result).isTrue();
	}

	@Test
	public void surfaceCreatedSetsMediaPlayerOnPreparedListener() {
		doNothing().when(mockMediaPlayer).setOnPreparedListener(
				any(VideoPlayerPrepareListener.class));

		activity.surfaceCreated(mockSurfaceHolder);

		verify(mockMediaPlayer).setOnPreparedListener(
				any(VideoPlayerPrepareListener.class));
	}

	@Test
	public void surfaceCreatedSetsMediaPlayerOnCompletionListener() {
		doNothing().when(mockMediaPlayer).setOnCompletionListener(
				any(VideoPlayerOnCompletionListener.class));

		activity.surfaceCreated(mockSurfaceHolder);

		verify(mockMediaPlayer).setOnCompletionListener(
				any(VideoPlayerOnCompletionListener.class));
	}

	@Test
	public void surfaceCreatedCallsMediaPlayerPrepareAsync() {
		activity.surfaceCreated(mockSurfaceHolder);
		verify(mockMediaPlayer).prepareAsync();
	}

	@Test
	public void surfaceDestroyedReleasesMediaPlayerWhenNotInReleaseState() {
		activity.setMediaplayerReleased(false);
		activity.surfaceDestroyed(mockSurfaceHolder);
		assertThat(activity.isMediaplayerReleased()).isTrue();
		verify(mockMediaPlayer).release();
	}

	@Test
	public void surfaceDestroyedDoesNotReleaseMediaPlayerWhenInReleaseState() {
		activity.setMediaplayerReleased(true);
		activity.surfaceDestroyed(mockSurfaceHolder);
		assertThat(activity.isMediaplayerReleased()).isTrue();
		verify(mockMediaPlayer, times(0)).release();
	}

	@Test
	@Config(reportSdk = 13)
	public void onCreateHidesCallsGetSupportActionBar() {
		activity = Robolectric
				.buildActivity(SerenitySurfaceViewVideoActivity.class).create()
				.get();
		ActionBar actionBar = activity.getSupportActionBar();
		assertThat(actionBar.isShowing()).isFalse();
	}

	@Override
	public List<Object> getModules() {
		List<Object> modules = new ArrayList<Object>();
		modules.add(new AndroidModule(Robolectric.application));
		modules.add(new TestModule());
		return modules;
	}

	@Module(addsTo = AndroidModule.class, includes = SerenityModule.class, overrides = true, injects = {
		SerenitySurfaceViewVideoActivity.class,
		SerenitySurfaceViewVideoPlayerTest.class })
	public class TestModule {

		@Provides
		MediaPlayer providesMediaPlayer() {
			return mockMediaPlayer;
		}

	}

}
