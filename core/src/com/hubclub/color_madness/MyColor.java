package com.hubclub.color_madness;

import com.badlogic.gdx.graphics.Color;

//clasa pentru culori ce urmeaza a fi combinate
public class MyColor extends Color{
	public int r,g,b;
	public float c,m,y,k,a;
	public int combined=2;
 
public MyColor(float c, float m, float y, float k , float a){
	 this.c=c;
	 this.m=m;
	 this.y=y;
	 this.k=k;
	 this.a=a;
 
	 r=(int) Math.round((1-c*(1-k)+k)*255+0.5);
	 g=(int) Math.round((1-m*(1-k)+k)*255+0.5);
	 b=(int) Math.round((1-y*(1-k)+k)*255+0.5);
 }
 
 public MyColor(int r,int g, int b, float a){
	 this.r=r;
	 this.g=g;
	 this.b=b;
	 this.a=a;
  
 
	 k=Math.min(c, Math.min(y,m));
	 c=(255-r-k)/(255-k);
	 m=(255-g-k)/(255-k);
	 y=(255-b-k)/(255-k);
 }
 
 public MyColor mix(MyColor color){
	 MyColor r = new MyColor(0,0,0,0);

  
	 r.c=(color.c+(combined-1)*c)/combined;
	 r.m=(color.m+(combined-1)*m)/combined;
	 r.y=(color.y+(combined-1)*y)/combined;
	 r.k=(color.k+(combined-1)*k)/combined;
	 r.a=(color.a+(combined-1)*a)/combined;
	 r.createRGB();
  
	 r.combined=combined+1;
  //Color res=new Color((float)r.r/255,(float)r.g/255,(float)r.b/255,r.a);
  
	 return r;
 }
 
 public void createRGB(){
	 r=(int) Math.round((1-c*(1-k)+k)*255+0.5);
	 g=(int) Math.round((1-m*(1-k)+k)*255+0.5);
	 b=(int) Math.round((1-y*(1-k)+k)*255+0.5);
  
 }
 
 public Color getRGB(){
	 Color res=new Color((float)r/255,(float)g/255,(float)b/255,a);
  
	 return res;
 	}
}