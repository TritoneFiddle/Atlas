package com.cscguru.client.map;

import org.newdawn.slick.geom.Rectangle;

public class Healer extends Rectangle {
	private static final long serialVersionUID = 1L;
	private int tileX;
	private int tileY;
	public Healer(float x, float y, int tileX, int tileY, float width, float height) {
		super(x, y, width, height);
		this.setTileX(tileX);
		this.setTileY(tileY);
	}
	public int getTileX() {
		return tileX;
	}
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}
	public int getTileY() {
		return tileY;
	}
	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

}
