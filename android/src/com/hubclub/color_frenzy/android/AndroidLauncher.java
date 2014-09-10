package com.hubclub.color_frenzy.android;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings.Secure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.hubclub.color_madness.Constants;
import com.hubclub.color_madness.IActivityRequestHandler;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.hubclub.color_madness.ColorGame;


public class AndroidLauncher extends  AndroidApplication implements IActivityRequestHandler {
	private AdView adView;
	
	private final int HIDE_AD = 0;
	private final int SHOW_AD = 1;

	private AdRequest adRequest;
	


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock=true;
	
		 adView = new AdView(this);
		 	AdSize adSize=new AdSize(LayoutParams.MATCH_PARENT,AdSize.BANNER.getHeight());
		    adView.setAdSize(adSize);
		    adView.setAdUnitId("XXXXXXXXXXXXXX");
		  
		   

		    
		    // Add the AdView to the view hierarchy. The view will have no size until the ad is loaded.
		    // This code assumes you have a LinearLayout with attribute android:id="@+id/linear_layout"
		    // in your activity_main.xml.
		    RelativeLayout layout = (RelativeLayout) findViewById(R.id.LinearLayout);
		    layout.addView(initializeForView(new ColorGame(this),config));
		    
		    	
		   	layout.addView(adView);
		       
	
		    // Create an ad request. Check logcat output for the hashed device ID to get test ads on a
		    // physical device.
		    
		     adRequest = new AdRequest.Builder().build();

		    	   
		    // Start loading the ad in the background.
		    adView.loadAd(adRequest);
		 
		    
		 
		Log.e("MainActivity" ,Secure.getString(getContext().getContentResolver(),Secure.ANDROID_ID));

		 
	}
	
	protected Handler handler = new Handler () {
		
		public void handleMessage (Message msg) {
		switch(msg.what) {
			case  SHOW_AD :{
				adView.loadAd(adRequest);
				adView.setVisibility(View.VISIBLE);
				 break; }
			
			case HIDE_AD : {
				adView.destroy();
				adView.setVisibility(View.GONE); break; }
		
			}
		}
		
	};
	

	  @Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) {
	      adView.resume();
	    }
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) {
	      adView.pause();
	    }
	    super.onPause();
	  }

	  @Override
	  public void onDestroy() {
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }

	@Override
	public void showAds(boolean show) {
		
		handler.sendEmptyMessage(show ? SHOW_AD : HIDE_AD);
		
	}
	 
	}

