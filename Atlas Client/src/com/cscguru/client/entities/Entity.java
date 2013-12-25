package com.cscguru.client.entities;

import org.newdawn.slick.geom.Rectangle;

/**Base class for handling objects that have bounds.
 * @author Bryan Bennett
 * @date 12/1/2013
 */
public class Entity extends Rectangle {
	private static final long serialVersionUID = 1L;
	/**Constructs an rectangular entity at (x,y) with a width and height.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Entity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

}
