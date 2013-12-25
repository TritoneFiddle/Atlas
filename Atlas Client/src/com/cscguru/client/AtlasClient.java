package com.cscguru.client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.cscguru.client.states.AtlasLogin;
import com.cscguru.client.states.CharacterCreation;
import com.cscguru.client.ui.Settings;

/**Starts the state-based game Atlas. t
 * @author Bryan Bennett
 * @date 12/1/2013
 */
public class AtlasClient extends StateBasedGame {
	public static final String gamename = "Atlas";
	public static final int login = 0;
	public static final int charCreation = 1;
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

	/**Starts the game.
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer appgc = new AppGameContainer(new AtlasClient(gamename));
		appgc.setDisplayMode(Settings.RES_W, Settings.RES_H,true);
		appgc.setUpdateOnlyWhenVisible(false);
		appgc.setShowFPS(false);
		appgc.setVSync(true);
		appgc.setTargetFrameRate(60);
		appgc.start();
	}
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.enterState(charCreation);
	}

}
