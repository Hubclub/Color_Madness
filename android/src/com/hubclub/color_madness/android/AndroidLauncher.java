package com.hubclub.color_madness.android;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.hubclub.color_madness.ColorGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		   PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
		   wl.acquire();
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new ColorGame(), config);
	}
}
