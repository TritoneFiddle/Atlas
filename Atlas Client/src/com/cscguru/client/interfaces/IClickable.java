package com.cscguru.client.interfaces;

import org.newdawn.slick.geom.Vector2f;

/**Provides a way to interact with mouse events.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public interface IClickable {

	
/**Provides a way to check to see if the mouse is over the object.  Returns a boolean value that can be dumped or used.
 * @param v
 * @return boolean
 */
public boolean checkMouseHover(Vector2f v);
/**Provides a way to check to see if the mouse has clicked the object.  Returns a boolean value that can be dumped or used.
 * @param v
 * @param button
 * @return boolean
 */
public boolean checkMouseClick(Vector2f v, int button);
}
