package com.cscguru.client.entities;

import java.util.Random;

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
	public MobStat(int minDmg, int maxDmg, int hp, int level, String name, boolean isHostile){
		this.isHostile = isHostile;
		this.setMinDmg(minDmg);
		this.setMaxDmg(maxDmg);
		this.hp = hp;
		maxHP = hp;
		this.level = level;
		this.name = name;
	}
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
		this.hasItem = m.isHasItem();
		this.itemChance = m.getItemChance();
		this.missChance = m.getMissChance();
		this.imageName = m.getImageName();
	}
	public MobStat(){
		
	}
	/**Returns the amount of damage the mob does.
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
	public void setHP(int val){
		hp = val;
		maxHP = hp;
	}
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
	public void setLevel(int lvl){
		level = lvl;
	}
	/**Returns the name of the mob.
	 * @return String
	 */
	public String getName() {
		return name;
	}
	public void setName(String n){
		name = n;
	}
	public boolean isHostile() {
		return isHostile;
	}
	public void setHostile(boolean val){
		isHostile = val;
	}
	public int getMinDmg() {
		return minDmg;
	}
	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}
	public int getMaxDmg() {
		return maxDmg;
	}
	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}
	public int getBonusXP() {
		return bonusXP;
	}
	public void setBonusXP(int bonusXP) {
		this.bonusXP = bonusXP;
	}
	public int getBonusGold() {
		return bonusGold;
	}
	public void setBonusGold(int bonusGold) {
		this.bonusGold = bonusGold;
	}
	public float getMissChance() {
		return missChance;
	}
	public void setMissChance(float missChance) {
		this.missChance = missChance;
	}
	public boolean isHasItem() {
		return hasItem;
	}
	public void setHasItem(boolean hasItem) {
		this.hasItem = hasItem;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public float getItemChance() {
		return itemChance;
	}
	public void setItemChance(float itemChance) {
		this.itemChance = itemChance;
	}
	public int getSight() {
		return sight;
	}
	public void setSight(int val) {
		this.sight = val;
	}
}
