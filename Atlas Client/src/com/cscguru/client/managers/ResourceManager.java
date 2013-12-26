package com.cscguru.client.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.entities.MobStat;
import com.cscguru.client.entities.Sprite;
import com.cscguru.client.enums.ItemType;
import com.cscguru.client.enums.Quality;
import com.cscguru.client.items.Armor;
import com.cscguru.client.items.Boots;
import com.cscguru.client.items.Helm;
import com.cscguru.client.items.Item;
import com.cscguru.client.items.ItemInfo;
import com.cscguru.client.items.OffHand;
import com.cscguru.client.items.Weapon;
import com.cscguru.client.map.AtlasMap;
import com.cscguru.client.ui.Gui;
import com.cscguru.client.ui.Log;
import com.cscguru.client.ui.Settings;

/**Class that loads all content into the game.
 * @author Bryan Bennett
 * @date Dec 5, 2013
 */
public class ResourceManager {
		//item infos
		private IPXParser ipx;
		
		//mob infos
		private MPXParser mpx;
		//paths
		private String imgPath = "res/items/images/";
		private String itemPath = "res/items/iteminfo/items.ipx";
		private String mobPath = "res/sprites/packages/mobs.mpx";
		//roll values
		private int commonRoll = Settings.COM_ROLL;
		private int uncommonRoll = Settings.UNCOM_ROLL;
		
		//maps
		private HashMap<String, AtlasMap> maps;
		private String mapPath = "res/tilesets/";
		private Set<String> mapKeys;
		private Gui gui;
		
		//sprites
		private String path = "res/sprites/";
		private HashMap<String, Sprite> sprites;
		
		//misc
		private Log log;
		
