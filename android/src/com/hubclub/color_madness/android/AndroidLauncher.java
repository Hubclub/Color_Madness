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
		
		
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock=true;
		initialize(new ColorGame(), config);
	}
}
