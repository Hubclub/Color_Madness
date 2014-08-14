package com.hubclub.color_madness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

//the constants class

public class Constants {
	public static final float height =(float) Gdx.graphics.getHeight()/800;
	public static final float width =(float) Gdx.graphics.getWidth()/480;
	public static final float BucketHeight = 150 * height;
	public static final float BucketWidth = 100 * width;
	public static final Vector2 BucketStartPoint = new Vector2 (240*width, 20*height);
	public static final float firstPoint = 50*width;
	public static final float distance = 480*width/3;
	public static final float dropWidth=25*width,dropHeight=60*height;

    //Menu buttons dimensions in pixels
    //normal button
    public static final float NORMAL_WIDTH = 250 * width;
    public static final float NORMAL_HEIGHT = 70 * height;
    //hardcore button
    public static final float HARDCORE_WIDTH = 303 *width;
    public static final float HARDCORE_HEIGHT = 70 * height;

    //Retry screen buttons
     public static final float RETRY_WIDTH = 193 * width;
     public static final float RETRY_HEIGHT = 68 * height;
     public static final float BACK_WIDTH = 173 * width;
     public static final float BACK_HEIGHT = 71 * height;

	
	

}
