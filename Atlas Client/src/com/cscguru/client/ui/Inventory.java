package com.cscguru.client.ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.entities.Player;
import com.cscguru.client.entities.PlayerInfo;
import com.cscguru.client.enums.ItemType;
import com.cscguru.client.enums.Quality;
import com.cscguru.client.interfaces.IDrawable;
import com.cscguru.client.items.Item;
import com.cscguru.client.managers.ResourceManager;
import com.cscguru.client.map.AtlasMap;

/**Handles the inventory logic.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class Inventory extends BoundBox implements IDrawable{
	private static final long serialVersionUID = 1L;
	private boolean swapping = false;
	private InventoryBox swapBox = null;
	private ToolTip tip;
	private boolean displayToolTip = false;
	private Vector2f tipPosition;
	private int bagTip;
	private int bagStart;
	//actual inventory
	private ArrayList<InventoryBox> bag;
	private ArrayList<InventoryBox> equipped;
	private InventoryBox head;
	private InventoryBox weapon;
	private InventoryBox boots;
	private InventoryBox offhand;
	private InventoryBox armor;
	private InventoryBox gloves;
	private InventoryBox back;
	private InventoryBox necklace;
	private InventoryBox ring;
	private InventoryBox belt;
	private Image helm;
	private Image coins;
	private Player p;
	//debug
	ResourceManager rm;
	/**Constructs an inventory at a specific vector.
	 * @param v
	 * @param rm
	 * @param p
	 * @throws SlickException
	 * @throws FileNotFoundException
	 */
	public Inventory(Vector2f v, ResourceManager rm, Player p) throws SlickException, FileNotFoundException{
		super(v,400,400);
		this.rm = rm;
		this.p = p;
		bag = new ArrayList<InventoryBox>();
		equipped = new ArrayList<InventoryBox>();
		tip = new ToolTip();
		helm = new Image("res/gui/helmicon.png");
		coins = new Image("res/items/images/coins.png");
		
		//constructs the unique item boxes for equipped items.  First several indexes are reserved for the equip boxes.
		head = new InventoryBox(x + 175, y - 250, this);
		head.setUnique(ItemType.HEAD, "Helm");
		armor = new InventoryBox(x + 175, y - 160, this);
		armor.setUnique(ItemType.ARMOR, "Armor");
		gloves = new InventoryBox(x + 115, y - 160, this);
		gloves.setUnique(ItemType.GLOVES, "Gloves");
		weapon = new InventoryBox(x + 55, y - 160, this);
		weapon.setUnique(ItemType.WEAPON, "Weapon");
		offhand = new InventoryBox(x + 250, y - 160, this);
		offhand.setUnique(ItemType.OFFHAND, "Offhand");
		boots = new InventoryBox(x + 175, y - 70, this);
		boots.setUnique(ItemType.BOOTS, "Boots");
		necklace = new InventoryBox(x + 350, y - 250, this);
		necklace.setUnique(ItemType.BACK, "Back");
		back = new InventoryBox(x + 350, y - 190, this);
		back.setUnique(ItemType.NECKLACE, "Neck");
		belt = new InventoryBox(x + 350, y - 130, this);
		belt.setUnique(ItemType.BELT, "Belt");
		ring = new InventoryBox(x + 350, y - 70, this);
		ring.setUnique(ItemType.RING, "Ring");
		
		
		bag.add(head);
		bag.add(armor);
		bag.add(gloves);
		bag.add(weapon);
		bag.add(offhand);
		bag.add(boots);
		bag.add(back);
		bag.add(belt);
		bag.add(necklace);
		bag.add(ring);
		equipped.add(weapon);
		equipped.add(ring);
		equipped.add(necklace);
		equipped.add(head);
		equipped.add(armor);
		equipped.add(gloves);
		equipped.add(offhand);
		equipped.add(boots);
		equipped.add(back);
		equipped.add(belt);
		bagStart = bag.size();
		
		//i = rows, j = columns, constructs the "backpack" part of the inventory.
		for (int i = 0; i < 4; i++){
			for(int j = 0; j < 6; j++){
				float x = v.x;
				float y = v.y;
				bag.add(new InventoryBox(x + (j * 70), y + (i * 70), this));
			}
		}
		//starting items
		addItem(rm.getSpecificItem(Quality.COMMON, "Dull Hatchet"),isBagFull());
	}
	
	@Override
	public void draw(Graphics g) {
		helm.draw(x + 142,y - 325);
		coins.draw(x, y + 270);
		g.setColor(Color.darkGray);
		g.drawLine(x - 20, y - 5, x + this.getWidth() + 20, y - 5);
		for (int i = 0; i < bag.size(); i++){
			bag.get(i).draw(g);
		}
		
		if (displayToolTip){
			tip.drawTip(g, tipPosition);
		}
	}

	@Override
	public boolean checkMouseHover(Vector2f v) {
		if (displayToolTip){
			InventoryBox temp = bag.get(bagTip);
			if (temp.checkMouseHover(v)){
				tipPosition.set(v.x - 250, v.y);
				return false;
			}
			else{
				displayToolTip = false;
				bagTip = -1;
				tipPosition = null;
			}
		}
		for (int i = 0; i < bag.size(); i++){
			InventoryBox temp = bag.get(i);
			boolean bool = temp.checkMouseHover(v);
			if (bool){
				displayToolTip = true;
				tipPosition = v;
				tipPosition.set(v.x - 250, v.y);
				bagTip = i;
				tip.setItem(temp.getItem());
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean checkMouseClick(Vector2f v, int button) {
		for (int i = 0; i < bag.size(); i++){
			InventoryBox box = bag.get(i);
			boolean temp = box.checkMouseClick(v, button);
			if (temp && button == 0){
				if (!swapping && box.hasItem()){
					swapping = true;
					swapBox = box;
					return false;
				}
				else if (swapping && !swapBox.equals(bag.get(i))){
					swapping = false;
					swapBox.swapItemWith(box);
					swapBox = null;
					return false;	
				}
			}
			if (temp && button == 1){
				if (box.getItem().hasDropped()){
					box.getItem().setDropped(false);
				}
			}
		}		
		swapping = false;
		swapBox = null;
		return false;
	}
	/**Checks to see if the bag is full, and returns the first available index if it is not full.  Returns -1 if it is full.
	 * @return int
	 */
	public int isBagFull(){
		//returns -1 if bag is full, returns the index of empty slot if bag is not full
		for (int i = bagStart; i < bag.size(); i++){
			if (!bag.get(i).hasItem()){
				return i;
			}
		}
		return -1;
	}
	/**Adds an item to the inventory at a specific box.
	 * @param item
	 * @param index
	 */
	public void addItem(Item item, int index){
		InventoryBox box = bag.get(index);
		box.addItem(item);
	}
	/**Debug method for generating random items.
	 * 
	 */
	public void addItem(){
		Item item = rm.getItem(4, new Vector2f(0,0),0,0);
		if (item != null){
			int i = isBagFull();
			if (i >= 0){
				addItem(item, i);
			}
		}
	}
	/**Returns the arraylist containing the equipment boxes.
	 * @return ArrayList<InventoryBox>
	 */
	public ArrayList<InventoryBox> getEquipment(){
		return equipped;
	}
	/**Transfers the item from the inventory box to the map.
	 * @param item
	 */
	public void itemDropped(Item item){
		AtlasMap map = rm.getMap("World_1.tmx");
		item.setX(p.getX());
		item.setY(p.getY());
		item.setTileX(p.getTileX());
		item.setTileY(p.getTileY());
		map.addItem(item);
	}
	/**Returns the player's info.
	 * @return PlayerInfo
	 */
	public PlayerInfo getPlayerInfo(){
		return p.getInfo();
	}
}
