package com.cscguru.client.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.entities.Facing;
import com.cscguru.client.entities.Mob;
import com.cscguru.client.entities.Player;
import com.cscguru.client.environment.Cycle;
import com.cscguru.client.interfaces.IClickable;
import com.cscguru.client.interfaces.IDrawable;
import com.cscguru.client.interfaces.IUpdatable;
import com.cscguru.client.items.Item;
import com.cscguru.client.map.AtlasMap;
import com.cscguru.client.map.Spawn;
/**
 * Handles the display of the world map.
 * @author Bryan Bennett
 * @date Dec 11, 2013
 */
public class Camera extends BoundBox implements IUpdatable, IDrawable, IClickable {
	private static final long serialVersionUID = -2492757686537332720L;
	private Vector2f origin;
	private AtlasMap map;
	private boolean isMoving;
	private boolean drawBackground = true;
	private int tileX = 0;
	private int tileY = 0;
	private Player p;
	private Cycle c;
	
	//movement
	private float offsetX = 0;
	private float offsetY = 0;
	private float offset_X = 0;  //keeps the offset the same in case an update changes the offset during rendering
	private float offset_Y = 0;	 //also decreases shearing greatly.
	private int targetTileX = 0;
	private int targetTileY = 0;
	private Facing direction;
	
	//mobs
	private ArrayList<Spawn> spawns;
	private ArrayList<Mob> mobs;
	

