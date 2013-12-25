package com.cscguru.client.enums;

/**Enum for item qualities.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public enum Quality {
	/**
	 * Generic items such as potions, gold, etc. (displayed white).
	 */
	BASIC, 
	/**
	 * Commonly found items (displayed white).
	 */
	COMMON, 
	/**
	 * Uncommonly found items (displayed green).
	 */
	UNCOMMON, 
	/**
	 * Rarely found items (displayed magenta).
	 */
	RARE, 
	/**
	 * Items found only via feats of strength (displayed orange).
	 */
	LEGENDARY;
}
