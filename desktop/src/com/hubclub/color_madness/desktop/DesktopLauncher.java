package com.hubclub.color_madness.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hubclub.color_madness.ColorGame;
import com.hubclub.color_madness.IActivityRequestHandler;

public class DesktopLauncher  {
	
	private static IActivityRequestHandler handler=new IActivityRequestHandler() {
		
		@Override
		public void showAds(boolean show) {
			// TODO Auto-generated method stub
			
		}
	};
		

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=480;
		config.height=800;
		new LwjglApplication(new ColorGame(handler), config);
	}


}
