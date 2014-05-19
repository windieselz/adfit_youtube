package com.adfit.plugins;

/*!
 *  
 * OpenYoutTubePlugin.java
 * Created By Urucas
 * 
 * OpenYouTubePlugin is a phonegap extension of the openyoutubeactivity created by Keyes Labs, 
 * you can check the source code in https://code.google.com/p/android-youtube-player/
 * 
 */

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.adfit.plugins.PlayerViewDemoActivity;

public class AdfitYoutube extends CordovaPlugin {

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		if(action.equals("open")) {
			String ytid = args.getString(0);
        	this.open(ytid, callbackContext);
        	return true;
        }        
		
		return false;
	}
	
	private void open(String vid, CallbackContext callbackContext) {
			
		//Intent intent = new Intent(null, Uri.parse("ytv://"+vid), this.cordova.getActivity(), OpenYouTubePlayerActivity.class);
		PlayerViewDemoActivity ytView = new PlayerViewDemoActivity();
		Intent myIntent = new Intent(this, ytView);
		myIntent.putExtra("key", value); //Optional parameters
		this.cordova.getActivity().startActivity(myIntent);
	}
	
}
