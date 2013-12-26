package com.cscguru.client.ui;

import org.newdawn.slick.geom.Vector2f;

/**Static class that maintains all the settings.
 * @author Bryan Bennett
 * @date Dec 5, 2013
 */
public abstract class Settings {
	//display
	/**
	 * Resolution of the game (width)
	 */
	public static final int RES_W = 1600;
	/**
	 * Resolution of the game (height)
	 */
	public static final int RES_H = 900;
	/**
	 * The x-coordinate (in pixels) of the camera relative to the screen.
	 */
	public static final int CAM_X = 452;
	/**
	 * The y-coordinate (in pixels) of the camera relative to the screen.
	 */
	public static final int CAM_Y = 207;
	/**
	 * A vector containing the CAM_X and CAM_Y values.
	 */
	public static final Vector2f ORIGIN = new Vector2f(CAM_X, CAM_Y);
	/**
	 * The display width of the camera.
	 */
	public static final int CAM_W = 672;
	/**
	 * The display height of the camera.
	 */
	public static final int CAM_H = 672;
	/**
	 * The speed at which the camera moves, effectively the player's moving speed.
	 */
	public static final float CAM_SPEED = .17f;
	/**
	 * The x-coordinate (in pixels) of where the player's sprite is to be centered.
	 */
	public static final float PLAYER_X = 772;
	/**
	 * The y-coordinate (in pixels) of where the player's sprite is to be centered.
	 */
	public static final float PLAYER_Y = 527;
	/**
	 * A vector containing PLAYER_X and PLAYER_Y.
	 */
	public static final Vector2f CENTER = new Vector2f(PLAYER_X, PLAYER_Y);
	
	//agents
	/**
	 * Normal movement speed of agents.
	 */
	public static final float NORM_SPD = .17f;
	/**
	 * Running speed of agents.
	 */
	public static final float RUN_SPD  = .37f;
	
	//items
	/**
	 * Maximum number needs for a common item to drop.
	 */
	public static final int COM_ROLL = 90;
	/**
	 * Maximum number needs for an uncommon to drop (effectively 91-95);
	 */
	public static final int UNCOM_ROLL = 98;
}
