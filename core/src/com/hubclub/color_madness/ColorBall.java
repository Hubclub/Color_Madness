package com.hubclub.color_madness;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

//The class for the falling balls

public class ColorBall {
	private Rectangle rect;
	private MyColor color;
	private float velocity;
	private float acceleration=5;
	
	public ColorBall(float x,MyColor color){
		
		this.color=color;
		rect=new Rectangle(x,800*Constants.height,Constants.dropWidth,Constants.dropHeight);
		
		//the initial speed of the balls
		velocity=2*Constants.height*MainScreen.delta;
	}
	
	void update(){
		//the acceleration
		velocity+=(float)acceleration*MainScreen.delta*Constants.height;
		//System.out.println(velocity);
		rect.y-=velocity;
	}
	
	public Rectangle getRectangle(){
		return rect;
	}
	
	public MyColor getColor(){
		return color;
	}
	
	
}
