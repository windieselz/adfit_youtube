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


import java.util.HashMap;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.PlaylistEventListener;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener; 
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.apache.cordova.CallbackContext;
/**
 * A simple YouTube Android API demo application which shows how to create a simple application that
 * displays a YouTube Video in a {@link YouTubePlayerView}.
 * <p>
 * Note, to use a {@link YouTubePlayerView}, your activity must extend {@link YouTubeBaseActivity}.
 */
public class PlayerViewDemoActivity extends YouTubeBaseActivity {
  private YouTubePlayerView youTubeView;	
  private YouTubePlayer player;
  private MyPlaylistEventListener playlistEventListener;
  private MyPlayerStateChangeListener playerStateChangeListener;
  private MyPlaybackEventListener playbackEventListener;
  private CallbackContext callback;
  private String ytId;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActionBar actionBar = getActionBar();
	actionBar.hide();
	
	/*
	 if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
	 */
	
    LinearLayout ll = new LinearLayout(this);
    ll.setOrientation(LinearLayout.VERTICAL);
    ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));   
    setContentView(ll);
    
    Intent intent = getIntent();
    ytId = intent.getStringExtra("youtubeid");
    
    youTubeView = new YouTubePlayerView(this);
    youTubeView.initialize("AIzaSyCcvPX2czx97G2gF1BkilCaJIv4do7PA9E", new OnInitializedListener() {
		@Override
		public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error){
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored){
			// TODO Auto-generated method stub
			 player.setPlayerStyle(PlayerStyle.CHROMELESS);
			 player.setPlaylistEventListener(playlistEventListener);
			 player.setPlayerStateChangeListener(playerStateChangeListener);
			 player.setPlaybackEventListener(playbackEventListener);
			 //player.cueVideo("4x1dDNiBje8");
			 player.loadVideo(ytId); 
		}
    });
    
    ll.addView(youTubeView);
    playlistEventListener = new MyPlaylistEventListener();
    playerStateChangeListener = new MyPlayerStateChangeListener();
    playbackEventListener = new MyPlaybackEventListener();
	/*
    Bundle bn = new Bundle();
    bn = getIntent().getExtras();
    HashMap<String, Object> getobj = new HashMap<String, Object>();
    getobj = (HashMap<String, Object>) bn.getSerializable("bundleobj");
    callback = (CallbackContext) getobj.get("callback");
    */
  }
  
  private void log(String message) {

  }
  
  @Override
  public void onBackPressed() {
      super.onBackPressed();
      this.finish();
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
	      finish();
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


