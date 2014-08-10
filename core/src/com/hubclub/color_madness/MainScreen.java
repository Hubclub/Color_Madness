package com.hubclub.color_madness;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
	private Texture img, background;
	private MyColor mix;// culoarea care trebuie obtinuta
	private SpriteBatch batch;
	private Bucket bucket;
	private Array<ColorBall> balls;// bilele generate
	private Array<Color> targets;// culorile care trebuie combinate pentru a obtine mix
	public static float delta;
	private ShapeRenderer shape;
	private int i,n,j;
	private float spawn=0,interval=2f;//variabile folosite pentru masurarea diferentei de timp dintre generarea a 2 bile
	private Random rand;
	private Rectangle bucketMouth;
	private int score;
	private int alive = 1;
	private MyColor bucketColor;

	private int highscore;
	private int[][] paleta ;// culorile de baza
	private int cursor;//variabila folosita pentru deplasarea in matrice
	private boolean prins;
	private StatusBar bar;
	private int[] proportion;//pentru verificare
	private boolean hard,bonus;
	
	
	public MainScreen(ColorGame game,int n,boolean hard,int score){
		this.game=game;
		this.n=n;
		this.score=score;
		rand=new Random();
		background = new Texture("wall.jpg");
		img = new Texture("bucket_empty.png");
		batch = new SpriteBatch();
		bucket=new Bucket(new Rectangle(Constants.BucketStartPoint.x,Constants.BucketStartPoint.y,Constants.BucketWidth,Constants.BucketHeight));
		balls = new Array<ColorBall>();
		shape=new ShapeRenderer();
		bucketMouth = new Rectangle(Constants.BucketStartPoint.x,Constants.BucketStartPoint.y+Constants.BucketHeight*(img.getHeight()-7)/img.getHeight(),Constants.BucketWidth,7*Constants.BucketHeight/img.getHeight());
		interval=5;
		this.hard=hard;
		bar=new StatusBar();
		
		bucketColor=new MyColor(0,0,0,1);
		targets = new Array<Color>();
	
		score=0;
		bonus=true;
		
		//definirea paletei
		paleta=new int[][]{{0,71,171},{0,128,0},{255,0,0},{78,22,9},{255,165,0},{255,255,0},{0,255,255},{66,170,255},{128,0,128},{0,0,0},{255,255,255}};
		proportion=new int[11];
		initVector(proportion);
		
		createMix();
		//pentru a evita cazul in care se combina aceleasi culori
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
		
		//generarea
		spawn+=delta;
		if(spawn>interval){
			cursor=rand.nextInt(11);
			balls.add(new ColorBall(new Circle(20+rand.nextInt(440)*Constants.width,800*Constants.height,20*Constants.width),new MyColor(paleta[cursor][0],paleta[cursor][1],paleta[cursor][2],1)));
			spawn=0;
		}
		
		
		//miscarea galetii
		bucket.update();
		bucketMouth.setX(bucket.getX());
		
	
		
		
		
		
		batch.begin();
		//batch.draw(background,0,0,480*Constants.width,800*Constants.height);
		
		batch.draw(img,bucket.getX(),Constants.BucketStartPoint.y,Constants.BucketWidth,Constants.BucketHeight);
		batch.end();
		
		shape.begin(ShapeType.Filled);
		
		
		//desenarea bilelor de prins
		for(i=0;i<balls.size;i++){
			shape.setColor(balls.get(i).getColor().getRGB());
			shape.circle(balls.get(i).getCircle().x,balls.get(i).getCircle().y ,balls.get(i).getCircle().radius);
		}
		
		//verificarea prinderii/scaparii unei bile
		for(i=0;i<balls.size;i++){
			balls.get(i).update();
			if(balls.get(i).getCircle().y<0){
				balls.removeIndex(i);
				for (j=0; j<targets.size; j++) {
					if (targets.get(j).equals(balls.get(i).getColor().getRGB())) {
						score=score - 10;
						bonus=false;
						break;
					}
				}
				
			}
			else
				if(balls.get(i).fits(bucketMouth)){
					for (j=0;j<targets.size; j++) {
						if (targets.get(j).equals(balls.get(i).getColor().getRGB())) {
							score= score + 100;
							if(prins)
								bucketColor=bucketColor.mix(balls.get(i).getColor());
							else{
								bucketColor=balls.get(i).getColor();
								prins=true;
							}
							balls.removeIndex(i);
							targets.removeIndex(j); //BAMB TRAMP A REZOLVAT PROBLEMA DUMNEZEII EI
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
		
		//culoarea galetii + bara
		shape.setColor(bucketColor.getRGB());
		shape.rect(bucket.getRectangle().x+bucket.getRectangle().width/3,bucket.getRectangle().y+bucket.getRectangle().height/3,bucket.getRectangle().width/3,bucket.getRectangle().height/3);
		shape.setColor(new Color(0.5f,0.5f,0.5f,1));
		shape.rect(bar.getBar().x,bar.getBar().y,bar.getBar().width,bar.getBar().height);
		
		//patratul negru in care se deseneaza mix
		shape.setColor(new Color(0,0,0,1));
		shape.rect(bar.getBlack().x,bar.getBlack().y,bar.getBlack().width,bar.getBlack().height);
		
		shape.setColor(mix.getRGB());
		shape.circle(bar.getTarget().x, bar.getTarget().y, bar.getTarget().radius);
		
		//in modul hard culorile din targets nu sunt desenate
		if(hard!=true){
			
			//desenarea culorilor din targets
			for(i=0;i<targets.size;i++){
				shape.setColor(targets.get(i));
				shape.circle(Math.max(i*380*Constants.width/n + 100*Constants.width,i*60*Constants.width+100*Constants.width), bar.getComponent().y, bar.getComponent().radius);
			}
		}
		shape.end();
		
		// moartea
		if (alive==0) {
			
			//scrierea/citirea fisierului care retine highscore-ul
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
	
	
	

}
