package com.hubclub.color_madness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class RetryScreen implements Screen,InputProcessor{

	private ColorGame game;
	private BitmapFont font;
	private ShapeRenderer shape;
	private Rectangle retry, menu, pointer;
	private SpriteBatch batch;
	private FileHandle file;
	private boolean hard;
	private int score;
	private boolean retryTouched,menuTouched;
    private Texture background;
    private Texture retryButton;
    private Texture backButton;


	public RetryScreen(ColorGame game){
		this.game=game;

	}

	public void set(boolean hard,int score){
		this.score=score;
		shape = new ShapeRenderer();
	
		retry = new Rectangle(Gdx.graphics.getWidth() / 2 - Constants.RETRY_WIDTH / 2, 500*Constants.height, Constants.RETRY_WIDTH, Constants.RETRY_HEIGHT);
		menu = new Rectangle(Gdx.graphics.getWidth() / 2 - Constants.BACK_WIDTH / 2, 300*Constants.height, Constants.BACK_WIDTH, Constants.BACK_HEIGHT);
		Gdx.input.setInputProcessor(this);
		pointer = new Rectangle(0,0,0.1f, 0.1f);
		batch=new SpriteBatch();
		font=new BitmapFont();
		font.setScale(Constants.width*2, Constants.height*2);
		file = Gdx.files.local("savefile/highscore.txt");
		this.hard=hard;
        //background
        background = new Texture(Gdx.files.internal("colour_cloud.jpg"));
        //buttons
        retryButton = new Texture(Gdx.files.internal("retry.png"));
        backButton = new Texture(Gdx.files.internal("back.png"));

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.begin();
        //draw background
        batch.draw(background,0,0,480*Constants.width,800*Constants.height); //i have no idea what these numbers mean

        //draw buttons
        batch.draw(retryButton, retry.x, retry.y, retry.width, retry.height);
        batch.draw(backButton, menu.x, menu.y, menu.width, menu.height);


		font.draw(batch, "You lost !", 190*Constants.width, 700*Constants.height);
		font.draw (batch, "Your score was: "+ score,0,100*Constants.height);
		if(file.exists())
		font.draw(batch, "Your highscore is: " +file.readString(), 0, 50*Constants.height);
		batch.end();

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
		background.dispose();

		System.gc();

		// TODO Auto-generated method stub
		//game.dispose();
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
		this.pointer.x=screenX;
		this.pointer.y=800*Constants.height-screenY;
		if(this.pointer.overlaps(menu) && !retryTouched){
			backButton.dispose();
			backButton=new Texture("back1.png");
			menu.x-=2;
			menu.y-=2;
			menuTouched=true;
		}
		if(this.pointer.overlaps(retry) && !menuTouched){
			retryButton.dispose();
			retryButton=new Texture("retry1.png");
			retry.x-=2;
			retry.y-=2;
			retryTouched=true;
		}
		//System.out.println(button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if(retryTouched){
			this.dispose();
			retryButton.dispose();
			backButton.dispose();
			retryTouched=false;
			ColorGame.mainScreen.set(2,hard,0);
			game.setScreen(ColorGame.mainScreen);
			

		}
		if(menuTouched){
			this.dispose();
			retryButton.dispose();
			backButton.dispose();
			menuTouched=false;
			ColorGame.menuScreen.set();
			game.setScreen(ColorGame.menuScreen);
		}
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
