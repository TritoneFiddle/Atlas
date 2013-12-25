package com.cscguru.client.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import com.cscguru.client.enums.ItemType;
import com.cscguru.client.interfaces.IDrawable;
import com.cscguru.client.items.Item;

/**The box that holds items.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class InventoryBox extends BoundBox implements IDrawable{
	private static final long serialVersionUID = 1L;
	private Image state;
	private Image normal;
	private Image hover;
	private Image select;
	private Item item;
	private boolean clicked = false;
	private boolean hasItem = false;
	private Inventory parent;
	private ItemType type;
	private String typeS;
	private boolean isUnique = false;
	private boolean isHovering = false;

	/**Constructs a box at (x,y) and keeps a reference of the Inventory instance.
	 * @param x
	 * @param y
	 * @param parent
	 * @throws SlickException
	 */
	public InventoryBox(float x, float y, Inventory parent) throws SlickException {
		super(x, y, 52, 52);
		normal = new Image("res/gui/boxNormal.png");
		hover = new Image("res/gui/boxHover.png");
		select = new Image("res/gui/boxSelect.png");
		state = normal;
		this.parent = parent;
	}

	@Override
	public void draw(Graphics g) {
		state.draw(x,y);
		if (hasItem){
			item.draw(g);
		}
		else if (isHovering && isUnique){
			g.setColor(Color.yellow);
			g.drawString(typeS, x + this.getWidth() / 2, y + this.getHeight()/2);
		}
	}

	@Override
	public boolean checkMouseHover(Vector2f v) {
		if (this.contains(v.x,v.y) && !clicked){
			state = hover;
			isHovering = true;
			if (hasItem && item.hasToolTip()){
				return true;
			}
			else{
				return false;
			}
		}
		else if (!clicked){
			state = normal;
			isHovering = false;
		}
		return false;
	}

	@Override
	public boolean checkMouseClick(Vector2f v, int button) {
		if (this.contains(v.x,v.y)){
			state = select;
			clicked = true;
		}
		else{
			state = normal;
			clicked = false;
		}
		
		if (clicked && button == 0){
			return true;
		}
		else if (clicked && button == 1 && hasItem){
			item.use(parent);
			if (item.hasDropped()){
				setHasItem(false);
			}
			state = normal;
		}
		return false;
	}
	/**Returns whether or not if the box has an item.
	 * @return boolean
	 */
	public boolean hasItem(){
		return hasItem;
	}
	/**Sets whether or not the box has an item.
	 * @param hasItem
	 */
	public void setHasItem(boolean hasItem){
		this.hasItem = hasItem;
		if (hasItem == false){
			item = null;
		}
	}
	/**Adds an item to this box.
	 * @param item
	 */
	public void addItem(Item item){
		item.setX(this.x + 13);
		item.setY(this.y + 10);
		this.item = item;
		setHasItem(true);
	}
	/**Swaps the contents of this box with another inventory box.
	 * @param swapper
	 */
	public void swapItemWith(InventoryBox swapper){
		Item swap = swapper.getItem();
		int level = parent.getPlayerInfo().getLevel();
		boolean check = false;
		if (isUnique || swapper.isUnique()){
			check = true;
		}
		else if (isUnique && swapper.isUnique()){
			state = normal;
			swapper.setNormal();
			return;
		}
		if (!check){
			if (swap != null){
				swapper.addItem(item);
				addItem(swap);
			}
			else{
				swapper.addItem(item);
				item = null;
				setHasItem(false);
			}
		}
		else{
			if (swap != null){
				if (isUnique && swap.getType() == type && swap.getLvlReq() <= level){
					swapper.addItem(item);
					addItem(swap);
				}
				else if (swapper.isUnique() && item.getType() == swapper.getUnique() && item.getLvlReq() <= level){
					swapper.addItem(item);
					addItem(swap);
				}
				else{
					state = normal;
					swapper.setNormal();
					return;
				}
			}
			else{
				if (swapper.isUnique() && item.getType() == swapper.getUnique() && item.getLvlReq() <= level){
					swapper.addItem(item);
					item = null;
					setHasItem(false);
				}
				else if (!swapper.isUnique()){
					swapper.addItem(item);
					item = null;
					setHasItem(false);
				}
				else{
					state = normal;
					swapper.setNormal();
					return;
				}
			}
		}
		state = normal;
		swapper.setNormal();
	}
	/**Returns the item stored in this box.
	 * @return Item
	 */
	public Item getItem(){
		return item;
	}
	/**Sets the clicked state of this box to normal.
	 * 
	 */
	public void setNormal(){
		state = normal;
		clicked = false;
	}
	/**Used only for inventory boxes that are used for equipping items.  Sets the box to where it only accepts a specific item type.  The string is what is displayed when the box is empty.
	 * @param type
	 * @param typeS 
	 */
	public void setUnique(ItemType type, String typeS){
		this.type = type;
		this.typeS = typeS;
		isUnique = true;
	}
	/**Returns the string representation of the item type.
	 * @return String
	 */
	public String typeToString(){
		return typeS;
	}
	/**Returns the item type that this box will only allow.
	 * @return ItemType
	 */
	public ItemType getUnique(){
		return type;
	}
	/**Returns whether or not this box is a unique box (only accepts specific types of items).
	 * @return boolean
	 */
	public boolean isUnique(){
		return isUnique;
	}

}
