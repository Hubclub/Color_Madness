package com.hubclub.color_madness;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.utils.Array;

public class MenuScreen implements Screen{
	

	private ColorGame game;
	private BitmapFont font;
	private SpriteBatch batch;
	private Rectangle normal, hardcore, pointer;
	private ShapeRenderer shape;
	

	
	public MenuScreen(ColorGame game){
		this.game = game;
		font = new BitmapFont();
		font.setScale(Constants.width*2, Constants.height*2);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		pointer = new Rectangle (0,0, 0.1f, 0.1f);
		normal = new Rectangle(190*Constants.width, 500*Constants.height,100*Constants.width, 75*Constants.height);
		hardcore = new Rectangle(190*Constants.width, 300*Constants.height, 100*Constants.width, 75*Constants.height);
		
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		font.draw(batch, "Hello", 210*Constants.width, 700*Constants.height);
		batch.end();
		
		shape.begin(ShapeType.Filled);
		shape.rect(normal.x, normal.y, normal.width, normal.height);
		shape.rect(hardcore.x, hardcore.y, hardcore.width, hardcore.height);
		shape.rect(pointer.x,pointer.y, pointer.width, pointer.height);
		shape.end();
		
		if (Gdx.input.justTouched()) {
			pointer.x=Gdx.input.getX();
			pointer.y=800*Constants.height-Gdx.input.getY();
			if (pointer.overlaps(normal)) {
				this.dispose();
				System.gc();
				game.setScreen(new MainScreen(game, 2,false,0));
			}
			if(pointer.overlaps(hardcore)){
				this.dispose();
				System.gc();
				game.setScreen(new MainScreen(game,2,true,0));

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
		game.dispose();
		shape.dispose();
		batch.dispose();
		font.dispose();

		
		// TODO Auto-generated method stub
		
	}


}
