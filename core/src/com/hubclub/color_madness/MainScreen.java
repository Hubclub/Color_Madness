package com.hubclub.color_madness;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
public class MainScreen implements Screen, InputProcessor {

	private ColorGame game;
	private Texture  background,bucketTexture;
	private MyColor mix;// the color that must be obtained
	private SpriteBatch batch;
	private Bucket bucket;
	private Array<ColorBall> balls;// in this array we find the generated balls that fall down the screen
	private Array<Color> targets;// here are the colors that must be combined in order to obtain mix
	private ShapeRenderer shape;
	private int i,n,j;
	private float spawn=0,interval=2f;// used to measure the difference of time between the spawn of 2 balls
	private Random rand;
	private Rectangle bucketMouth;
	private int score;
	private int alive;
	private MyColor bucketColor;
	private Pixmap img;
	private int highscore;
	private int[][] paleta ;// basic colors
	private int cursor;//used to move through the matrix
	private boolean prins;
	private StatusBar bar;
	private int[] proportion;//for checking some shitty cases
	private boolean hard,bonus,flash;
	private Texture barTexture, targetTexture;
	private BitmapFont font;
	private Pool<ColorBall> ballPool=new Pool<ColorBall>(){
		protected ColorBall newObject(){
			return new ColorBall();
		}
	};
	IActivityRequestHandler myHandler;
	private Sound lost, caught, missed;

	
	public MainScreen(ColorGame game,IActivityRequestHandler handler){
		this.game=game;
		myHandler=handler;
	}
	
