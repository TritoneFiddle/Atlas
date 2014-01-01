package com.cscguru.client.enums;

/**Enum that provides a way to identify tile types.
 * @author Bryan Bennett
 * @date Dec 2, 2013
 */
public enum TileType {
	/**
	 * Generic tiles
	 */
	GENERIC,
	/**
	 * Solid tiles, generic collisions
	 */
	SOLID,
	/**
	 * Trigger tiles.  Triggers some event (teleportation, ambush, etc)
	 */
	TRIGGER
}
