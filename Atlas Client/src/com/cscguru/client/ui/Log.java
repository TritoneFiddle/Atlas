package com.cscguru.client.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.cscguru.client.interfaces.IDrawable;

/**Handles real-time printing of combat, statuses, and other events.
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class Log implements IDrawable {
	private boolean isVisible = false;
	private ArrayList<String> list;
	private ArrayList<Color> colors;
	private int alignX = 40;
	private int alignY = 230;
	
	/**
	 * Initializes an instance of the Log class.
	 */
	public Log(){
		list = new ArrayList<String>();
		colors = new ArrayList<Color>();
	}

	@Override
	public void draw(Graphics g) {
		if (isVisible){
			int k = 0;
			for (int i = 0; i < list.size(); i++){
				k = (i * 15) + alignY;
				g.setColor(colors.get(i));
				g.drawString(list.get(i), alignX, k);
			}
		}	
	}
	/**Sets whether or not the log is visible.
	 * @param val
	 */
	public void setVisible(boolean val){
		isVisible = val;
	}
	/**Writes to the log based on the string input.  Default color is white.
	 * @param s
	 */
	public void writeToLog(String s){
		if (list.size() < 38){
			list.add(s);
			colors.add(Color.white);
		}
		else{
			list.remove(0);
			colors.remove(0);
			list.add(s);
			colors.add(Color.white);
		}
	}
	/**Writes to the log based on the string and color input.
	 * @param s
	 * @param c
	 */
	public void writeToLog(String s, Color c){
		if (list.size() < 38){
			list.add(s);
			colors.add(c);
		}
		else{
			list.remove(0);
			colors.remove(0);
			list.add(s);
			colors.add(c);
		}
	}
}
