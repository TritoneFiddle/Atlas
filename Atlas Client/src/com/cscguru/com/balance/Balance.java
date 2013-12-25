package com.cscguru.com.balance;

/**Static class for class balances.
 * @author Bryan Bennett
 * @date Dec 5, 2013
 */
public abstract class Balance {
	//stats
		public static final float START_HP = 100f;
		public static final float START_MP = 100f;
		public static final float START_STAM = 1000f;
		public static final int START_GOLD = 50;
		public static final int START_MAXXP = 1000;
		public static final float XP_RATIO = 2.1f;
		public static final float REDUCT_RATIO = .25f;
		public static final float REDUCT_BASE = 10;
	
	//mobs
		public static final float PIERCE_REDUCT = .05f;
		
	//knight stats
		public final static float HP_RATIO_K = 1.25f;
		public final static float MP_RATIO_K = 1.01f;

}
