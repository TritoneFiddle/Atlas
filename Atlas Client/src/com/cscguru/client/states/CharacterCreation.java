package com.cscguru.client.states;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import com.cscguru.client.entities.Player;
import com.cscguru.client.enums.CType;
import com.cscguru.client.managers.ResourceManager;
import com.cscguru.client.ui.Button;
import com.cscguru.client.ui.Settings;

/**Gamestate that allows the player to create his or her character.
 * @author Bryan Bennett
 * @date Dec 4, 2013
 */
public class CharacterCreation extends BasicGameState {
	private int stateID;
	private Player p;
	private GameState am;
	private Image knight;
	private Image mage;
	private Image thief;
	private ResourceManager rm;
	private ArrayList<Button> buttons;
	private float lastMouseX = 0;
	private float lastMouseY = 0;
	private TextField tf;
	/**Constructs the character creation gamestate.
	 * @param stateID
	 */
	public CharacterCreation(int stateID){
		this.stateID = stateID;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		am = (AtlasMain) sbg.getState(2);
		tf = new TextField(gc, gc.getDefaultFont(), 700,500,200,25);
		tf.setBackgroundColor(Color.white);
		tf.setTextColor(Color.black);
		
		buttons = new ArrayList<Button>();
		try {
			rm = new ResourceManager();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		knight = rm.getSprite("knight.png").getSubImage(0, 0,32,32);
		mage = rm.getSprite("mage.png").getSubImage(0, 0,32,32);
		thief = rm.getSprite("thief.png").getSubImage(0, 0,32,32);
		
		Button button1 = new Button(520, 400, "Choose Knight", 0);
		Button button2 = new Button(710, 400, "Choose Mage", 1);
		Button button3 = new Button(900, 400, "Choose Thief", 2);
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		knight.draw(594,360);
		mage.draw(784, 360);
		thief.draw(976, 360);
		g.setColor(Color.white);
		g.drawString("Name:", 650, 500);
		tf.render(gc, g);
		tf.setFocus(true);
		for (int i = 0; i < buttons.size(); i++){
			buttons.get(i).draw(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input in = gc.getInput();
		float mouseX = in.getMouseX();
		float mouseY = in.getMouseY();
		
		if (mouseX != lastMouseX || mouseY != lastMouseY){
			for (int i = 0; i < buttons.size();i++){
				buttons.get(i).checkMouseHover(new Vector2f(mouseX, mouseY));
			}
			lastMouseX = mouseX;
			lastMouseY = mouseY;
		}
		if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			for (int i = 0; i < buttons.size();i++){
				boolean temp = buttons.get(i).checkMouseClick(new Vector2f(mouseX, mouseY), 0);
				if (temp && tf.getText().length() > 0){
					int e = buttons.get(i).getButtonID();
					Vector2f point = rm.getMap("World_1.tmx").getStartLocation();
					int x = (int) point.x;
					int y = (int) point.y;
					switch (e){
					case 0:
						p = new Player(Settings.PLAYER_X, Settings.PLAYER_Y, rm.getSprite("knight.png"), tf.getText(), CType.KNIGHT, x, y, rm.getLog());
						break;
					case 1:
						p = new Player(Settings.PLAYER_X, Settings.PLAYER_Y, rm.getSprite("mage.png"),tf.getText(), CType.MAGE, x, y,rm.getLog());
						break;
					case 2:
						p = new Player(Settings.PLAYER_X, Settings.PLAYER_Y, rm.getSprite("thief.png"),tf.getText(), CType.THIEF,x,y,rm.getLog());
						break;
					}
					am = new AtlasMain(p,rm,2);
					sbg.addState(am);
					am.init(gc, sbg);
					sbg.enterState(2);
				}
			}
		}
		if (in.isKeyPressed(Input.KEY_ESCAPE)){
			System.exit(0);
		}
		
	}

	@Override
	public int getID() {
		return stateID;
	}
}
