package com.cscguru.client.entities;

import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import com.cscguru.client.enums.CType;
import com.cscguru.client.ui.Log;

/**Authoritative class for player functions.  Contains the player's information, animations, and location (both
 * in pixel and tile).
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class Player extends Agent{
	private static final long serialVersionUID = -6296025914537500112L;
	private PlayerInfo info;
	private int attackTime = 0;
	private int attackSpeed = 1000;
	private boolean isDead;
	private boolean isInvincible; //debug
	private Log log;
	/**Initializes a new player with the parameters passed in.
	 * @param x
	 * @param y
	 * @param ss
	 * @param name
	 * @param type
	 * @param tileX
	 * @param tileY
	 * @param log
	 */
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
	/**Seperate update method to handle the player's timers such as attack speed.
	 * @param delta
	 */
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
	/**Returns the player's information.  This provides a way to access the stats of the player such as health, mana,
	 * experience, and more.
	 * @return PlayerInfo
	 */
	public PlayerInfo getInfo(){
		return info;
	}
	/**Player attempts to attack on-screen mobs if and only if the attack time exceeds his attack speed and if the player's current
	 * hitbox (bumper) intersects with a mob.
	 * @param mobs
	 */
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

	/**Returns whether or not the player is dead.
	 * @return boolean
	 */
	public boolean isDead() {
		return isDead;
	}

	/**Sets whether or not the player is dead.
	 * @param isDead
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	/**Method used to provide damage to the player.
	 * @param d
	 */
	public void takeDamage(float d){
		info.takeDamage(d);
		/**
		 * The isInvincible is for testing purposes only.
		 */
		if (info.getHp() <= 0 && !isInvincible){
			isDead = true;
		}
	}

	/**For testing only.  Returns whether or not the player is invincible.
	 * @return boolean
	 */
	public boolean isInvincible() {
		return isInvincible;
	}

	/**For testing only.  Sets whether or not the player is invincible.
	 * @param isInvincible
	 */
	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}
}
