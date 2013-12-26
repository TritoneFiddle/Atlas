package com.cscguru.client.entities;

import java.util.Random;

/**Holds the information that pertains to a mob such as minimum and maximum damage, hp, level, bonus
 * experience and more.
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class MobStat {
	private boolean isHostile;
	private int minDmg;
	private int maxDmg;
	private int hp;
	private int maxHP;
	private int level;
	private int bonusXP;
	private int bonusGold;
	private float missChance;
	private boolean hasItem;
	private String itemName;
	private float itemChance;
	private String imageName;
	private int sight = 0;
	
	private String name;
	/**Initializes  a new MobStat instance.  Usually only instanced once for the spawners to hold.
	 * @param minDmg
	 * @param maxDmg
	 * @param hp
	 * @param level
	 * @param name
	 * @param isHostile
	 */
	public MobStat(int minDmg, int maxDmg, int hp, int level, String name, boolean isHostile){
		this.isHostile = isHostile;
		this.setMinDmg(minDmg);
		this.setMaxDmg(maxDmg);
		this.hp = hp;
		maxHP = hp;
		this.level = level;
		this.name = name;
	}
	/**Copies the MobStat.  Usually used to respawn a mob via the spawner.
	 * @param m
	 */
	public MobStat(MobStat m){
		this.isHostile = m.isHostile();
		this.minDmg = m.getMinDmg();
		this.maxDmg = m.getMaxDmg();
		this.maxHP = m.getMaxHP();
		this.hp = maxHP;
		this.level = m.getLevel();
		this.name = m.getName();
		this.sight = m.getSight();
		this.bonusXP = m.getBonusXP();
		this.bonusGold = m.getBonusGold();
		this.itemName = m.getItemName();
		this.hasItem = m.hasItem();
		this.itemChance = m.getItemChance();
		this.missChance = m.getMissChance();
		this.imageName = m.getImageName();
	}
	/**
	 * Default constructor.
	 */
	public MobStat(){
		
	}
	/**Returns a single instance of damage between the minimum and maximum values of the mob's damage..
	 * @return int
	 */
	public int getDmg() {
		Random r = new Random();
		int k = r.nextInt(maxDmg - minDmg + 1) + minDmg;
		return k;
	}
	/**Returns the current hit points of the mob.
	 * @return int
	 */
	public int getHp() {
		return hp;
	}
	/**Sets the mob's hit points.
	 * @param val
	 */
	public void setHP(int val){
		hp = val;
		maxHP = hp;
	}
	/**Returns the mob's maximum hit points.
	 * @return int
	 */
	public int getMaxHP(){
		return maxHP;
	}
	/**Reduces the hitpoints of the mob by the integer value.
	 * @param val
	 */
	public void takeDamage(int val) {
		this.hp -= val;
		if (hp <= 0){
			hp = 0;
		}
	}
	/**Returns the level of the mob.
	 * @return int
	 */
	public int getLevel() {
		return level;
	}
	/**Sets the mob's level.
	 * @param lvl
	 */
	public void setLevel(int lvl){
		level = lvl;
	}
	/**Returns the name of the mob.
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**Sets the mob's name.
	 * @param n
	 */
	public void setName(String n){
		name = n;
	}
	/**Returns whether or not the mob is hostile.
	 * @return boolean
	 */
	public boolean isHostile() {
		return isHostile;
	}
	/**Sets whether or not the mob is hostile.
	 * @param val
	 */
	public void setHostile(boolean val){
		isHostile = val;
	}
	/**Returns the mob's minimum damage.
	 * @return int
	 */
	public int getMinDmg() {
		return minDmg;
	}
	/**Sets the mob's minimum damage.
	 * @param minDmg
	 */
	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}
	/**Returns the mobs maximum damage.
	 * @return int
	 */
	public int getMaxDmg() {
		return maxDmg;
	}
	/**Sets the mobs maximum damage.
	 * @param maxDmg
	 */
	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}
	/**Returns the bonus experience this mob gives if killed.
	 * @return int
	 */
	public int getBonusXP() {
		return bonusXP;
	}
	/**Sets the bonus experience this mob gives if killed.
	 * @param bonusXP
	 */
	public void setBonusXP(int bonusXP) {
		this.bonusXP = bonusXP;
	}
	/**Returns the bonus gold this mob gives if killed.
	 * @return int
	 */
	public int getBonusGold() {
		return bonusGold;
	}
	/**Sets the bonus gold this mob gives if killed.
	 * @param bonusGold
	 */
	public void setBonusGold(int bonusGold) {
		this.bonusGold = bonusGold;
	}
	/**Returns the miss chance of this mob's attack swing.
	 * @return float
	 */
	public float getMissChance() {
		return missChance;
	}
	/**Sets the miss chance of this mob's attack swing.
	 * @param missChance
	 */
	public void setMissChance(float missChance) {
		this.missChance = missChance;
	}
	/**Returns whether or not this mob holds a specific item to drop.
	 * @return boolean
	 */
	public boolean hasItem() {
		return hasItem;
	}
	/**Sets whether or not this mob holds a specific item to drop.
	 * @param hasItem
	 */
	public void setHasItem(boolean hasItem) {
		this.hasItem = hasItem;
	}
	/**Returns the name of the item this mob holds if hasItem() == true.
	 * @return String
	 */
	public String getItemName() {
		return itemName;
	}
	/**Sets the item's name this mob holds.
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**Returns the name of the image associated with this mob.
	 * @return String
	 */
	public String getImageName() {
		return imageName;
	}
	/**Sets the name of the image associated with this mob.
	 * @param imageName
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**Returns the drop chance of the held item if hasItem() == true.
	 * @return float
	 */
	public float getItemChance() {
		return itemChance;
	}
	/**Sets the drop chance of the held item.
	 * @param itemChance
	 */
	public void setItemChance(float itemChance) {
		this.itemChance = itemChance;
	}
	/**Returns the sight value (aggro range) of this mob.
	 * @return int
	 */
	public int getSight() {
		return sight;
	}
	/**Sets the sight value (aggro range) of this mob.
	 * @param val
	 */
	public void setSight(int val) {
		this.sight = val;
	}
}
