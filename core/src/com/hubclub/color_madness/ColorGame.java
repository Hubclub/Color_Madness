package com.hubclub.color_madness;

public class ColorGame extends com.badlogic.gdx.Game {
	
	public static MainScreen mainScreen ;
	public static MenuScreen menuScreen ;
	public static RetryScreen retryScreen ; 
	
	@Override
	public void create () {
		mainScreen = new MainScreen(this);
		menuScreen = new MenuScreen(this);
		menuScreen.set();
		retryScreen = new RetryScreen(this);
		this.setScreen(menuScreen);
	}

	@Override
	public void render () {
		
		super.render();
	
	}
}
