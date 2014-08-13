package com.hubclub.color_madness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class RetryScreen implements Screen{
	
	private ColorGame game;
	private BitmapFont font;
	private ShapeRenderer shape;
	private Rectangle retry, menu, pointer;
	private SpriteBatch batch;
	private FileHandle file;
	private boolean hard;
	private int score;
	
	
	public RetryScreen(ColorGame game){
		this.game=game;
		
	}
	
	public void set(boolean hard,int score){
		this.score=score;
		shape = new ShapeRenderer();
		retry = new Rectangle(190*Constants.width, 500*Constants.height,100*Constants.width, 75*Constants.height);
		menu = new Rectangle(190*Constants.width, 300*Constants.height, 100*Constants.width, 75*Constants.height);
		pointer = new Rectangle(0,0,0.1f, 0.1f);
		batch=new SpriteBatch();
		font=new BitmapFont();
		font.setScale(Constants.width*2, Constants.height*2);
		file = Gdx.files.local("savefile/highscore.txt");
		this.hard=hard;
	
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		shape.begin(ShapeType.Filled);
		shape.rect(retry.x, retry.y, retry.width, retry.height);
		shape.rect(menu.x, menu.y, menu.width, menu.height);
		shape.end();
		
		batch.begin();
		font.draw(batch, "You lost !", 190*Constants.width, 700*Constants.height);
		font.draw (batch, "Your score was: "+ score,0,100*Constants.height);
		if(file.exists())
		font.draw(batch, "Your highscore is: " +file.readString(), 0, 50*Constants.height);
		batch.end();
		
		if (Gdx.input.justTouched()) {
			pointer.x=Gdx.input.getX();
			pointer.y=800*Constants.height - Gdx.input.getY();
			if (pointer.overlaps(menu)) {
				this.dispose();
				ColorGame.menuScreen.set();
				game.setScreen(ColorGame.menuScreen);

			}
			
			if (pointer.overlaps(retry)) {
				this.dispose();
				ColorGame.mainScreen.set(2,hard,0);
				game.setScreen(ColorGame.mainScreen);

			}

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
		shape.dispose();
		batch.dispose();
		font.dispose();
		
		System.gc();

		// TODO Auto-generated method stub
		//game.dispose();
	}
	
	

}
