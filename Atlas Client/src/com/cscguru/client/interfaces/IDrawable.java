package com.cscguru.client.interfaces;

import org.newdawn.slick.Graphics;

/**Simple interface for objects that can be drawn to the screen.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public interface IDrawable {
	
/**Draw an object to the screen via the graphics object.
 * @param g
 */
public void draw(Graphics g);
}
