package com.cscguru.client.items;

import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.ui.Inventory;

/**Item class for helm items.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class Helm extends Item {
	private static final long serialVersionUID = 1L;

	/**Constructs a helm item.
	 * @param info
	 * @param v
	 */
	public Helm(ItemInfo info, Vector2f v, int tileX, int tileY) {
		super(info, v, tileX, tileY);
	}

	@Override
	public String getDescription() {
		return "Armor: " + armor + "\nLevel: " + lvlReq + "\nValue: " + value + "\n" + desc;
	}

	@Override
	public void use(Inventory parent) {
		hasDropped = true;
		parent.itemDropped(this);
	}

}
