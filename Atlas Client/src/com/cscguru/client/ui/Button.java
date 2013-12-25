package com.cscguru.client.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.cscguru.client.interfaces.IClickable;
import com.cscguru.client.interfaces.IDrawable;

/**Button class.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class Button extends Rectangle implements IDrawable,IClickable {
	private static final long serialVersionUID = 1L;
	private Image state;
	private Image normal;
	private Image pressed;
	private Image hovered;
	private float text_x;
	private float text_y;
	private int buttonID;
	private String text;
	/**Constructs a button at (x,y) with the appropriate image of text.
	 * @param x
	 * @param y
	 * @param text 
	 * @param buttonID 
	 * @throws SlickException
	 */
	public Button(float x, float y, String text, int buttonID) throws SlickException {
		super(x, y, 180, 53);
		this.buttonID = buttonID;
		this.text = text;
		int length = text.length();
		normal = new Image("res/gui/button.png");
		pressed = new Image("res/gui/buttonPressed.png");
		hovered = new Image("res/gui/buttonHighlight.png");
		state = normal;
		
		text_x = x + 91 - (length * 5);
		text_y = y + 17;
	}

	@Override
	public void draw(Graphics g) {
		state.draw(x,y);
		g.setColor(Color.white);
		g.drawString(text, text_x, text_y);
	}

	@Override
	public boolean checkMouseHover(Vector2f v) {
		if (this.contains(v.x,v.y)){
			state = hovered;
		}
		else{
			state = normal;
		}
		return false;
	}

	@Override
	public boolean checkMouseClick(Vector2f v, int button) {
	if (this.contains(v.x,v.y)){
		state = pressed;
		return true;
	}
	else{
		state = normal;
	}
		return false;
	}
	/**Returns the integer identification for this button.
	 * @return buttonID
	 */
	public int getButtonID(){
		return buttonID;
	}

}
