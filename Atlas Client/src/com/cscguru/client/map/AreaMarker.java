package com.cscguru.client.map;

import org.newdawn.slick.geom.Rectangle;

public class AreaMarker {
	private String name;
	private int tileX;
	private int tileY;
	private Rectangle r;
	
	public AreaMarker(float objX, float objY, int tileX, int tileY, int w, int h, String name){
		this.name = name;
		this.tileX = tileX;
		this.tileY = tileY;
		this.r = new Rectangle(objX, objY, w, h);
	}
	public Rectangle getRectangle(){
		return r;
	}
	public String getName(){
		return name;
	}
	public int getTileX(){
		return tileX;
	}
	public int getTileY(){
		return tileY;
	}
}
