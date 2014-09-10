package com.hubclub.color_madness;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SensivityBar {
	private Texture barTexture, pointerTexture;
	private Rectangle pointer, bar;
	private FileHandle settings;
	
	public SensivityBar(){
		settings=Gdx.files.local("savefile/settings.txt");
		bar=new Rectangle(40*Constants.width,50*Constants.height,400*Constants.width,30*Constants.height);
		if(settings.exists()){
			pointer=new Rectangle(Float.valueOf(settings.readString()),50*Constants.height,20*Constants.width,30*Constants.height);
		}else{
			pointer=new Rectangle(240*Constants.width,50*Constants.height,20*Constants.width,30*Constants.height);
		}
		barTexture=new Texture("gold_bar.png");
		pointerTexture=new Texture("pic.png");
		
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(barTexture,bar.x,bar.y,bar.width,bar.height);
		batch.draw(pointerTexture,pointer.x,pointer.y,pointer.width,pointer.height);
	}
	
	public void movePointer(int x){
		pointer.x=x;
	}
	
	public Rectangle getBar(){
		return bar;
	}
	public float getX(){
		return pointer.x;
	}
	
	public void dispose(){
		barTexture.dispose();
		pointerTexture.dispose();
	
	}
}
