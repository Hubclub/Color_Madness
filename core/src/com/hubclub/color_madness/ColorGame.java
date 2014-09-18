package com.hubclub.color_madness;

public class ColorGame extends com.badlogic.gdx.Game {
	
	public static MainScreen mainScreen ;
	public static MenuScreen menuScreen ;
	public static RetryScreen retryScreen ; 
	public static InstructionScreen instructionScreen;
	public static LoadingScreen loadingScreen;
	public static boolean ad;
	private IActivityRequestHandler myRequestHandler;
	
	public ColorGame(IActivityRequestHandler handler ){
		myRequestHandler=handler;
	}
	
	@Override
	public void create () {
		ad=true;
		instructionScreen = new InstructionScreen(this);
		mainScreen = new MainScreen(this,myRequestHandler);
		menuScreen = new MenuScreen(this,myRequestHandler);
		loadingScreen = new LoadingScreen(this, myRequestHandler);
		loadingScreen.set(true, true);
		retryScreen = new RetryScreen(this,myRequestHandler);
		this.setScreen(loadingScreen);

	}

	@Override
	public void render () {
		
		super.render();
	
	}
}
