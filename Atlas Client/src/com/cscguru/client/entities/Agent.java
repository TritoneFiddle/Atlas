package com.cscguru.client.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

import com.cscguru.client.interfaces.ITiled;
import com.cscguru.client.interfaces.IUpdatable;

/**Abstract class used for players and mobs.
 * @author Bryan Bennett
 * @date Dec 12, 2013
 */
public abstract class Agent extends Entity implements IUpdatable, ITiled{
	static final long serialVersionUID = 1L;
	protected int tileX;
	protected int tileY;
	private Facing face;
	protected Rectangle left;
	protected Rectangle right;
	protected Rectangle up;
	protected Rectangle down;
	protected Rectangle hitBox;
	protected Actor actor;
	protected Animation anim;

	/**Initializes an agent based on the passed parameters.  Constructor also creates an actor
	 * using the sprite parameter.  Sets the default facing value to Facing.SOUTH.  Also constructs 
	 * the hitboxes (bumpers) used for collision detection.  This constructor is primarily used for players.
	 * @param x
	 * @param y
	 * @param tileX
	 * @param tileY
	 * @param ss
	 */
	public Agent(float x, float y, int tileX, int tileY,Sprite ss) {
		super(x, y, ss.getSpriteWidth()  * 16, ss.getSpriteHeight() * 16);
		actor = new Actor(ss);
		this.tileX = tileX;
		this.tileY = tileY;
		
		//bumpers
		left = new Rectangle(x - 8, y + 8, 8,16);
		right = new Rectangle(x + 32, y + 8, 8,16);
		up = new Rectangle(x + 8, y - 8, 16,8);
		down = new Rectangle(x + 8, y + 32, 16,8);
		
		
		setFacing(Facing.SOUTH);
	}
	/**Initializes an agent based on the passed parameters.  Constructor stores the actor passed in.  Sets the default facing value to Facing.SOUTH.  Also constructs 
	 * the hitboxes (bumpers) used for collision detection.  This constructor primarily used for mobs.
	 * @param x
	 * @param y
	 * @param tileX
	 * @param tileY
	 * @param actor
	 */
	public Agent(float x, float y, int tileX, int tileY,Actor actor) {
		super(x, y, actor.getSpriteWidth() * 16, actor.getSpriteHeight() * 16);
		this.actor = actor;
		this.tileX = tileX;
		this.tileY = tileY;
		int w = actor.getSpriteWidth();
		int h = actor.getSpriteHeight();
		int mod = 0;
		if (h > 32){
			mod = 16;
		}
		
		//bumpers
		left = new Rectangle(x - 8, y + 8, 8,h/2 + mod);
		right = new Rectangle(x + w, y + 8, 8,h/2 + mod);
		up = new Rectangle(x + 8, y - 8, w/2 + mod,8);
		down = new Rectangle(x + 8, y + h, w/2 + mod,8);
		
		
		setFacing(Facing.SOUTH);
	}
	

	@Override
	public int getTileX() {
		return tileX;
	}

	@Override
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	@Override
	public int getTileY() {
		return tileY;
	}

	@Override
	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	/**Changes the facing of the agent to either east, north, south, or west.
	 * @param face
	 */
	public void setFacing(Facing face){
		this.face = face;
		if (face == Facing.EAST){
			hitBox = right;
		}
		else if (face == Facing.WEST){
			hitBox = left;
		}
		else if (face == Facing.SOUTH){
			hitBox = down;
		}
		else{
			hitBox = up;
		}
		anim = actor.getAnimation(this);
	}
	/**Returns the facing of the agent.
	 * @return Facing
	 */
	public Facing getFacing(){
		return face;
	}
	/**Returns the current bumper based on the current facing value of the agent.
	 * @return Rectangle
	 */
	public Rectangle getHitBox(){
		return hitBox;
	}
	/**Returns the actor class that holds the agent's animations.
	 * @return Actor
	 */
	public Actor getActor(){
		return actor;
	}
}
