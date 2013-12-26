package com.cscguru.client.map;

import org.newdawn.slick.geom.Rectangle;

/**Handles all map objects that heal agents.
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class Healer extends Rectangle {
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
	/**Returns the x-coordinate (in tiles) of the healer.
	 * @return int
	 */
	public int getTileX() {
		return tileX;
	}
	/**Sets the x-coordinate (in tiles) of the healer.
	 * @param tileX
	 */
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}
	/**Returns the y-coordinate (in tiles) of the healer.
	 * @return int
	 */
	public int getTileY() {
		return tileY;
	}
	/**Sets the y-coordinate (in tiles) of the healer.
	 * @param tileY
	 */
	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

}
