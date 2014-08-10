package com.hubclub.color_madness;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

//clasa pentru bilele care cad

public class ColorBall {
	private Circle circle;
	private MyColor color;
	private float velocity;
	private float acceleration=5;
	
	public ColorBall(Circle circle,MyColor color){
		this.circle=new Circle(circle);
		this.color=color;
		
		//viteza initiala
		velocity=2*Constants.height*MainScreen.delta;
	}
	
	void update(){
		//accelerarea
		velocity+=(float)acceleration*MainScreen.delta*Constants.height;
		//System.out.println(velocity);
		circle.y-=velocity;
	}
	
	public Circle getCircle(){
		return circle;
	}
	
	public MyColor getColor(){
		return color;
	}
	
	public boolean fits(Rectangle rectangle){
		
		//verifica daca o bila intra in galeata
		if(circle.y<rectangle.y+rectangle.height && circle.y>rectangle.y){
			if(circle.x>rectangle.x && circle.x<rectangle.x+rectangle.width){
				return true;
			}
			else return false;
		}
		else return false;
	}
}
