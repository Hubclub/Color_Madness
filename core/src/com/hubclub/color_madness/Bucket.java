package com.hubclub.color_madness;

//The class for the bucket

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Bucket {
	
	private Rectangle rectangle;
	
	public Bucket(Rectangle rectangle){
		
		this.rectangle = new Rectangle (rectangle);
		
	}
	
	public void update() {
		
			//The movement of the bucket for Android/Desktop versions
		
			if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer) ){
				rectangle.x-=Gdx.input.getAccelerometerX()*MainScreen.delta*Constants.width*110;
			}
		
			if(Gdx.input.isKeyPressed(Keys.A)) {
				rectangle.x-=250*Constants.width*MainScreen.delta;
			}
		
			if (Gdx.input.isKeyPressed(Keys.D)) {
				rectangle.x+=250*Constants.width*MainScreen.delta;
			}
			
			//Preventing the bucket from getting out of the screen
			
			if(rectangle.x<0){
				rectangle.x=1;
			}
			
			if(rectangle.x+rectangle.width>Gdx.graphics.getWidth()){
				rectangle.x=Gdx.graphics.getWidth()-rectangle.width-1;
			}
		}
		
	
	public float getX(){
		return rectangle.x;
	}
	
	public Rectangle getRectangle(){
		return rectangle;
	}
	
	
	

}
