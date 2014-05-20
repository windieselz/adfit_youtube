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

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.adfit.plugins.PlayerViewDemoActivity;

public class AdfitYoutube extends CordovaPlugin implements Serializable {
	
	private CallbackContext callback = null;
	
	private static AdfitYoutube _instance;

    public AdfitYoutube(){
    	
    }

    public static AdfitYoutube getInstance(){
        if (_instance == null)
        {
            _instance = new AdfitYoutube();
        }
        return _instance;
    }
	
	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		Log.d("Youtube","start android cordova execute");
		_instance = this;
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
		PlayerViewDemoActivity ytView = new PlayerViewDemoActivity();
		Context context = this.cordova.getActivity().getApplicationContext();
		Intent myIntent = new Intent(context, PlayerViewDemoActivity.class);
		myIntent.putExtra("youtubeid", vid);
		this.callback = callbackContext;
		//this.cordova.getActivity().startActivityForResult(myIntent,Activity.RESULT_OK);
		this.cordova.startActivityForResult((CordovaPlugin) this, myIntent, 0);
	}
	
	public final void setResult (int resultCode){
		if(resultCode == 1){
			Log.d("Youtube","watch ended");
		}else{
			Log.d("Youtube","watch failed");
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("Youtube","Result code : "+resultCode);
	}
	
	public void sendCallback(boolean status){
		if(this.callback == null){
			Log.d("Youtube","callback is null");
			return ;
		}
		
		if (status) { 
			 cordova.getActivity().runOnUiThread(new Runnable() {
		            public void run() {
		            	callback.success("ok");
		            }
		        });
			//this.callback.success("ok");
        } else {
        	//this.callback.error("Expected one non-empty string argument.");
        	cordova.getActivity().runOnUiThread(new Runnable() {
	            public void run() {
	            	callback.error("failed");
	            }
	        });
        }

	}
	
}
