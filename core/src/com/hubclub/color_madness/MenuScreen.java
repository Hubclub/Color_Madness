package com.hubclub.color_madness;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class MenuScreen implements Screen, InputProcessor{
	

	private ColorGame game;
	private BitmapFont font;
	private SpriteBatch batch;
	private Rectangle normal, hardcore, pointer;
	private ShapeRenderer shape;
	private boolean normalTouched, hardTouched;

    private Texture hardcoreButton;
    private Texture normalButton;
	private Texture background;

    //Stage
    //private Stage stage;
    //private Label label;
    //private Label.LabelStyle style;
    BitmapFont sceneFont;
	
	public MenuScreen(ColorGame game){
		this.game = game;
	}
	
	public void set () {
        //Scene 2d label
        sceneFont = new BitmapFont();
        //style = new Label.LabelStyle();
       // style.font = sceneFont;
       // stage = new Stage();
        //label = new Label("Hello Color Game", style);
        
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




		batch.end();
		
		shape.begin(ShapeType.Filled);
		//shape.rect(pointer.x,pointer.y, pointer.width, pointer.height);
		shape.end();
		
       //stage.addActor(label);
       //stage.draw();
		
		
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
			ColorGame.mainScreen.set(2,false,0);
			game.setScreen(ColorGame.mainScreen);
		}
		
		if (hardTouched) {
			this.dispose();
			normalButton.dispose();
			hardcoreButton.dispose();
			hardTouched=false;
			ColorGame.mainScreen.set(2,true,0);
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


}
