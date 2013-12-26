package com.cscguru.client.entities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import org.newdawn.slick.Color;

import com.cscguru.client.enums.CType;
import com.cscguru.client.ui.Log;
import com.cscguru.com.balance.Balance;

/**Holds the information that pertains to a player such as the player's health, 
 * mana, stamina, damage, armor, and more.
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class PlayerInfo {
	private String name;
	private float hp;
	private float maxHP;
	private float mp;
	private float maxMP;
	private float stam;
	private float maxStam;
	private int gold;
	private float xp;
	private float maxXP;
	private CType type;
	private int level;
	private int minDmg;
	private int maxDmg;
	private int armor;
	private int modifiedHP;  //this is the max HP based on items and is not necessarily the base HP.
	private DecimalFormat df;
	private Log log;
	
	/**Initializes an instance of PlayerInfo based on the information passed in.
	 * @param name
	 * @param type
	 * @param log
	 */
	public PlayerInfo(String name, CType type, Log log){
		this.name = name;
		this.type = type;
		this.log = log;
		df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_DOWN);
		maxXP = Balance.START_MAXXP;
		xp = 0;
		maxHP =  Balance.START_HP;
		modifiedHP = (int) maxHP;
		hp = maxHP;
		maxMP = Balance.START_MP;
		mp = maxMP;
		maxStam = Balance.START_STAM;
		stam = maxStam;
		level = 1;
		gold = Balance.START_GOLD;
	}

	/**Returns the name of the player.
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**Returns the class type of the player.
	 * @return CType
	 */
	public CType getType() {
		return type;
	}
	/**Returns the string representation of the class type of the player.
	 * @return String
	 */
	public String getStringType(){
		if (type == CType.KNIGHT){
			return "Knight";
		}
		else if (type == CType.MAGE){
			return "Mage";
		}
		else{
			return "Thief";
		}
	}
	/**Returns the current hit points of the player.
	 * @return float
	 */
	public float getHp() {
		return hp;
	}
	/**Changes the hit points based on the positivity or negativity of the passed in value.  Will not allow
	 * the hit points to fall below zero or exceed the maximum hit point value.
	 * @param hp
	 */
	public void changeHP(float hp){
		this.hp += hp;
		if (this.hp <= 0){
			this.hp = 0;
			log.writeToLog("You have been killed!");
		}
		if (this.hp > maxHP){
			this.hp = maxHP;
		}
	}
	/**Reduces the player's hit points based on the passed in value.
	 * @param val
	 */
	public void takeDamage(float val){
		if (val <= 0){
			val = 1;
		}
		changeHP(val * -1);
	}
	/**Returns the player's maximum hit points (base max + extra item hit points).
	 * @return float
	 */
	public float getMaxHP() {
		return modifiedHP;
	}
	/**Sets the modified hit point value.
	 * @param val
	 */
	public void modifyMaxHP(int val){
		modifiedHP = (int) (maxHP + val);
	}
	/**Returns the player's current mana.
	 * @return float
	 */
	public float getMp() {
		return mp;
	}
	/**Changes the player's mana based on the positivity or negativity of the value passed in.
	 * @param val
	 */
	public void changeMp(float val){
		this.mp += val;
	}
	/**Returns the maximum mana.
	 * @return float
	 */
	public float getMaxMP() {
		return maxMP;
	}
	/**Returns the current stamina of the player.
	 * @return float
	 */
	public float getStam() {
		return stam;
	}

	/**Changes the player's stamina based on the positivity or negativity of the value passed in.
	 * @param stam
	 */
	public void changeStam(float stam) {
		this.stam += stam;
	}
	/**Returns the player's maximum stamina.
	 * @return float
	 */
	public float getMaxStam() {
		return maxStam;
	}
	/**Returns the amount of gold the player has.
	 * @return int
	 */
	public int getGold() {
		return gold;
	}
	/**Changes the player's gold count based on the positivity or negativity of the value passed in.
	 * @param gold
	 */
	public void changeGold(int gold) {
		this.gold += gold;
	}

	/**Returns the player's current experience.
	 * @return float
	 */
	public float getXp() {
		return xp;
	}

	/**Changes the player's current experience passed on the positivity or negativity of the value passed
	 * in.  Also handles the leveling up of the player if the current experience exceeds the maximum experience
	 * and sets the new maximum experience along with the new level up stats.
	 * @param xp
	 */
	public void changeXp(float xp) {
		this.xp += xp;
		if (this.xp > maxXP){
			this.xp = 0;
			if (type == CType.KNIGHT){
				maxHP = maxHP * Balance.HP_RATIO_K;
				maxMP = maxMP * Balance.MP_RATIO_K;
			}
			else if (type == CType.MAGE){
				//TODO
			}
			else{
				//TODO
			}
			level += 1;
			log.writeToLog("You are now level " + level + "!", Color.magenta);
			maxXP = maxXP * Balance.XP_RATIO;
			hp = maxHP;
			mp = maxMP;
		}
	}

	/**Returns the maximum experience needed to level up.
	 * @return float
	 */
	public float getMaxXP() {
		return maxXP;
	}

	/**Returns the player's level.
	 * @return int
	 */
	public int getLevel() {
		return level;
	}

	/**Returns the player's armor value.
	 * @return int
	 */
	public int getArmor() {
		return armor;
	}

	/**Sets the player's armor value.
	 * @param armor
	 */
	public void setArmor(int armor) {
		this.armor = armor;
	}

	/**Randomizes the damage by generating a number between (inclusively) the player's minimum and maximum damage
	 * and returns that number. 
	 * @return int
	 */
	public int getDamage() {
		Random r = new Random();
		int k = r.nextInt(maxDmg - minDmg + 1) + minDmg;
		return k;
	}
	/**Returns the player's minimum damage.
	 * @return int
	 */
	public int getMinDmg() {
		return minDmg;
	}

	/**Sets the player's minimum damage.
	 * @param minDmg
	 */
	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}

	/**Returns the player's maximum damage.
	 * @return int
	 */
	public int getMaxDmg() {
		return maxDmg;
	}

	/**Sets the player's maximum damage.
	 * @param maxDmg
	 */
	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	/**Returns the damage reduction benefit based on the player's armor value and current level.
	 * @return float
	 */
	public float getDmgReduct() {
		float k =(float) (armor/(level * Balance.REDUCT_RATIO + Balance.REDUCT_BASE));
		if (k > .3f){
			return .3f;
		}
		String s = df.format(k);
		return Float.parseFloat(s);
	}
}
