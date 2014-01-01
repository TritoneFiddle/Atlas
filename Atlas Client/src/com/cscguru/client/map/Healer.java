package com.cscguru.client.map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import com.cscguru.client.interfaces.ITiled;

/**Handles all map objects that heal agents.
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class Healer extends Rectangle implements ITiled{
	private static final long serialVersionUID = 1L;
	private int tileX;
	private int tileY;
	/**Initializes an instance of a Healer class.
	 * @param x
	 * @param y
	 * @param tileX
	 * @param tileY
	 * @param width
	 * @param height
	 */
	public Healer(float x, float y, int tileX, int tileY, float width, float height) {
		super(x, y, width, height);
		this.setTileX(tileX);
		this.setTileY(tileY);
	}
	@Override
	public int getTileX() {
		return tileX;
	}
	@Override
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}
	@Override
	public int getTileY() {
		return tileY;
	}
	@Override
	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	@Override
	public void draw(Graphics g, int offsetX, int offsetY) {
		// Nothing
	}

}
