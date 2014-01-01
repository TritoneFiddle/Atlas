package com.cscguru.client.interfaces;

import org.newdawn.slick.Graphics;

/**Interface for objects that must have a frame of reference attached to the tiled map and the camera offset.
 * @author Bryan Bennett
 * @date Jan 1, 2014
 */
public interface ITiled {

	/**Overridable method that provides a way to render graphics onto the screen with the appropriate offset passed in by the camera.
	 * @param g
	 * @param offsetX
	 * @param offsetY
	 */
	public void draw(Graphics g, int offsetX, int offsetY);
	/**Returns the x-coordinate in tiles of this component.
	 * @return int
	 */
	public int getTileX();
	/**Returns the y-coordinate in tiles of this component.
	 * @return int
	 */
	public int getTileY();
	/**Sets the x-coordinate in tiles of this component.
	 * @param tileX 
	 * 
	 */
	public void setTileX(int tileX);
	/**Sets the y-coordinate in tiles of this component.
	 * @param tileY
	 */
	public void setTileY(int tileY);
}
