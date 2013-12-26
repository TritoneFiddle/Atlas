package com.cscguru.client.entities;

import org.newdawn.slick.Animation;

/**Class that handles the animations for agents (mobs, players).  It is quite literally the "face" of
 * an agent.
 * @author Bryan Bennett
 * @date 12/26/2013
 */
public class Actor{
	private Animation down;
	private Animation left;
	private Animation right;
	private Animation up;
	private Sprite ss;
	private int duration = 100;
	
	/**Initializes the actor.  Left, up, down, and right animations are created.
	 * @param ss
	 */
	public Actor(Sprite ss){
		this.ss = ss;
		/**
		 * Sprite indexes are column-major.
		 */
		down = new Animation(ss, 0,0,2,0, true, duration, false);
		down.setPingPong(true);
		left = new Animation(ss, 0,1,2,1, true, duration, false);
		left.setPingPong(true);
		right = new Animation(ss, 0, 2, 2, 2, true, duration, false);
		right.setPingPong(true);
		up = new Animation(ss, 0, 3, 2, 3, true, duration, false);
		up.setPingPong(true);
	}
	/**Returns the correct animation based on the current facing value of the agent.
	 * @param a
	 * @return Animation
	 */
	public Animation getAnimation(Agent a){
		Facing face = a.getFacing();
		if (face == Facing.EAST){
			return right;
		}
		else if (face == Facing.WEST){
			return left;
		}
		else if (face == Facing.SOUTH){
			return down;
		}
		else{
			return up;
		}
	}
	/**Returns the width of an individual sprite (not the width of the spritesheet).
	 * @return int
	 */
	public int getSpriteWidth(){
		return ss.getSpriteWidth();
	}
	/**Returns the height of an individual sprite (not the height of thet spritesheet).
	 * @return int
	 */
	public int getSpriteHeight(){
		return ss.getSpriteHeight();
	}
}
