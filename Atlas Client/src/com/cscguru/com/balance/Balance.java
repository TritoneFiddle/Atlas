package com.cscguru.com.balance;

/**Static class for class balances.
 * @author Bryan Bennett
 * @date Dec 5, 2013
 */
public abstract class Balance {
	//stats
		/**
		 * Starting hit points of the player.
		 */
		public static final float START_HP = 100f;
		/**
		 * Starting mana of the player.
		 */
		public static final float START_MP = 100f;
		/**
		 * Starting stamina of the player.
		 */
		public static final float START_STAM = 1000f;
		/**
		 * Starting gold of the player.
		 */
		public static final int START_GOLD = 50;
		/**
		 * Starting max experience of the player.
		 */
		public static final int START_MAXXP = 1000;
		/**
		 * Ratio that determines how much experience the player needs to level up based on the previous level 
		 * max experience. (eg. experience needs for level 2:  1000; experience needs for level 3: 1000*2.1)
		 */
		public static final float XP_RATIO = 2.1f;
		/**
		 * This ratio gets multiplied by the player's level to determine how much armor the player needs
		 * for (although impossible) 100% damage reduction.
		 */
		public static final float REDUCT_RATIO = .25f;
		/**
		 * Base armor value needed to achieve (although impossible) 100% damage reduction.
		 */
		public static final float REDUCT_BASE = 10;
	
	//mobs
		/**
		 * This ratio gets multiplied by how many levels higher than the player the mob is.  For every level,
		 * the player's armor reduction drops by .05%. This calculation can go into the negative to add damage
		 * done to the player.
		 */
		public static final float PIERCE_REDUCT = .05f;
	
	//knight stats
		/**
		 * Leveling ratio for HP for knights.
		 */
		public final static float HP_RATIO_K = 1.25f;
		/**
		 * Leveling ratio for MP for knights.
		 */
		public final static float MP_RATIO_K = 1.01f;

}
