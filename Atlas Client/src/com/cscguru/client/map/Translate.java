package com.cscguru.client.map;

import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.ui.Settings;

/**Static class for translating pixel points to tiled map points.
 * @author Bryan Bennett
 * @date Dec 3, 2013
 */
public abstract class Translate {
	/**Translates a pixel point to a tile point.
	 * @param v
	 * @return Vector2f
	 */
	public static Vector2f toTile(Vector2f v){
		int x = (int) (v.x - Settings.CAM_X) / 16;
		int y = (int) (v.y - Settings.CAM_Y) / 16;
		return new Vector2f (x, y);
	}
	/**Translates a pixel point to a tile point.
	 * @param v
	 * @return Vector2f
	 */
	public static Vector2f toPixel(Vector2f v){
		int x = (int)(v.x * 16) + Settings.CAM_X;
		int y = (int)(v.y * 16) + Settings.CAM_Y;
		return new Vector2f (x, y);
	}
	/**Translates a point to a vector.
	 * @param x1
	 * @param y1
	 * @return Vector2f
	 */
	public static Vector2f toPixel(int x1, int y1){
		int x = (int)(x1 * 16) + Settings.CAM_X;
		int y = (int)(y1 * 16) + Settings.CAM_Y;
		return new Vector2f(x, y);
	}
}
