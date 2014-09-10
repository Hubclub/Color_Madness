package com.hubclub.color_madness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InstructionScreen implements Screen, InputProcessor {
	
	private Texture texture ;
	private SpriteBatch batch ;
	private ColorGame game;
	
	public InstructionScreen (ColorGame game) {
		Gdx.input.setInputProcessor(this);
		this.game=game;
		texture = new Texture ("instruction_normal.png");
		batch = new SpriteBatch ();
		/*batch.begin();
		batch.draw(texture,0f, 0f, 480*Constants.width, 800*Constants.height);
		batch.end();
		
		if (Gdx.input.justTouched()) {
			dispose();
			game.setScreen(ColorGame.mainScreen);

		}
		*/
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		batch.begin();
		batch.draw(texture,0f, 0f, 480*Constants.width, 800*Constants.height);
		batch.end();
		
		if (Gdx.input.justTouched()) {
			dispose();
			ColorGame.mainScreen.set(2,false,0);
			game.setScreen(ColorGame.mainScreen);
			//dispose();
		}
	
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
		
		texture.dispose();
		batch.dispose();
		System.gc();
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
