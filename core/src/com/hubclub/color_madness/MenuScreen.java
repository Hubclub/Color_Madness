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

public class MenuScreen implements Screen, InputProcessor{
	

	private ColorGame game;
	private BitmapFont font;
	private SpriteBatch batch;
	private Rectangle normal, hardcore, pointer;
	private ShapeRenderer shape;
	private boolean normalTouched, hardTouched;
	private IActivityRequestHandler myHandler;
    private Texture hardcoreButton;
    private Texture normalButton;
	private Texture background;
	private FileHandle file,settings;
	private SensivityBar bar;

    BitmapFont sceneFont;
	
	public MenuScreen(ColorGame game,IActivityRequestHandler handler){
		this.game = game;
		myHandler=handler;
		file=Gdx.files.local("savefile/highscore.txt");
		
		settings=Gdx.files.local("savefile/settings.txt");
	}
	
	public void set () {
        //Scene 2d label
        sceneFont = new BitmapFont();

        bar=new SensivityBar();
        Gdx.input.setInputProcessor(this);
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
        font.draw(batch, "Bucket sensitivity", 130*Constants.width, 130*Constants.height);
        bar.draw(batch);


		batch.end();
		
		if(Gdx.input.isTouched()){
			if(this.pointer.overlaps(bar.getBar())){
				if(Gdx.input.getX()>40*Constants.width && Gdx.input.getX()<420*Constants.width)
					bar.movePointer(Gdx.input.getX());
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
		background.dispose();
		shape.dispose();
		batch.dispose();
		font.dispose();
		bar.dispose();

		
		// TODO Auto-generated method stub
		
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
		this.pointer.x=screenX;
		this.pointer.y=800*Constants.height - screenY;
		if (this.pointer.overlaps(normal) && !hardTouched) {
			normalButton.dispose();
			normalButton = new Texture("normal1.png");
			normal.x-=2;
			normal.y-=2;
			normalTouched=true;
		}
		
		if (this.pointer.overlaps(hardcore) && !normalTouched) {
			hardcoreButton.dispose();
			hardcoreButton = new Texture("hardcore1.png");
			hardcore.x-=2;
			hardcore.y-=2;
			hardTouched=true;
		}
		
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (normalTouched) {
			
			this.dispose();
			normalButton.dispose();
			hardcoreButton.dispose();
			normalTouched=false;
			myHandler.showAds(false);
			Gdx.app.log("MenuScreen", ""+bar.getX());
			if(settings.exists()){
				if(Float.valueOf(settings.readString())!=bar.getX()){
					settings.writeString(Float.toString(bar.getX()),false);
				}
			}
			else{
				settings.writeString(Float.toString(bar.getX()), false);
				Gdx.app.log("MenuScreen", ""+bar.getX());
			}
		
			if (file.exists()) {
				Gdx.app.log("Main-touched", file.name());
				Gdx.app.log("if-file", "true");
				ColorGame.mainScreen.set(2, false, 0);
				game.setScreen(ColorGame.mainScreen);
				
			}
			else {
				Gdx.app.log("if-file", "false");
				game.setScreen(ColorGame.instructionScreen);
			}
			
		}
		
		if (hardTouched) {
			this.dispose();
			normalButton.dispose();
			hardcoreButton.dispose();
			hardTouched=false;
			ColorGame.mainScreen.set(2,true,0);
			myHandler.showAds(false);
			game.setScreen(ColorGame.mainScreen);
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
	
	public float getSensivity(){
		return bar.getX();
	}
	


}
