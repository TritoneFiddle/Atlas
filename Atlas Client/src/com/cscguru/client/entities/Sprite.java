package com.cscguru.client.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Sprite extends SpriteSheet {
	private int tileWidth;
	private int tileHeight;
	public Sprite(Image image) {
		super(image, image.getWidth() / 3, image.getHeight() / 4);
		tileWidth = image.getWidth() / 48;
		tileHeight = image.getHeight() / 64;
	}
	public int getSpriteWidth(){
		return tileWidth;
	}
	public int getSpriteHeight(){
		return tileHeight;
	}

}
