package com.cscguru.client.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**Gamestate that handles logging in to the multiplayer server.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class AtlasLogin extends BasicGameState {
	private int stateID;
	
	/**Constructs a gamestate used for logging into the multiplayer server.
	 * @param id
	 */
	public AtlasLogin(int id){
		stateID = id;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return stateID;
	}

}
