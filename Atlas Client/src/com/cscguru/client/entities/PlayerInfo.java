package com.cscguru.client.entities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import org.newdawn.slick.Color;

import com.cscguru.client.enums.CType;
import com.cscguru.client.ui.Log;
import com.cscguru.com.balance.Balance;

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
	private int modifiedHP;
	private DecimalFormat df;
	private Log log;
	
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

	public String getName() {
		return name;
	}
	public CType getType() {
		return type;
	}
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
	public float getHp() {
		return hp;
	}
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
	public void takeDamage(float d){
		if (d <= 0){
			d = 1;
		}
		changeHP(d * -1);
	}
	public float getMaxHP() {
		return modifiedHP;
	}
	public void modifyMaxHP(int val){
		modifiedHP = (int) (maxHP + val);
	}
	public float getMp() {
		return mp;
	}
	public void changeMp(float mp){
		this.mp += mp;
	}
	public float getMaxMP() {
		return maxMP;
	}
	public float getStam() {
		return stam;
	}

	public void changeStam(float stam) {
		this.stam += stam;
	}
	public float getMaxStam() {
		return maxStam;
	}
	public int getGold() {
		return gold;
	}
	public void changeGold(int gold) {
		this.gold += gold;
	}

	public float getXp() {
		return xp;
	}

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

	public float getMaxXP() {
		return maxXP;
	}

	public int getLevel() {
		return level;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getDamage() {
		Random r = new Random();
		int k = r.nextInt(maxDmg - minDmg + 1) + minDmg;
		return k;
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

	public float getDmgReduct() {
		float k =(float) (armor/(level * Balance.REDUCT_RATIO + Balance.REDUCT_BASE));
		if (k > .3f){
			return .3f;
		}
		String s = df.format(k);
		return Float.parseFloat(s);
	}
}
