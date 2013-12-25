package com.cscguru.client.ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.cscguru.client.entities.PlayerInfo;
import com.cscguru.client.interfaces.IDrawable;

public class StatScreen implements IDrawable{
	private PlayerInfo info;
	private int alignX = 43;
	private boolean isVisible = false;
	private DecimalFormat df;
	
	public StatScreen(PlayerInfo info) throws SlickException{
		this.info = info;
		df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
	}
	@Override
	public void draw(Graphics g) {
		if (isVisible){
			g.setColor(Color.green);
			g.drawString(info.getName() + " the " + info.getStringType(), alignX, 230);
			g.setColor(Color.yellow);
			g.drawString("Level " + info.getLevel(), alignX, 245);
			g.setColor(Color.white);
			g.drawLine(alignX, 275, alignX + 350, 275);
			g.drawString("    Health: ", alignX, 295);
			g.drawString("      Mana: ", alignX, 310);
			g.drawString("   Stamina: ", alignX, 325);
			g.drawString("Experience: ", alignX, 340);
			g.setColor(Color.green);
			g.drawString(info.getHp() + "     (max: " + info.getMaxHP() + ")", alignX + 100, 295);
			g.drawString(info.getMp() + "     (max: " + info.getMaxMP() + ")", alignX + 100, 310);
			g.drawString(Float.toString(info.getStam()), alignX + 100, 325);
			g.drawString(info.getXp() + "(" + ((int)(info.getXp() / (info.getMaxXP() * 1.0)*100)) + "%)", alignX + 100, 340);
			g.setColor(Color.white);
			g.drawLine(alignX, 380, alignX + 350, 380);
			g.setColor(Color.red);
			g.drawString("    Damage: ", alignX, 400);
			g.drawString("     Armor: ", alignX, 415);
			g.drawString(" Reduction: ", alignX, 430);
			g.setColor(Color.white);
			g.drawString(info.getMinDmg() + " to " + info.getMaxDmg(), alignX + 100, 400);
			g.drawString(Integer.toString(info.getArmor()), alignX + 100, 415);
			float k = info.getDmgReduct() * 100;
			g.drawString(df.format(k) + "%", alignX + 100, 430);
		}
	}
	public void equippedStats(int minDmg, int maxDmg, int armor, int plusHP){
		info.setMinDmg(minDmg);
		info.setMaxDmg(maxDmg);
		info.setArmor(armor);
		info.modifyMaxHP(plusHP);
	}
	public void setVisible(boolean val){
		isVisible = val;
	}
}
