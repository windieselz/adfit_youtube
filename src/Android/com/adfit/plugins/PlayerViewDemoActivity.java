/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adfit.plugins;


import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.PlaylistEventListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * A simple YouTube Android API demo application which shows how to create a simple application that
 * displays a YouTube Video in a {@link YouTubePlayerView}.
 * <p>
 * Note, to use a {@link YouTubePlayerView}, your activity must extend {@link YouTubeBaseActivity}.
 */
public class PlayerViewDemoActivity extends YouTubeFailureRecoveryActivity {
	
  private YouTubePlayer player;
  private MyPlaylistEventListener playlistEventListener;
  private MyPlayerStateChangeListener playerStateChangeListener;
  private MyPlaybackEventListener playbackEventListener;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
 
    LinearLayout ll = new LinearLayout(this);
    ll.setOrientation(LinearLayout.VERTICAL);
    ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    /*
    TextView tv1 = new TextView(this);
    tv1.setText("HELLO");
    ll.addView(tv1);
    */
    
    setContentView(ll);
    //setContentView(R.layout.playerview_demo);

    //YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
    YouTubePlayerView youTubeView = new YouTubePlayerView(this);
    ll.addView(youTubeView);
    youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    
    playlistEventListener = new MyPlaylistEventListener();
    playerStateChangeListener = new MyPlayerStateChangeListener();
    playbackEventListener = new MyPlaybackEventListener();
  }

  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
      boolean wasRestored) {
    if (!wasRestored) {
      
    }
    
    this.player = player;
    player.setPlayerStyle(PlayerStyle.CHROMELESS);
    player.setPlaylistEventListener(playlistEventListener);
    player.setPlayerStateChangeListener(playerStateChangeListener);
    player.setPlaybackEventListener(playbackEventListener);
    //player.cueVideo("4x1dDNiBje8");
    player.loadVideo("4x1dDNiBje8"); 
  }

  @Override
  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
    return (YouTubePlayerView) findViewById(R.id.youtube_view);
  }
  
  private void log(String message) {

  }
  
  
  private final class MyPlaylistEventListener implements PlaylistEventListener {
	    @Override
	    public void onNext() {
	      log("NEXT VIDEO");
	    }

	    @Override
	    public void onPrevious() {
	      log("PREVIOUS VIDEO");
	    }

	    @Override
	    public void onPlaylistEnded() {
	      log("PLAYLIST ENDED");
	    }
	  }

	  private final class MyPlaybackEventListener implements PlaybackEventListener {
	    String playbackState = "NOT_PLAYING";
	    String bufferingState = "";
	    @Override
	    public void onPlaying() {
	      playbackState = "PLAYING";
	      log("\tPLAYING ");
	    }

	    @Override
	    public void onBuffering(boolean isBuffering) {
	      bufferingState = isBuffering ? "(BUFFERING)" : "";
	      log("\t\t" + (isBuffering ? "BUFFERING " : "NOT BUFFERING "));
	    }

	    @Override
	    public void onStopped() {
	      playbackState = "STOPPED";
	      log("\tSTOPPED");
	    }

	    @Override
	    public void onPaused() {
	      playbackState = "PAUSED";
	      log("\tPAUSED ");
	    }

	    @Override
	    public void onSeekTo(int endPositionMillis) {
	      log("\tSEEKTO");
	    }
	  }

	  private final class MyPlayerStateChangeListener implements PlayerStateChangeListener {
	    String playerState = "UNINITIALIZED";

	    @Override
	    public void onLoading() {
	      playerState = "LOADING";
	      log(playerState);
	    }

	    @Override
	    public void onLoaded(String videoId) {
	      playerState = String.format("LOADED %s", videoId);
	      log(playerState);
	    }

	    @Override
	    public void onAdStarted() {
	      playerState = "AD_STARTED";
	      log(playerState);
	    }

	    @Override
	    public void onVideoStarted() {
	      playerState = "VIDEO_STARTED";
	      log(playerState);
	    }

	    @Override
	    public void onVideoEnded() {
	      playerState = "VIDEO_ENDED";
	      log(playerState);
	    }

	    @Override
	    public void onError(ErrorReason reason) {
	      playerState = "ERROR (" + reason + ")";
	      if (reason == ErrorReason.UNEXPECTED_SERVICE_DISCONNECTION) {
	        // When this error occurs the player is released and can no longer be used.
	        player = null;
	      }
	      log(playerState);
	    }

	  }
}


