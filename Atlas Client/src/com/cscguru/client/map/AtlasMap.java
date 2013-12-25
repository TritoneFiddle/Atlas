package com.cscguru.client.map;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.cscguru.client.entities.Agent;
import com.cscguru.client.entities.Facing;
import com.cscguru.client.entities.Mob;
import com.cscguru.client.entities.MobStat;
import com.cscguru.client.entities.Player;
import com.cscguru.client.interfaces.IUpdatable;
import com.cscguru.client.items.Item;
import com.cscguru.client.managers.ResourceManager;
import com.cscguru.client.ui.Log;
import com.cscguru.client.ui.Settings;

public class AtlasMap extends TiledMap implements IUpdatable{
	private ResourceManager rm;
	private Log log;
	private String zone = "";
	//integer that represents the first tile in the alpha tileset
	private int collisionID;
	//collision array
	private int[][] red;
	private int startX;
	private int startY;
	//map objects
	private ArrayList<Spawn> spawns;
	private ArrayList<AreaMarker> markers;
	private ArrayList<Healer> healers;
	private int totalSpawns;
	private int lootChance = 90;
	
	private ArrayList<Item> items;
	
	
	public AtlasMap(String ref, ResourceManager rm) throws SlickException {
		super(ref);
		this.rm = rm;
		log = rm.getLog();
		items = new ArrayList<Item>();
		healers = new ArrayList<Healer>();

		//Building the collision array (column-major)
		collisionID = this.getTileSet(0).firstGID;
		red = new int[getWidth()][getHeight()];
		for (int i = 0; i < getHeight(); i++){
			for (int j = 0; j < getWidth(); j++){
				int tileID = getTileId(j,i,0);
				if (tileID == collisionID){
					red[j][i] = 1;
				}
			}
		}
		
		//Building map objects
		spawns = new ArrayList<Spawn>();
		markers = new ArrayList<AreaMarker>();
		int groupCount = getObjectGroupCount();
		for (int i = 0; i < groupCount; i++){
			int objCount = getObjectCount(i);
			for (int j = 0; j < objCount; j++){
				String s = getObjectType(i, j);
				int objX = getObjectX(i,j);
				int objY = getObjectY(i,j);
				if (s.equals("Spawn")){
					s = getObjectProperty(i,j,"name", "");
					MobStat ms = rm.getMobStat(s);
					s = ms.getImageName();
					Spawn spawn = new Spawn(objX, objY, rm.getSprite(s), ms, this);
					spawns.add(spawn);
					totalSpawns += 1;
				}
				else if (s.equals("Start")){
					int x = objX/16;
					int y = objY/16;
					startX = x;
					startY = y;
				}
				else if (s.equals("Healer")){
					int width = getObjectWidth(i, j);
					int height = getObjectHeight(i,j);
					int tx = objX / 16;
					int ty = objY  /16;
					objX += Settings.CAM_X;
					objY += Settings.CAM_Y;
					Healer heal = new Healer(objX,objY,tx,ty,width,height);
					healers.add(heal);
				}
				else if (s.equals("Area")){
					int tx = objX / 16;
					int ty = objY / 16;
					int w = getObjectWidth(i, j);
					int h = getObjectHeight(i,j);
					String temp = getObjectProperty(i, j, "name", "N/A");
					objX += Settings.CAM_X;
					objY += Settings.CAM_Y;
					AreaMarker marker = new AreaMarker(objX, objY, tx, ty, w, h, temp);
					markers.add(marker);
				}
				
			}
		}
		
	}

