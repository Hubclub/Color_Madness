package com.hubclub.color_madness;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

//The class for the falling balls

public class ColorBall implements Poolable{
	private Rectangle rect;
	private MyColor color;
	private float velocity;
	private float acceleration=5;
	private Texture drop;
	
/*	public ColorBall(float x,MyColor color){
		
		this.color=color;
		rect=new Rectangle(x,800*Constants.height,Constants.dropWidth,Constants.dropHeight);
		
		//the initial speed of the balls
		velocity=2*Constants.height*MainScreen.delta;
	}*/
	
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
	public Texture getTexture(){
		return drop;
	}
	public void setTexture(Texture drop){
		this.drop=drop;
	}
	/*public void dispose(){
		drop.dispose();
	} */

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		drop=null;
		rect=null;
		velocity=0;
		acceleration=0;
		color=null;
	}
	
	public void init(float x, MyColor color){
		this.color=color;
		rect=new Rectangle(x,800*Constants.height,Constants.dropWidth,Constants.dropHeight);
		
		//the initial speed of the balls
		velocity=2*Constants.height*MainScreen.delta;
		acceleration=5;
	}
	
}
