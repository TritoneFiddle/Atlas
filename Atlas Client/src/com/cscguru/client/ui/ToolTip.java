package com.cscguru.client.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.enums.Quality;
import com.cscguru.client.items.Item;

/**Class that handles the tooltips of items or other entities.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class ToolTip{
	private Image tip;
	private Image img;
	private String description;
	private Item item;
	private Color color = Color.white;
	
	/**Constructs a tooltip.
	 * @throws SlickException
	 */
	public ToolTip() throws SlickException{
		tip = new Image("res/gui/tooltip.png");
	}
	/**Draws the tip at a vector.
	 * @param g
	 * @param v
	 */
	public void drawTip(Graphics g, Vector2f v) {
		float x = v.x;
		float y = v.y;
		tip.draw(x,y);
		img.draw(x + 107, y + 10);
		g.setColor(color);
		g.drawString(item.getName(), x + 13, y + 45);
		g.setColor(Color.white);
		g.drawString(description, x + 13, y + 60);
	}
	/**Sets the item that is to be tooltipped.
	 * @param item
	 */
	public void setItem(Item item){
		this.item = item;
		img = item.getImage();
		description = item.getDescription();
		Quality quality = item.getQuality();
		if (quality == Quality.COMMON){
			color = Color.white;
		}
		else if (quality == Quality.UNCOMMON){
			color = Color.green;
		}
		else if (quality == Quality.RARE){
			color = Color.magenta;
		}
		else if (quality == Quality.LEGENDARY){
			color = new Color(146,78,0);
		}
	}
}
