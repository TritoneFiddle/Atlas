package com.cscguru.client.states;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.cscguru.client.entities.Facing;
import com.cscguru.client.entities.Player;
import com.cscguru.client.items.Item;
import com.cscguru.client.managers.ResourceManager;
import com.cscguru.client.map.AtlasMap;
import com.cscguru.client.ui.Camera;
import com.cscguru.client.ui.Gui;
import com.cscguru.client.ui.Settings;

/**Main class for the entire game.  Handles the logic, update, rendering, and user input.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class AtlasMain extends BasicGameState {
	private int stateID;
	private Gui gui;
	private Vector2f lastMouse;
	private boolean showDebug = false;
	private ResourceManager rm;
	private Player p;
	private Camera cam;
	private AtlasMap map;
	
	//debug
	private float mouseX;
	private float mouseY;
	
	/**Constructs the main gamestate with a specific ID.
	 * @param p 
	 * @param rm 
	 * @param id
	 */
	public AtlasMain(Player p, ResourceManager rm, int id){
		this.p = p;
		stateID = id;
		this.rm = rm;
		map = rm.getMap("World_1.tmx");
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		lastMouse = new Vector2f(0,0);
		cam = new Camera(Settings.ORIGIN, map.getCameraOffset(), map,p);
		try {
			gui = new Gui(rm, cam.getCycle(), p, rm.getLog());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		cam.draw(g);
		p.draw(g, 0,0);
		cam.draw(g);
		gui.draw(g);
		if (showDebug){
			boolean val = p.isInvincible();
			g.drawString("X: " + mouseX + "  Y: " + mouseY + "\nX: " + (mouseX - 470) + "  Y: " + (mouseY - 207) + "\nTileX: " + p.getTileX() + " TileY: " + p.getTileY() + "\nTotal Spawns: " + map.getTotalSpawns() + "\nTotal Items: " + map.getItemList().size() + "\nInvincibility: " + val + "\nLoot Chance: " + map.getLootChance(), mouseX + 25, mouseY );
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		gui.updateStats();
		rm.update(delta);
		map.checkZone(p);
		gui.setZone(map.getZone());
		p.updateTimers(delta);
		Input in = gc.getInput();
		Vector2f v = new Vector2f (in.getMouseX(), in.getMouseY());
		if (v != lastMouse){
			gui.checkMouseHover(v);
		}
		if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			gui.checkMouseClick(v,0);
			cam.checkMouseClick(v,0);
		}
		if (in.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
			gui.checkMouseClick(v, 1);
		}
		if (in.isKeyPressed(Input.KEY_G)){
			gui.addItem();
		}
		if (in.isKeyPressed(Input.KEY_ESCAPE)){
			System.exit(0);
		}
		if (in.isKeyPressed(Input.KEY_F1)){
			if (showDebug){
				showDebug = false;
				gc.setShowFPS(false);
			}
			else{
				showDebug = true;
				gc.setShowFPS(true);
			}
		}
		if (in.isKeyPressed(Input.KEY_F2)){
			boolean val = p.isInvincible();
			p.setInvincible(!val);
		}
		if (in.isKeyPressed(Input.KEY_F3)){
			int k = map.getLootChance();
			if (k >= 90){
				map.setLootChance(0);
			}
			else{
				map.setLootChance(90);
			}
		}
		if (in.isKeyPressed(Input.KEY_SPACE)){
			ArrayList<Item> items = map.getItemList();
			for (int i = 0; i < items.size(); i++){
				Item item = items.get(i);
				if (p.intersects(item) || p.contains(item)){
					int val = gui.isBagFull();
					if (val >= 0){
						gui.addItem(item, val);
						item.setOnGround(false);
						items.remove(i);
						i -= 1;
					}
				}
			}
		}
		if (in.isKeyDown(Input.KEY_E) || in.isMouseButtonDown(4)){
			p.attack(cam.getMobsOnScreen());
		}
		/**
		 * Player movement.
		 */
		if (cam.isMoving()){
			p.update(delta);
		}
		if (in.isKeyDown(Input.KEY_W) && !cam.isMoving()){
			p.setFacing(Facing.NORTH);
			if (map.canMove(p) && !cam.checkMobCollision()){
				cam.moveCamera(Facing.NORTH);
				p.move(Facing.NORTH);
				p.setTileY(p.getTileY() - 1);
			}
		}
		if (in.isKeyDown(Input.KEY_S) && !cam.isMoving()){
			p.setFacing(Facing.SOUTH);
			if (map.canMove(p) && !cam.checkMobCollision()){
				cam.moveCamera(Facing.SOUTH);
				p.move(Facing.SOUTH);
				p.setTileY(p.getTileY() + 1);
			}
		}
		if (in.isKeyDown(Input.KEY_A) && !cam.isMoving()){
			p.setFacing(Facing.WEST);
			if (map.canMove(p) && !cam.checkMobCollision()){
				cam.moveCamera(Facing.WEST);
				p.move(Facing.WEST);
				p.setTileX(p.getTileX() - 1);
			}
		}
		if (in.isKeyDown(Input.KEY_D) && !cam.isMoving()){
			p.setFacing(Facing.EAST);
			if (map.canMove(p) && !cam.checkMobCollision()){
				cam.moveCamera(Facing.EAST);
				p.move(Facing.EAST);
				p.setTileX(p.getTileX() + 1);
			}
		}
		if (in.isKeyDown(Input.KEY_LSHIFT)){
			
		}
		else if (!in.isKeyDown(Input.KEY_LSHIFT)){
			
		}
		
		if (map.isHealing(p)){
			float dx = (float) (p.getInfo().getMaxHP() * .001);
			p.getInfo().changeHP(dx);
		}
		
		//debug
		mouseX = in.getMouseX();
		mouseY = in.getMouseY();
		
		//camera update (must come after movements)
		cam.update(delta);
	}

	@Override
	public int getID() {
		return stateID;
	}
}
