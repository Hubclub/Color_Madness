package com.hubclub.color_madness;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class MenuScreen implements Screen{
	

	private ColorGame game;
	private BitmapFont font;
	private SpriteBatch batch;
	private Rectangle normal, hardcore, pointer;
	private ShapeRenderer shape;

    private Texture hardcoreButton;
    private Texture normalButton;
	private Texture background;

	
	public MenuScreen(ColorGame game){
		this.game = game;
	}
	
	public void set () {
		font = new BitmapFont();
		font.setScale(Constants.width*2, Constants.height*2);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		pointer = new Rectangle (0,0, 0.1f, 0.1f);

		normal = new Rectangle(Gdx.graphics.getWidth() / 2 - Constants.NORMAL_WIDTH/2, 500*Constants.height,250*Constants.width, 70*Constants.height); //i have no idea where does 500 and 250 px comes from
		hardcore = new Rectangle(Gdx.graphics.getWidth() / 2 - Constants.HARDCORE_WIDTH /2, 300*Constants.height, Constants.HARDCORE_WIDTH, Constants.HARDCORE_HEIGHT); //300 is still a mistery
        //background
        background = new Texture(Gdx.files.internal("colour_cloud.jpg"));

        //buttons
        normalButton = new Texture(Gdx.files.internal("normal.png"));
        hardcoreButton = new Texture(Gdx.files.internal("hardcore.png"));

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
        //draw the background
        batch.draw(background,0,0,480*Constants.width,800*Constants.height);
        //draw the menu buttons
        batch.draw(normalButton, normal.x, normal.y, normal.width, normal.height);
        batch.draw(hardcoreButton, hardcore.x , hardcore.y, hardcore.width, hardcore.height);


		font.draw(batch, "Hello", 210*Constants.width, 700*Constants.height);

		batch.end();
		
		shape.begin(ShapeType.Filled);
		//shape.rect(pointer.x,pointer.y, pointer.width, pointer.height);
		shape.end();
		
		if (Gdx.input.justTouched()) {
			pointer.x=Gdx.input.getX();
			pointer.y=800*Constants.height-Gdx.input.getY();
			if (pointer.overlaps(normal)) {
				this.dispose();
				//System.gc();
				ColorGame.mainScreen.set(2,false,0);
				game.setScreen(ColorGame.mainScreen);
			}
			if(pointer.overlaps(hardcore)){
				this.dispose();
				System.gc();
				ColorGame.mainScreen.set(2,true,0);
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
		//game.dispose();
		shape.dispose();
		batch.dispose();
		font.dispose();

		
		// TODO Auto-generated method stub
		
	}


}
