package com.cscguru.client.ui;

import org.newdawn.slick.geom.Vector2f;

/**Static class that maintains all the settings.
 * @author Bryan Bennett
 * @date Dec 5, 2013
 */
public abstract class Settings {
	//display
	public static final int RES_W = 1600;
	public static final int RES_H = 900;
	public static final int CAM_X = 452;
	public static final int CAM_Y = 207;
	public static final Vector2f ORIGIN = new Vector2f(CAM_X, CAM_Y);
	public static final int CAM_W = 672;
	public static final int CAM_H = 672;
	public static final float CAM_SPEED = .17f;
	public static final float PLAYER_X = 772;
	public static final float PLAYER_Y = 527;
	public static final Vector2f CENTER = new Vector2f(PLAYER_X, PLAYER_Y);
	
	//agents
	public static final float NORM_SPD = .17f;
	public static final float RUN_SPD  = .37f;
	
	//items
	public static final int COM_ROLL = 90;
	public static final int UNCOM_ROLL = 95;
}
