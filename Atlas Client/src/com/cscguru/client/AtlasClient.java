package com.cscguru.client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.cscguru.client.states.AtlasLogin;
import com.cscguru.client.states.CharacterCreation;
import com.cscguru.client.ui.Settings;

/**Starts the state-based game Atlas.
 * @author Bryan Bennett
 * @date 12/1/2013
 */
public class AtlasClient extends StateBasedGame {
	/**
	 * String that holds the name of the game.  Also the string that appears in the title bar when running 
	 * in windowed mode.
	 */
	public static final String gamename = "Atlas";
	/**
	 * Numeric value that identifies the login game state.
	 */
	public static final int login = 0;
	/**
	 * Numeric value that identifies the character creation screen.
	 */
	public static final int charCreation = 1;
	/**
	 * Numeric value that identifies the main game state.
	 */
	public static final int main = 2;

	/**Constructor for the game.
	 * @param gamename
	 */
	public AtlasClient(String gamename) {
		super(gamename);
		this.addState(new AtlasLogin(login));
		this.addState(new CharacterCreation(charCreation));
		//this.addState(new AtlasMain(main));
	}

	/**Starts the game.  Initializes the application game container.
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		System.out.println(System.getProperty("java.library.path"));
		AppGameContainer appgc = new AppGameContainer(new AtlasClient(gamename));
		appgc.setDisplayMode(Settings.RES_W, Settings.RES_H,true);
		appgc.setUpdateOnlyWhenVisible(false);
		appgc.setShowFPS(false);
		/**
		 * setVSync(true) gets rid of shearing.
		 */
		appgc.setVSync(true);
		appgc.setTargetFrameRate(60);
		appgc.start();
	}
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.enterState(charCreation);
	}

}
