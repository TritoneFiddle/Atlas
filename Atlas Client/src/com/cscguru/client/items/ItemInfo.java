package com.cscguru.client.items;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.enums.ItemType;
import com.cscguru.client.enums.Quality;

/**Holds all the information a particular item could possibly have.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class ItemInfo {
	private String name;
	private int lvlreq = 0;
	private int value = -1;
	private Image img;
	private String description = "";
	private ItemType type;
	private int mod = 0;
	private int max = 0;
	private Quality quality;
	/**Constructs an ItemInfo object to hold item information.
	 */
	public ItemInfo(){
	}
	/**Sets the item type of this item.
	 * @param type
	 */
	public void setItemType(ItemType type){
		this.type = type;
	}
	/**Sets the quality of the item.
	 * @param q
	 */
	public void setQuality(Quality q){
		this.quality = q;
	}
	/**Sets the name of the item.
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	/**Returns the name of the item.
	 * @return String
	 */
	public String getName(){
		return name;
	}
	/**Sets the level requirement of the item.
	 * @param lvlreq
	 */
	public void setLvlReq(int lvlreq){
		this.lvlreq = lvlreq;
	}
	/**Returns the level requirement of the item.
	 * @return int
	 */
	public int getLvlReq(){
		return lvlreq;
	}
	/**Sets the value (in gold) of the item.
	 * @param value
	 */
	public void setValue(int value){
		this.value = value;
	}
	/**Returns the value (in gold) of the item.
	 * @return int
	 */
	public int getValue(){
		return value;
	}
	/**Sets the image of the item.
	 * @param img
	 */
	public void setImage(Image img){
		this.img = img;
	}
	/**Returns the image of the item.
	 * @return Image
	 */
	public Image getImage(){
		return img;
	}
	/**Sets the description of the item for the tooltip.
	 * @param description
	 */
	public void setDesc(String description){
		this.description = description;
	}
	/**Returns the description of the item for the tooltip.
	 * @return String
	 */
	public String getDesc(){
		return description;
	}
	/**Returns the ItemType of the item.
	 * @return ItemType
	 */
	public ItemType getType(){
		return type;
	}
	/**Returns the damage this item can do, if applicable.
	 * @return int
	 */
	public int getMod() {
		return mod;
	}
	/**Sets the damage this item can do, if applicable.
	 * @param mod
	 */
	public void setMod(int mod) {
		this.mod = mod;
	}
	/**Returns the Quality of this object (rarity).
	 * @return Quality
	 */
	public Quality getQuality(){
		return quality;
	}
	/**Generates an item using the information of this object.
	 * @return Item
	 */
	public Item generateItem(){
		Vector2f v = new Vector2f(0,0);
		if (type == ItemType.ARMOR){
			return new Armor(this, v,0,0);
		}
		else if (type == ItemType.HEAD){
			return new Helm(this, v,0,0);
		}
		else if (type == ItemType.OFFHAND){
			return new OffHand(this,v,0,0);
		}
		else if (type == ItemType.BOOTS){
			return new Boots(this,v,0,0);
		}
		else if (type == ItemType.WEAPON){
			return new Weapon(this,v,0,0);
		}
		else{
			//TODO
		}
		return null;
	}
	/**Returns the maximum damage of this item (if it is a weapon).
	 * @return int
	 */
	public int getMax() {
		return max;
	}
	/**Sets the maximum damage of this item (if it is a weapon).
	 * @param max
	 */
	public void setMax(int max) {
		this.max = max;
	}
}
