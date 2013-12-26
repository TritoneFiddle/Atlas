package com.cscguru.client.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**Class that adds a little more functionality to the SpriteSheet class that allows
 * the getting of the width and height of an individual sprite on the sheet.
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class Sprite extends SpriteSheet {
	private int tileWidth;
	private int tileHeight;
	/**Initializes the sprite with the image and stores the width and height of an invidual sprite on the sheet.
	 * @param image
	 */
	public Sprite(Image image) {
		super(image, image.getWidth() / 3, image.getHeight() / 4);
		tileWidth = image.getWidth() / 48;
		tileHeight = image.getHeight() / 64;
	}
	/**Returns an individual sprite's width.
	 * @return int
	 */
	public int getSpriteWidth(){
		return tileWidth;
	}
	/**Returns an individual sprite's height.
	 * @return int
	 */
	public int getSpriteHeight(){
		return tileHeight;
	}

}
