package com.cscguru.client.map;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;

/**Class handles fire graphics.
 * @author Bryan Bennett
 * @date Dec 3, 2013
 */
public class Fire extends ParticleSystem {
	private FireEmitter fire;
	private Vector2f v;
	/**Constructs a fire to be drawn at the vector.
	 * @param ref 
	 * @param v
	 */
	public Fire(String ref, Vector2f v) {
		super(ref);
		fire = new FireEmitter();
		addEmitter(fire);
		this.v = v;
		setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
	}
	/**
	 * Draws the fire at an (x,y) coordinate.
	 */
	public void drawFire(){
		render(v.x,v.y);
	}

}
