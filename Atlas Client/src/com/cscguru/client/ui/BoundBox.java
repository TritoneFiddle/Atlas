package com.cscguru.client.ui;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.interfaces.IClickable;

/**Entity-like base class to provide an interface for clickable objects (such as the inventory blocks).
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public abstract class BoundBox extends Rectangle implements IClickable {
	private static final long serialVersionUID = 1L;

	/**Constructs a BoundBox at (x,y) with a width and height.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public BoundBox(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	/**Constructs a BoundBox at a vector with a width and height.
	 * @param v
	 * @param width
	 * @param height
	 */
	public BoundBox(Vector2f v, float width, float height){
		super(v.x, v.y, width, height);
	}
}