	/**Initializes an instance of the camera object based on the origin and offset coordinates.
	 * @param origin
	 * @param offset
	 * @param map
	 * @param p
	 */
	public Camera(Vector2f origin, Vector2f offset, AtlasMap map, Player p){
		super(origin, Settings.CAM_H, Settings.CAM_W);
		this.origin = origin;
		this.map = map;
		this.p = p;
		c = new Cycle();
		tileX = (int)offset.x;
		tileY = (int)offset.y;
		targetTileX = tileX;
		targetTileY = tileY;
		spawns = map.getSpawns();
		mobs = new ArrayList<Mob>();
	}
	/**
	 * Changes the map.
	 */
	public void changeMap(){
		//TODO change map when player enters a building or dungeon
	}
	@Override
	public boolean checkMouseHover(Vector2f v) {
		for (int i = 0; i < mobs.size(); i++){
			mobs.get(i).checkMouseHover(v);
		}
		return false;
	}
	@Override
	public boolean checkMouseClick(Vector2f v, int button) {
		for (int i = 0; i < mobs.size(); i++){
			mobs.get(i).checkMouseClick(v, button);
		}
		return false;
	}
	@Override
	public void draw(Graphics g) {
		if (!p.isDead()){
		/**
		 * Flips between drawing the background and drawing the foreground.
		 * the offset_X and offset_Y keeps both translations in sync during the rendering process.  Reduces shearing.
		 */
			if (drawBackground){
				offset_X = offsetX;
				offset_Y = offsetY;
				g.translate((int)offset_X, (int)offset_Y);  //(int) casting to reduce horizontal shearing.
				map.render((int)origin.x,(int)origin.y, tileX, tileY, 42, 42, 1, false);
				map.render((int)origin.x,(int)origin.y, tileX, tileY, 42, 42, 2, false);
				ArrayList<Item> items = map.getItemList();
				for (int i = 0; i < items.size(); i++){
					if (items.get(i) != null){
						items.get(i).draw(tileX,tileY);
					}
				}
				g.translate((int)offset_X *-1,(int) offset_Y*-1);	//resets the graphics translation.
				drawBackground = false;
			}
			else{
				g.translate((int)offset_X, (int)offset_Y);
				for (int i = 0; i < spawns.size(); i++){
					Spawn spawn = spawns.get(i);
					if (spawn.hasMob()){
						Mob m = spawn.getMob();
						m.draw(g, tileX, tileY);
						if (m.isOnScreen() && !m.isListed()){
							m.setListed(true);
							mobs.add(m);
						}
						if (!m.isHooked() && m.isOnScreen() && m.withinAggroDistance(p) && m.isHostile()){
							m.setHooked(true, p);
						}
					}
				}
				map.render((int)origin.x,(int)origin.y, tileX, tileY, 42, 42, 3, false);
				map.render((int)origin.x,(int)origin.y, tileX, tileY, 42, 42, 4, false);
				
				//lighting
				g.setDrawMode(Graphics.MODE_ALPHA_MAP);
				g.clearAlphaMap();
				g.setColor(new Color(255,255,255,c.getAlpha()));
				g.fillRect(Settings.CAM_X, Settings.CAM_Y, 672, 672);
				map.drawLights(g, tileX, tileY, c.getAlpha());		
				g.translate((int)offset_X *-1,(int) offset_Y*-1);	//resets the graphics translation.
				
				g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
				g.setColor(new Color(0,0,0,c.getAlpha()));
				g.fillRect(Settings.CAM_X, Settings.CAM_Y, 672, 672);	
				g.setDrawMode(Graphics.MODE_NORMAL);
				g.clearAlphaMap();
				g.flush();
				drawBackground = true;
			}
		}
		else{
			g.setColor(Color.white);
			g.drawString("You are dead.", Settings.CAM_X + 290, Settings.CAM_Y + 300);
		}
	}
	@Override
	public void update(int delta) {
		c.update(delta);
		if (isMoving){
			switch (direction){
			case EAST:
				offsetX -= Settings.CAM_SPEED * delta;
				if (offsetX <= -16){
					isMoving = false;
				}
				break;
			case NORTH:
				offsetY += Settings.CAM_SPEED * delta;
				if (offsetY >= 16){
					isMoving = false;
				}
				break;
			case SOUTH:
				offsetY -= Settings.CAM_SPEED * delta;
				if (offsetY <= -16){
					isMoving = false;
				}
				break;
			case WEST:
				offsetX += Settings.CAM_SPEED * delta;
				if (offsetX >= 16){
					isMoving = false;
				}
				break;
			default:
				System.out.println("Problem with update.  Enum switch defaulted.");
				break;
			}
			if (!isMoving){
				offsetX = 0;
				offsetY = 0;
				tileX = targetTileX;
				tileY = targetTileY;
			}
		}
		else{
			
		}		
	}
	/**Returns whether or not the camera is in the middle of moving.
	 * @return boolean
	 */
	public boolean isMoving(){
		return isMoving;
	}
	/**Moves the camera in the direction of the facing value.
	 * @param face
	 */
	public void moveCamera(Facing face){
		direction = face;
		switch (face){
		case EAST:
			targetTileX = tileX + 1;
			break;
		case NORTH:
			targetTileY = tileY - 1;
			break;
		case SOUTH:
			targetTileY = tileY + 1;
			break;
		case WEST:
			targetTileX = tileX - 1;
			break;
		default:
			System.out.println("Problem with moveCamera.  Enum switch defaulted.");
			break;
		}
		isMoving = true;
	}
	/**Checks to see if the player has collided with a mob on-screen.
	 * @return boolean
	 */
	public boolean checkMobCollision(){
		for (int i = 0; i < mobs.size(); i++){
			Mob m = mobs.get(i);
			if (m.isDespawned()){
				mobs.remove(i);
				i -= 1;
				continue;
			}
			Rectangle pHitBox = p.getHitBox();
			if (!m.isOnScreen()){
				m.setListed(false);
				mobs.remove(i);
				continue;
			}
			if (pHitBox.intersects(m)){
				return true;
			}			
		}
		return false;
	}
	/**Returns the day and night cycle.
	 * @return Cycle
	 */
	public Cycle getCycle(){
		return c;
	}
	/**Returns an array list containing all the mobs currently on the screen.
	 * @return ArrayList<Mob>
	 */
	public ArrayList<Mob> getMobsOnScreen(){
		return mobs;
	}
}