	public void set(int n,boolean hard,int score){
		Gdx.input.setInputProcessor(this);
		this.n=n;
		this.hard=hard;
		alive=1;
		this.score=score;
		flash=false;
		rand=new Random();
		background = new Texture("color.jpg");
		img = new Pixmap(Gdx.files.internal("bucket.png"));
		bucketTexture=new Texture(img);
		batch = new SpriteBatch();
		bucket=new Bucket(new Rectangle(Constants.BucketStartPoint.x,Constants.BucketStartPoint.y,Constants.BucketWidth,Constants.BucketHeight));
		balls = new Array<ColorBall>();
		shape=new ShapeRenderer();
		bucketMouth = new Rectangle(Constants.BucketStartPoint.x,Constants.BucketStartPoint.y+Constants.BucketHeight*(img.getHeight()-7)/img.getHeight(),Constants.BucketWidth,7*Constants.BucketHeight/img.getHeight());
		interval=5;
		bar=new StatusBar();
		bucketColor=new MyColor(0,0,0,1);
		targets = new Array<Color>();
		barTexture = new Texture(Gdx.files.internal("gold_bar.png"));
		targetTexture = new Texture(Gdx.files.internal("target.png"));
		font = new BitmapFont();
		font.setScale(Constants.width*2, Constants.height*2);
		
		lost = Gdx.audio.newSound(Gdx.files.internal("data/ggnoob.mp3"));
		caught = Gdx.audio.newSound(Gdx.files.internal("data/prins.mp3"));
		missed = Gdx.audio.newSound(Gdx.files.internal("data/ratez.mp3"));
		
	
		score=0;
		bonus=true;
		prins=false;
		//defining the palette colors
		paleta=new int[][]{{0,71,171},{0,128,0},{255,0,0},{78,22,9},{255,165,0},{255,255,0},{0,255,255},{66,170,255},{128,0,128},{0,0,0},{255,255,255}};
		proportion=new int[11];
		initVector(proportion);
		
		createMix();
		//we use this to prevent the case in which we have to combine the same colors (eg : 2 red and 2 blue , 4 red)
		while(cmmdc(proportion)!=1){
			createMix();
		}
		

		if(n<=3){
			interval=0.6f;
		}
		else{
			interval=Math.max(0.5f-(float)n/100,0.25f);
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor((float)212/255, (float)202/255,(float) 178/255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		

		
		//this.delta=delta;
		
		//System.out.println(ballPool.getFree());
		//System.out.println("" + interval);
		//System.out.println(Gdx.app.getJavaHeap());
		
		//randomly generating the dropping balls
		spawn+=delta;
		//System.out.println(interval);
		
		if(spawn>interval){
			
			cursor=rand.nextInt(11);
			ColorBall newBall=ballPool.obtain();
			newBall.init(20+rand.nextInt(440)*Constants.width,new MyColor(paleta[cursor][0],paleta[cursor][1],paleta[cursor][2],1),delta);
			newBall.setTexture(new Texture(cursor+".png"));
			balls.add(newBall);
			spawn=0;
		
		}
		
		
		// calling the function for the bucket movement
		bucket.update(delta);
		bucketMouth.setX(bucket.getX());
		if(!flash){
			

		batch.begin();
		batch.draw(background,0,0,480*Constants.width,800*Constants.height); // an optional background, this one is the best so far.
		
		batch.draw(bucketTexture,bucket.getX(),Constants.BucketStartPoint.y,Constants.BucketWidth,Constants.BucketHeight);
		
		for(ColorBall ball : balls){
			batch.draw(ball.getTexture(),ball.getRectangle().x,ball.getRectangle().y,ball.getRectangle().width,ball.getRectangle().height);
		}
		
		font.draw(batch, "Score:"+ score, 0, 25*Constants.height);
		
		batch.end();

	
		//Checking if one ball has been caught, and if so, if it is a valid ball or not
		for(ColorBall ball : balls){
			ball.update(delta);
			Color color = new Color (ball.getColor().getRGB());
			if(ball.getRectangle().y<0){
				balls.removeValue(ball, false);
				ballPool.free(ball);
				for (j=0; j<targets.size; j++) {
					if (targets.get(j).equals(color)) {
						score=score - 20;
						bonus=false;
						missed.play();
						break;
					}
				}
				
			}
			else
				if(ball.getRectangle().overlaps(bucketMouth)){
					for (Color target : targets) {
						if (target.equals(color)) {
							score= score + 100;
							if (hard) {
								score+=100;
							}
							
							if(prins){
								colorPixmap(img, bucketColor.getRGB(), bucketColor.mix(ball.getColor()).getRGB());
								bucketTexture.dispose();
								bucketTexture=new Texture(img);
								bucketColor=bucketColor.mix(ball.getColor());
							}
							else{
								colorPixmap(img, bucketColor.getRGB(), color);
								bucketTexture.dispose();
								bucketTexture=new Texture(img);
								bucketColor=ball.getColor();
								prins=true;
							}
							balls.removeValue(ball, false);
							ballPool.free(ball);
							targets.removeValue(target, false); //BAMB TRAMP A REZOLVAT PROBLEMA DUMNEZEII EI
							alive=1;
							caught.play();
							break;
						}
						else {
							alive=0;
						}

					}
				}
		}

		
		updateStatusBar();
		}
		else{
			Gdx.gl.glClearColor((float)212/255, (float)202/255,(float) 178/255, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}
		// checking if we are still alive or not ^^
		if (alive==0) {
			
			lost.play();
			//reading/writing the file in which we keep the highscore
			FileHandle file = Gdx.files.local("savefile/highscore.txt");
			if(file.exists()){
			  highscore = Integer.valueOf(file.readString());
			}else{
				
				highscore=-30000;
			}
			
			if(score > highscore){
			   file.writeString(Integer.toString(score ),false);
			}
			this.dispose();
			System.gc();
			ColorGame.retryScreen.set(hard,score);
			myHandler.showAds(true);
			game.setScreen(ColorGame.retryScreen);
			
		}
		
		if (targets.size==0) {
			if (bonus) {
				score = score +200;
			}
			this.dispose();
			System.gc();
			Gdx.gl20.glClearColor(1, 1, 1, 1);
			flash=true;
			ColorGame.mainScreen.set(n+1,hard,score);
			game.setScreen(ColorGame.mainScreen);
			
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
		//disposing shit
		img.dispose();
		batch.dispose();
		shape.dispose();
		for(ColorBall ball : balls){
			//ball.dispose();
			balls.removeValue(ball, false);
			ballPool.free(ball);
		}
		balls.clear();
		
		/* for (Color color : targets) {
			targets.removeValue(color, false);
		} */
		//targets.clear(); 
		background.dispose();
		barTexture.dispose();
		bucketTexture.dispose();
		targetTexture.dispose();
		
		lost.dispose();
		caught.dispose();
		missed.dispose();
		font.dispose();
		//game.dispose();
	}
	
	public int cmmdc(int[] vec){
		int d = vec[0];
		int rest=0;
		for(i=1;i<11;i++){
			if (vec[i]!=0) {
				do{
					rest=d%vec[i];
					d=vec[i];
					vec[i]=rest;
				}while(rest!=0);
			}
		}
		
		
		return d;
	}
	
	public void initVector(int[] vec){
		for(i=0;i<11;i++)
			vec[i]=0;
	}
	
	public void createMix(){
		targets.clear();
		
		for(i=0;i<n;i++){
			cursor=rand.nextInt(11);
			targets.add(new Color((float)paleta[cursor][0]/255,(float)paleta[cursor][1]/255,(float)paleta[cursor][2]/255,1));
			proportion[cursor]++;
			if(i==0)
				mix=new MyColor(paleta[cursor][0],paleta[cursor][1],paleta[cursor][2],1);
			else
				mix=mix.mix(new MyColor(paleta[cursor][0],paleta[cursor][1],paleta[cursor][2],1));
		}
	}
	
	public void colorPixmap(Pixmap pixmap,Color init,Color end){
		pixmap.setColor(end);
		for(i=0;i<pixmap.getWidth();i++){
			for(j=0;j<pixmap.getHeight()/2;j++){
				if(pixmap.getPixel(i, j)==Color.rgba8888(init)){
					pixmap.drawPixel(i, j);
				}
			}
		}
	}
	
	 private void updateStatusBar() {
		 //draw status bar texture
		 batch.begin();
		 batch.draw(barTexture, bar.getBar().x , bar.getBar().y, bar.getBar().width, bar.getBar().height);
		 batch.draw(targetTexture, bar.getBlack().x, bar.getBlack().y, bar.getBlack().width, bar.getBlack().height);
		 batch.end();
		 
		 shape.begin(ShapeType.Filled);
		 shape.setColor(mix.getRGB());
		 shape.circle(bar.getTarget().x, bar.getTarget().y, bar.getTarget().radius);
		 
		 
		 //checking if hardcore mode is enabled or not. In hardcore mode the targets colors won't be displayed
		 if(hard!=true){
			 
			//drawing the targets colors
			for(i=0;i<targets.size;i++){
					shape.setColor(targets.get(i));
					shape.circle(Math.min(-(i+1)*380*Constants.width/n + 430*Constants.width,-(i+1)*60*Constants.width+430*Constants.width), bar.getComponent().y, bar.getComponent().radius);
			}
		}
		shape.end();
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
