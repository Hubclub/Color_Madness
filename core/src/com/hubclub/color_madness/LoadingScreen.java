package com.hubclub.color_madness;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class LoadingScreen implements Screen, InputProcessor {
	
	private ColorGame game;
	private SpriteBatch myBatch;
	private Texture texture;
	private boolean menu, hard, dispose;
	
	
	public LoadingScreen(ColorGame game, IActivityRequestHandler handler){
		this.game=game;
		
		
	}
	
	public void set ( boolean menu, boolean hard) {
		
		
		this.menu=menu;
		this.hard=hard;
		texture = new Texture ("Loadingpic.png");
		myBatch = new SpriteBatch();
		dispose=false;
		
	//	ColorGame.mainScreen.set(2, hard, 0);
		if (menu) {
		
			ColorGame.menuScreen.set();
			Timer.schedule(new Task() {
				public void run () {
					ColorGame.loadingScreen.dispose();
					game.setScreen(ColorGame.menuScreen);
			

				}
			}, 2);
		}
		else {
			
		
			
			ColorGame.mainScreen.set( hard);
			
			Timer.schedule(new Task() {
				public void run () {
					ColorGame.loadingScreen.dispose();
					game.setScreen(ColorGame.mainScreen);
					System.out.println("da");
				}
			}, 2);
		}
		
	}

	@Override
	public void render(float delta) {
			
		
		myBatch.begin();
		myBatch.draw(texture, 0, 0, 480*Constants.width, 800*Constants.height);
		myBatch.end();
		
		
		//System.out.println(" "+ dispose);
		
	}

	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		myBatch.dispose();
		texture.dispose();
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
