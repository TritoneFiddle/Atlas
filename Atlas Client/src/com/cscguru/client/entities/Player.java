package com.cscguru.client.entities;

import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import com.cscguru.client.enums.CType;
import com.cscguru.client.ui.Log;

public class Player extends Agent{
	private static final long serialVersionUID = -6296025914537500112L;
	private PlayerInfo info;
	private int attackTime = 0;
	private int attackSpeed = 1000;
	private boolean isDead;
	private boolean isInvincible; //debug
	private Log log;
	public Player(float x, float y, Sprite ss, String name, CType type, int tileX, int tileY, Log log) {
		super(x,y,tileX,tileY, ss);
		anim = actor.getAnimation(this);
		this.log = log;
		info = new PlayerInfo(name, type, log);
	}

	@Override
	public void draw(Graphics g, int offsetX, int offsetY) {
		if (!isDead){
			anim.draw(getX(), getY());
		}
	}

	@Override
	public void update(int delta) {
		anim.update(delta);
	}
	public void updateTimers(int delta){
		attackTime += delta;
		if (attackTime > attackSpeed){
			attackTime = attackSpeed + 1;
		}
	}
	/**Starts the walking animation dependent on which way the player is current facing.
	 * @param face
	 */
	public void move(Facing face){
		setFacing(face);
		anim = actor.getAnimation(this);
		anim.start();
	}
	/**Starts the walking animation dependent on which way the player is current facing.
	 */
	public void move(){
		anim.start();
	}
	public PlayerInfo getInfo(){
		return info;
	}
	public void attack(ArrayList<Mob> mobs){
		if (attackTime >= attackSpeed){
			attackTime = 0;
			for (int i = 0; i < mobs.size(); i++){
				Mob mob = mobs.get(i);
				if (hitBox.intersects(mob)){
					mob.takeDamage(this);
					if (mob.isDead()){
						int mlvl = mob.getMobStat().getLevel();
						mobs.remove(i);
						info.changeXp(mlvl * 60);
						Random r = new Random();
						int k = r.nextInt(mlvl * 5);
						log.writeToLog("Received " + k + " gold and " + mlvl*60 + " experience.", Color.yellow);
						info.changeGold(k);
					}
				}
			}
		}
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	public void takeDamage(float d){
		info.takeDamage(d);
		if (info.getHp() <= 0 && !isInvincible){
			isDead = true;
		}
	}

	public boolean isInvincible() {
		return isInvincible;
	}

	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}
}
