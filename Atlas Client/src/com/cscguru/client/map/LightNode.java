package com.cscguru.client.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.interfaces.ITiled;
import com.cscguru.client.ui.Settings;

/**Class that handles the basic lighting on the map.
 * @author Bryan Bennett
 * @date Jan 1, 2014
 */
public class LightNode implements ITiled {
	private int tileX;
	private int tileY;
	private Image i;
	private int offsetX;
	private int offsetY;
	
	/**Initializes a light node at an x,y (in tiles) location.
	 * @param tileX
	 * @param tileY
	 * @throws SlickException 
	 */
	public LightNode(int tileX, int tileY) throws SlickException{
		this.tileX = tileX;
		this.tileY = tileY;
		i = new Image("res/gfx/light.png");
		offsetX = i.getWidth()/2;
		offsetY = i.getHeight()/2;
	}
	/**Draws the lightnode with the correct alpha value.
	 * @param g
	 * @param camX
	 * @param camY
	 * @param alpha
	 */
	public void draw(Graphics g, int camX, int camY, float alpha) {
		if (tileX + 20 >= camX && tileY + 20 >= camY){
			int diffX = (tileX - camX) * 16 + Settings.CAM_X;
			int diffY = (tileY - camY) * 16 + Settings.CAM_Y;
			i.setAlpha(alpha);
			i.draw(diffX-161, diffY-161);
		}
		
	}
	@Override
	public void draw(Graphics g, int camX, int camY) {
		if (tileX + 20 >= camX && tileY + 20 >= camY){
			int diffX = (tileX - camX) * 16 + Settings.CAM_X;
			int diffY = (tileY - camY) * 16 + Settings.CAM_Y;
			i.draw(diffX - offsetX, diffY - offsetY);
		}
		
	}

	@Override
	public int getTileX() {
		return tileX;
	}

	@Override
	public int getTileY() {
		return tileY;
	}

	@Override
	public void setTileX(int tileX) {
		this.tileX = tileX;	
	}

	@Override
	public void setTileY(int tileY) {
		this.tileY = tileY;	
	}

}
