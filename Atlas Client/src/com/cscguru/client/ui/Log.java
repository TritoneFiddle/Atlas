package com.cscguru.client.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.cscguru.client.interfaces.IDrawable;

public class Log implements IDrawable {
	private boolean isVisible = false;
	private ArrayList<String> list;
	private ArrayList<Color> colors;
	private int alignX = 40;
	private int alignY = 230;
	
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
	public void setVisible(boolean val){
		isVisible = val;
	}
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