	@Override
	public void update(int delta) {
		for (int i = 0; i < spawns.size(); i++){
			spawns.get(i).update(delta);
		}
		for (int i = 0; i < items.size(); i++){
			if (items.get(i) != null){
				items.get(i).update(delta);
				if (items.get(i).isStale()){
					items.remove(i);
				}
			}
		}
	}
	/**Returns whether or not the player can move.  True case grants movement.
	 * @param a
	 * @return boolean
	 */
	public boolean canMove(Agent a){
		Facing face = a.getFacing();
		int x = a.getTileX();
		int y = a.getTileY();
		
		int w = a.getActor().getSpriteWidth();
		int h = a.getActor().getSpriteHeight();
		
			switch (face){
			case EAST:
				if (x + w >= getWidth()){
					return false;
				}
				for (int i = 0; i < h; i++){
					if (red[x + w][y + i] == 1){
						return false;
					}
				}
				break;
			case NORTH:
				if (y - 1 < 0){
					return false;
				}
				for (int i = 0; i < w; i++){
					if (red[x + i][y - 1] == 1){
						return false;
					}
				}
				break;
			case SOUTH:
				if (y + h >= getHeight()){
					return false;
				}
				for (int i = 0; i < w; i++){
					if (red[x + i][y + h] == 1){
						return false;
					}
				}
				break;
			case WEST:
				if (x - 1 < 0){
					return false;
				}
				for (int i = 0; i < h; i++){
					if (red[x - 1][y + i] == 1){
						return false;
					}
				}
				break;
			default:
				break;
		
			}
		return true;
	}
	/**Returns the offset for the camera.  This offset is based off of where the player starts in the world, so that the correct camera is offsetted to the correct x and y tile coordinates.
	 * @return Vector2f
	 */
	public Vector2f getCameraOffset(){
		return new Vector2f (startX - 20, startY - 20);
	}
	/**Returns the player's starting location.
	 * @return Vector2f
	 */
	public Vector2f getStartLocation(){
		return new Vector2f (startX, startY);
	}
	/**Returns the array containing all the spawns on the map.
	 * @return Spawn[][]
	 */
	public ArrayList<Spawn> getSpawns(){
		return spawns;
	}
	public int getTotalSpawns(){
		return totalSpawns;
	}
	public void runLootTable(Mob m){
		int mlvl = m.getMobStat().getLevel();
		Vector2f v = new Vector2f(m.getTileX() * 16 + Settings.CAM_X, m.getTileY() * 16 + Settings.CAM_Y);
		Random r = new Random();
		int k = r.nextInt(100) + 1;
		if (k > lootChance){
			Item item = rm.getItem(mlvl, v, m.getTileX(), m.getTileY());
			if (item != null){
				items.add(item);
			}
		}
	}
	public ArrayList<Item> getItemList(){
		return items;
	}
	public boolean isHealing(Player p){
		for (int i = 0; i < healers.size(); i++){
			Healer heals = healers.get(i);
			int tileX = heals.getTileX();
			int tileY = heals.getTileY();
			int pX = p.getTileX();
			int pY = p.getTileY();
			int j = Math.abs(pX - tileX);
			int k = Math.abs(pY - tileY);
			if (j > 20 || k > 20){
				continue;
			}
			int x = tileX - (p.getTileX() - 20);
			int y = tileY - (p.getTileY() - 20);
			x = (x * 16) + Settings.CAM_X;
			y = (y * 16) + Settings.CAM_Y;
			heals.setX(x);
			heals.setY(y);
			if (heals.intersects(p.getHitBox())){
				return true;
			}
		}
		return false;
	}
	public Log getLog(){
		return log;
	}
	public String getZone(){
		return zone;
	}
	public void checkZone(Player p){
		for (int i = 0; i < markers.size(); i++){
			AreaMarker marks = markers.get(i);
			int tileX = marks.getTileX();
			int tileY = marks.getTileY();
			int pX = p.getTileX();
			int pY = p.getTileY();
			int j = Math.abs(pX - tileX);
			int k = Math.abs(pY - tileY);
			if (j > 20 || k > 20){
				continue;
			}
			int x = tileX - (p.getTileX() - 20);
			int y = tileY - (p.getTileY() - 20);
			x = (x * 16) + Settings.CAM_X;
			y = (y * 16) + Settings.CAM_Y;
			Rectangle r = marks.getRectangle();
			r.setX(x);
			r.setY(y);
			if (r.intersects(p) || r.contains(p)){
				zone = marks.getName();
			}
		}
	}
	public void addItem(Item item){
		items.add(item);
	}
	public void setLootChance(int k){
		lootChance = k;
	}
	public int getLootChance(){
		return lootChance;
	}

}