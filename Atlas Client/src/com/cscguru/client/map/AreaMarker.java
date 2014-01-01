package com.cscguru.client.map;

import org.newdawn.slick.geom.Rectangle;

/**Handles zoning on the map.  Allows the game to be able to detect when the player has entered
 * into a new zone and to display the relevant information on the GUI.
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class AreaMarker{
	private String name;
	private int tileX;
	private int tileY;
	private Rectangle r;
	
	/**Initializes an instance of the AreaMarker class.
	 * @param objX
	 * @param objY
	 * @param tileX
	 * @param tileY
	 * @param w
	 * @param h
	 * @param name
	 */
	public AreaMarker(float objX, float objY, int tileX, int tileY, int w, int h, String name){
		this.name = name;
		this.tileX = tileX;
		this.tileY = tileY;
		this.r = new Rectangle(objX, objY, w, h);
	}
	/**Returns the rectangle encompassing the entire marker.
	 * @return Rectangle
	 */
	public Rectangle getRectangle(){
		return r;
	}
	/**Returns the name of the zone the player is entering.
	 * @return String
	 */
	public String getName(){
		return name;
	}
	/**Returns the x-coordinate in tiles.
	 * @return int
	 */
	public int getTileX(){
		return tileX;
	}
	/**Returns the y-coordinate in tiles.
	 * @return int
	 */
	public int getTileY(){
		return tileY;
	}
}
