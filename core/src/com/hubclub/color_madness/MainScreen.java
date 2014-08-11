package com.hubclub.color_madness;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
public class MainScreen implements Screen {

	private ColorGame game;
	private Texture  background;
	private MyColor mix;// the color that must be obtained
	private SpriteBatch batch;
	private Bucket bucket;
	private Array<ColorBall> balls;// in this array we find the generated balls that fall down the screen
	private Array<Color> targets;// here are the colors that must be combined in order to obtain mix
	public static float delta;
	private ShapeRenderer shape;
	private int i,n,j;
	private float spawn=0,interval=2f;// used to measure the difference of time between the spawn of 2 balls
	private Random rand;
	private Rectangle bucketMouth;
	private int score;
	private int alive = 1;
	private MyColor bucketColor;
	private Pixmap img, drop;
	private int highscore;
	private int[][] paleta ;// basic colors
	private int cursor;//used to move through the matrix
	private boolean prins;
	private StatusBar bar;
	private int[] proportion;//for checking some shitty cases
	private boolean hard,bonus;
	private Color lastBall;
	
	
	public MainScreen(ColorGame game,int n,boolean hard,int score){
		this.game=game;
		this.n=n;
		this.score=score;
		rand=new Random();
		background = new Texture("wall.jpg");
		img = new Pixmap(Gdx.files.internal("bucket.png"));
		drop=new Pixmap(Gdx.files.internal("pic.png"));
		batch = new SpriteBatch();
		bucket=new Bucket(new Rectangle(Constants.BucketStartPoint.x,Constants.BucketStartPoint.y,Constants.BucketWidth,Constants.BucketHeight));
		balls = new Array<ColorBall>();
		shape=new ShapeRenderer();
		bucketMouth = new Rectangle(Constants.BucketStartPoint.x,Constants.BucketStartPoint.y+Constants.BucketHeight*(img.getHeight()-7)/img.getHeight(),Constants.BucketWidth,7*Constants.BucketHeight/img.getHeight());
		interval=5;
		this.hard=hard;
		bar=new StatusBar();
		
		lastBall=new Color(0,0,1,1);
		bucketColor=new MyColor(0,0,0,1);
		targets = new Array<Color>();
	
		score=0;
		bonus=true;
		
		//defining the palette colors
		paleta=new int[][]{{0,71,171},{0,128,0},{255,0,0},{78,22,9},{255,165,0},{255,255,0},{0,255,255},{66,170,255},{128,0,128},{0,0,0},{255,255,255}};
		proportion=new int[11];
		initVector(proportion);
		
		createMix();
		//we use this to prevent the case in which we have to combine the same colors (eg : 2 red and 2 blue , 4 red)
		while(cmmdc(proportion)!=1){
			createMix();
		}
		
		interval=0.5f - (float)n/100 ;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor((float)212/255, (float)202/255,(float) 178/255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.delta=delta;
		
		
		
		System.out.println("" + interval);
		
		//randomly generating the dropping balls
		spawn+=delta;
		if(spawn>interval){
			cursor=rand.nextInt(11);
			balls.add(new ColorBall(20+rand.nextInt(440)*Constants.width,new MyColor(paleta[cursor][0],paleta[cursor][1],paleta[cursor][2],1)));
			spawn=0;
		}
		
		
		// calling the function for the bucket movement
		bucket.update();
		bucketMouth.setX(bucket.getX());
		
	
		
		
		
		
		batch.begin();
		batch.draw(background,0,0,480*Constants.width,800*Constants.height); // an optional background, this one is the best so far.
		
		batch.draw(new Texture(img),bucket.getX(),Constants.BucketStartPoint.y,Constants.BucketWidth,Constants.BucketHeight);
		for(ColorBall ball : balls){
			colorPixmap(drop,lastBall,ball.getColor().getRGB());
			lastBall=ball.getColor().getRGB();
			batch.draw(new Texture(drop),ball.getRectangle().x,ball.getRectangle().y,ball.getRectangle().width,ball.getRectangle().height);
		}
		
		batch.end();
		
		shape.begin(ShapeType.Filled);
		
		
	
		//Checking if one ball has been caught, and if so, if it is a valid ball or not
		for(ColorBall ball : balls){
			ball.update();
			if(ball.getRectangle().y<0){
				balls.removeValue(ball, true);
				for (j=0; j<targets.size; j++) {
					if (targets.get(j).equals(ball.getColor().getRGB())) {
						score=score - 10;
						bonus=false;
						break;
					}
				}
				
			}
			else
				if(ball.getRectangle().overlaps(bucketMouth)){
					
					for (Color target : targets) {
						if (target.equals(ball.getColor().getRGB())) {
							score= score + 100;
							if(prins){
								colorPixmap(img, bucketColor.getRGB(), bucketColor.mix(ball.getColor()).getRGB());
								bucketColor=bucketColor.mix(ball.getColor());
							}
							else{
								colorPixmap(img, bucketColor.getRGB(), ball.getColor().getRGB());
								bucketColor=ball.getColor();
								prins=true;
							}
							
							balls.removeValue(ball, true);
							targets.removeValue(target, true); //BAMB TRAMP A REZOLVAT PROBLEMA DUMNEZEII EI
							//System.out.println(score);
							alive=1;
							break;
						}
						else {
							alive=0;
						}

					}
				}
		}
		
		
		shape.setColor(new Color(0.5f,0.5f,0.5f,1));
		shape.rect(bar.getBar().x,bar.getBar().y,bar.getBar().width,bar.getBar().height);
		
		//the black rectangle in which the final color (mix) is draw
		shape.setColor(new Color(0,0,0,1));
		shape.rect(bar.getBlack().x,bar.getBlack().y,bar.getBlack().width,bar.getBlack().height);
		
		shape.setColor(mix.getRGB());
		shape.circle(bar.getTarget().x, bar.getTarget().y, bar.getTarget().radius);
		
		//checking if hardcore mode is enabled or not. In hardcore mode the targets colors won't be displayed
		if(hard!=true){
			
			//drawing the targets colors
			for(i=0;i<targets.size;i++){
				shape.setColor(targets.get(i));
				shape.circle(Math.max(i*380*Constants.width/n + 100*Constants.width,i*60*Constants.width+100*Constants.width), bar.getComponent().y, bar.getComponent().radius);
			}
		}
		shape.end();
		
		// checking if we are still alive or not ^^
		if (alive==0) {
			
			//reading/writing the file in which we keep the highscore
			FileHandle file = Gdx.files.local("savefile/highscore.txt");
			if(file.exists()){
			  highscore = Integer.valueOf(file.readString());
			}
			
			if(score > highscore){
			   file.writeString(Integer.toString(score ),false);
			}
			game.setScreen(new RetryScreen(game,n,hard,score));
			this.dispose();
			
		}
		
		if (targets.size==0) {
			if (bonus) {
				score = score +100;
			}
			game.setScreen(new MainScreen(game, n+1,hard,score));
			this.dispose();
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
			for(j=0;j<pixmap.getHeight();j++){
				if(pixmap.getPixel(i, j)==Color.rgba8888(init)){
					pixmap.drawPixel(i, j);
				}
					
			}
		}
	}

	
	
	

}
