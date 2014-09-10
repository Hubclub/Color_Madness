package com.hubclub.color_madness;

public class ColorGame extends com.badlogic.gdx.Game {
	
	public static MainScreen mainScreen ;
	public static MenuScreen menuScreen ;
	public static RetryScreen retryScreen ; 
	public static InstructionScreen instructionScreen;
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
		menuScreen.set();
		retryScreen = new RetryScreen(this,myRequestHandler);
		this.setScreen(menuScreen);

	}

	@Override
	public void render () {
		
		super.render();
	
	}
}
