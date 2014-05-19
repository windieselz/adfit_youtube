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

import java.util.LinkedHashMap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.adfit.plugins.PlayerViewDemoActivity;

public class AdfitYoutube extends CordovaPlugin {

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		Log.d("Youtube","start android cordova execute");
		if(action.equals("openYoutube")) {
			String ytStr = args.getString(0);
			JSONObject jsonObj = new JSONObject(ytStr);
			String ytid = jsonObj.getString("youtubeId");
			Log.d("Youtube","start android youtube ID : "+ytid);
        	this.open(ytid, callbackContext);
        	return true;
        }        
		
		return false;
	}
	
	private void open(String vid, CallbackContext callbackContext) {
			
		//Intent intent = new Intent(null, Uri.parse("ytv://"+vid), this.cordova.getActivity(), OpenYouTubePlayerActivity.class);
		PlayerViewDemoActivity ytView = new PlayerViewDemoActivity();
		Context context = this.cordova.getActivity().getApplicationContext();
		Intent myIntent = new Intent(context, PlayerViewDemoActivity.class);
		myIntent.putExtra("youtubeid", vid);
		/*
		LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
        obj.put("callback", callbackContext); 
		Bundle b = new Bundle();
        b.putSerializable("bundleobj", obj);
        myIntent.putExtras(b);
        */
		this.cordova.getActivity().startActivity(myIntent);
	}
	
}
