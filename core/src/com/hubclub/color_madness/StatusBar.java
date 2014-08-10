package com.hubclub.color_madness;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;


//bara de sus 
public class StatusBar {
	private Rectangle bar,black;
	private Circle target;
	private Circle component;

	public StatusBar(){
		
		bar=new Rectangle();
		bar.x=0;
		bar.y=(800-50)*Constants.height;
		bar.width=480*Constants.width;
		bar.height=50*Constants.height;
		
		target=new Circle(50*Constants.width,Constants.height*(800-25),15*Constants.width);
		component=new Circle(150*Constants.width,Constants.height*(800-25),15*Constants.width);
		black=new Rectangle(30*Constants.width,(800-50)*Constants.height,40*Constants.width,50*Constants.height);
	}

	public Rectangle getBar() {
		return bar;
	}

	public Circle getTarget() {
		return target;
	}

	public Circle getComponent() {
		return component;
	}
	
	public Rectangle getBlack(){
		return black;
	}
	
	
}
