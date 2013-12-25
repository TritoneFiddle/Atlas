package com.cscguru.client.entities;

import org.newdawn.slick.Animation;

public class Actor{
	private Animation down;
	private Animation left;
	private Animation right;
	private Animation up;
	private Sprite ss;
	private int duration = 100;
	
	public Actor(Sprite ss){
		this.ss = ss;
		down = new Animation(ss, 0,0,2,0, true, duration, false);
		down.setPingPong(true);
		left = new Animation(ss, 0,1,2,1, true, duration, false);
		left.setPingPong(true);
		right = new Animation(ss, 0, 2, 2, 2, true, duration, false);
		right.setPingPong(true);
		up = new Animation(ss, 0, 3, 2, 3, true, duration, false);
		up.setPingPong(true);
	}
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
	public int getSpriteWidth(){
		return ss.getSpriteWidth();
	}
	public int getSpriteHeight(){
		return ss.getSpriteHeight();
	}
}