		/**Constructs the resource manager and loads all content into the game.
		 * @throws FileNotFoundException
		 * @throws SlickException
		 */
		public ResourceManager() throws FileNotFoundException, SlickException{
			//misc
			log = new Log();
			//items
			ipx = new IPXParser(itemPath, imgPath);	
			//sprites
			sprites = new HashMap<String,Sprite>();
			loadSprites();
			//mobs
			mpx = new MPXParser(mobPath);
			//maps
			maps = new HashMap<String, AtlasMap>();
			loadMaps();
			mapKeys = maps.keySet();
			spriteDimensions();
		}
		/**Returns an item based on the level of the monster kills and places it at the vector the monster was killed at.
		 * @param monsterLevel
		 * @param v
		 * @param tileX 
		 * @param tileY 
		 * @return Item
		 */
		public Item getItem(int monsterLevel, Vector2f v, int tileX, int tileY){
			int min = monsterLevel - 2;
			monsterLevel += 1;
			if (min < 0){
				min = 0;
			}
			Random r = new Random();
			int firstRoll = r.nextInt(100) + 1;
			ArrayList<ItemInfo> list;
			if (firstRoll <= commonRoll){
				list = ipx.getListOf(Quality.COMMON);
				System.out.println("Common");                      //debug
			}
			else if (firstRoll <= uncommonRoll){
				list = ipx.getListOf(Quality.UNCOMMON);
				System.out.println("Uncommon");                      //debug
			}
			else{
				list = ipx.getListOf(Quality.RARE);
				System.out.println("Rare");                      //debug
			}
			int max = 0;
			int listMin = 0;
			//assumes sorted by lvlReq
			boolean foundMax = false;
			boolean foundMin = false;
			for (int i = 0; i < list.size(); i++){
				ItemInfo info = list.get(i);
				System.out.print(info.getLvlReq() + ",");					//debug
				if (info.getLvlReq() <= monsterLevel){
					max = i;
				}
				else if(info.getLvlReq() > monsterLevel){
					foundMax = true;
				}
				if (info.getLvlReq() < min){
					listMin = i;
				}
				else if (info.getLvlReq() >= min){
					if (!foundMin){
						listMin = i;
					}
					foundMin = true;
				}
			}
			System.out.println();											//debug
			System.out.println("(Max: " + max + " Min: " + listMin + ")");  //debug
			if (!foundMax && !foundMin){
				return null;
			}
			int secondRoll = 0;
			int difference = max - listMin;
			if (difference > 0){
				secondRoll = r.nextInt(difference) + listMin;  //returns an item between the min and maximum
			}
			else{
				secondRoll = max;
			}
			if (secondRoll >= list.size()){
				secondRoll = list.size() - 1;
			}
			System.out.println("ROLL: " + secondRoll);									//debug
			System.out.println("---------------------------------------------");		//debug
			ItemInfo info = list.get(secondRoll);
			if (info.getLvlReq() < monsterLevel - 2 || info.getLvlReq() > monsterLevel){
				return null;
			}
			ItemType type = info.getType();
			if (type == ItemType.WEAPON){
				return new Weapon(info, v, tileX, tileY);
			}
			else if (type == ItemType.ARMOR){
				return new Armor(info,v, tileX, tileY);
			}
			else if (type == ItemType.HEAD){
				return new Helm(info,v, tileX, tileY);
			}
			else if (type == ItemType.BOOTS){
				return new Boots(info,v,tileX, tileY);
			}
			else if (type == ItemType.OFFHAND){
				return new OffHand(info,v, tileX, tileY);
			}
			return null;
		}
		/**Returns a specific item by quality and name.
		 * @param q
		 * @param name
		 * @return Item
		 */
		public Item getSpecificItem(Quality q, String name){
			ArrayList<ItemInfo> list = null;
			if (q == Quality.COMMON){
				list = ipx.getListOf(Quality.COMMON);
			}
			else if (q == Quality.UNCOMMON){
				list = ipx.getListOf(Quality.UNCOMMON);
			}
			else if (q == Quality.RARE){
				list = ipx.getListOf(Quality.RARE);
			}
			else if (q == Quality.LEGENDARY){
				list = ipx.getListOf(Quality.LEGENDARY);
			}
			else if (q == Quality.BASIC){
				list = ipx.getListOf(Quality.BASIC);
			}
			for (int i = 0; i < list.size(); i++){
				ItemInfo info = list.get(i);
				if (info.getName().equals(name)){
					return info.generateItem();
				}
			}
			return null;
		}
		private void loadMaps() throws FileNotFoundException, SlickException{
			long time = System.currentTimeMillis();
			File f = new File(mapPath);
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++){
				if (files[i].isDirectory()){
					continue;
				}
				else{
					String pathName = files[i].getName();
					String ext = pathName.split("\\.")[1];
					if (ext.equals("tmx")){
						String temp = mapPath.concat(pathName);
						AtlasMap map = new AtlasMap(temp, this);
						maps.put(pathName, map);
					}
				}
			}
			time = System.currentTimeMillis() - time;
			System.out.println("Loaded maps took " + time + " ms.");
		}
		/**Returns the requested AtlasMap.
		 * @param e
		 * @return AtlasMap
		 */
		public AtlasMap getMap(String e){
			return maps.get(e);
		}
		/**Updates all the maps.
		 * @param delta
		 */
		public void update(int delta){
			Iterator<String> it = mapKeys.iterator();
			while (it.hasNext()){
				String temp = it.next();
				maps.get(temp).update(delta);
			}
		}
		private void loadSprites() throws SlickException{
			File f = new File(path);
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++){
				String temp = files[i].getName();
				if (temp.equals("packages")){
					continue;
				}
				Image img = new Image(path.concat(temp));
				Sprite ss = new Sprite(img);
				sprites.put(temp, ss);
			}
		}
		/**Returns the selected spritesheet.
		 * @param ref
		 * @return SpriteSheet
		 */
		public Sprite getSprite(String ref){
			return sprites.get(ref);	
		}
		/**Returns the gui;
		 * @return GUI
		 */
		public Gui getGui(){
			return gui;
		}
		/**Sets the gui.
		 * @param g
		 */
		public void setGui(Gui g){
			this.gui = g;
		}
		/**Returns the Log instance contained in this class.
		 * @return Log
		 */
		public Log getLog(){
			return log;
		}
		private void spriteDimensions(){
			System.out.println("\n\n\n");
			System.out.println("Usable spritesheet dimensions");
			System.out.println("------------------------------------------");
			for (int i = 16; i < 400; i++){
				if (i % 48 == 0){
					for (int j = 16; j < 400; j++){
						if (j % 64 == 0){
							System.out.println(i + " x " + j);
						}
					}
				}
			}
			System.out.println("\n\n\n");
		}
		/**Returns the MobStat based on the reference string.
		 * @param ref
		 * @return MobStat
		 */
		public MobStat getMobStat(String ref){
			return mpx.getMobStat(ref);
		}
		
}
