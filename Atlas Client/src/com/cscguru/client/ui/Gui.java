package com.cscguru.client.ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.cscguru.client.entities.Player;
import com.cscguru.client.entities.PlayerInfo;
import com.cscguru.client.interfaces.IClickable;
import com.cscguru.client.interfaces.IDrawable;
import com.cscguru.client.items.Item;
import com.cscguru.client.managers.ResourceManager;
import com.cscguru.com.balance.Balance;

/**Handles the gui elements of the game.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class Gui implements IDrawable,IClickable{
	private String time;
	private Image border;
	private Image screenBorder;
	private Image hpstoneBar;
	private Inventory inv;
	private ArrayList<Button> buttons;
	@SuppressWarnings("unused")
	private ResourceManager rm;
	private Cycle c;
	private Player p;
	private PlayerInfo info;
	private String zone;
	
	//screens
	private StatScreen stats;
	private Log log;
	
	/**Constructs a GUI object to handle most of the drawing of the user interface and keeps a reference of the camera object.
	 * @param rm 
	 * @throws SlickException
	 * @throws FileNotFoundException
	 */
	public Gui(ResourceManager rm, Cycle c, Player p, Log log) throws SlickException, FileNotFoundException{
		this.rm = rm;
		this.c = c;
		this.p = p;
		info = p.getInfo();
		stats = new StatScreen(info);
		this.log = log;
		log.setVisible(true);
		border = new Image("res/gui/border.png");
		screenBorder = new Image("res/gui/statScreen.png");
		hpstoneBar = new Image("res/gui/bghealthbar.png");
		inv = new Inventory(new Vector2f(1160,300), rm, p);
		buttons = new ArrayList<Button>();
		buttons.add(new Button(15,30, "Statistics",0));
		buttons.add(new Button(196,30, "Skills",1));
		buttons.add(new Button(15,84, "Quests",2));
		buttons.add(new Button(196,84, "Guild",3));
		buttons.add(new Button(15,138, "Trade",4));
		buttons.add(new Button(196,138, "Log",5));
	}
	@Override
	public void draw(Graphics g) {
		//seals the edges so nothing is shown beyond the border
		g.setColor(Color.black);
		g.fillRect(Settings.CAM_X - 64, Settings.CAM_Y, 64, 640);
		g.fillRect(Settings.CAM_X + Settings.CAM_W, Settings.CAM_Y, 64, 640);
		g.fillRect(Settings.CAM_X, Settings.CAM_Y + 672, 640, 200);
		g.fillRect(Settings.CAM_X, Settings.CAM_Y - 50, 680, 50);
		//center piece
		g.setColor(new Color(117,101,3));
		float width = (float) (info.getXp() / info.getMaxXP());
		g.fillRect(Settings.CAM_X + 63, Settings.CAM_Y - 40, 545 * width, 30);		//xp
		hpstoneBar.draw(Settings.CAM_X + 151,Settings.CAM_Y - 199);
		g.setColor(new Color(70,0,0, .6f));					//health
		width = (float) (info.getHp() / info.getMaxHP());
		g.fillRect(Settings.CAM_X + 161, Settings.CAM_Y - 199, 347 * width, 73);
		g.setColor(new Color(0,28,105));				//mana
		width = (float) (info.getMp() / info.getMaxMP());
		g.fillRect(Settings.CAM_X + 166, Settings.CAM_Y - 117, 335 * width, 30);
		g.setColor(new Color(0,64,0));					//stam
		width = (float) (info.getStam() / Balance.START_STAM);
		g.fillRect(Settings.CAM_X + 166, Settings.CAM_Y - 80, 335*width, 30);
		border.draw(Settings.CAM_X - 62,Settings.CAM_Y - 207);
		
		//time
		time = c.getTime();
		g.setColor(Color.black);
		g.drawString(time, 1395, 860);
		
		//zone name
		g.setColor(new Color(138,0,0));
		g.drawString(zone, 1140, 860);
		
		//gold count
		g.setColor(Color.yellow);
		g.drawString(Integer.toString(p.getInfo().getGold()), 1195, inv.getY() + 278);
		//inventory
		inv.draw(g);
		
		for (int i = 0; i < buttons.size(); i++){
			buttons.get(i).draw(g);
		}
		
		//screens
		screenBorder.draw(20,210);
		stats.draw(g);
		log.draw(g);
	}
	@Override
	public boolean checkMouseHover(Vector2f v) {
		if (v.x > Settings.CAM_X + Settings.CAM_W){
			inv.checkMouseHover(v);
		}
		else if (v.x < Settings.CAM_X){
			for (int i = 0; i < buttons.size(); i++){
				Button button = buttons.get(i);
				button.checkMouseHover(v);
			}
		}
		return false;
	}
	@Override
	public boolean checkMouseClick(Vector2f v, int button) {
		if (v.x > Settings.CAM_X + Settings.CAM_W){
		inv.checkMouseClick(v,button);
		}
		else if (v.x < Settings.CAM_X){
			for (int i = 0; i < buttons.size(); i++){
				boolean temp = buttons.get(i).checkMouseClick(v, button);
				if (temp && buttons.get(i).getButtonID() == 5){
					log.setVisible(true);
					stats.setVisible(false);
				}
				else if (temp && buttons.get(i).getButtonID() == 0){
					stats.setVisible(true);
					log.setVisible(false);
				}
			}
		}
		return false;
	}
	/**Interface to the Inventory.isBagFull() method.  Returns the index of an available slot, returns -1 if the bag is full.
	 * @return int
	 */
	public int isBagFull(){
		//returns -1 if bag is full, returns the index of the available slot if bag is not full
		return inv.isBagFull();
	}
	/**Debug method for generating random objects.
	 * 
	 */
	public void addItem(){
		inv.addItem();
	}
	/**Adds the item to the inventory at a specific bag position.
	 * @param item
	 * @param position
	 */
	public void addItem(Item item, int position){
		inv.addItem(item,position);
	}
	/**Updates the player's stats from items in the inventory.
	 * 
	 */
	public void updateStats(){
		int totalArmor = 0;
		int minDmg = 0;
		int maxDmg = 0;
		int totalHealth = 0;
		ArrayList<InventoryBox> equipped = inv.getEquipment();
		InventoryBox weapon = equipped.get(0); //weapon
		InventoryBox ring = equipped.get(1);  //ring
		InventoryBox neck = equipped.get(2);  //necklace
		if (weapon.hasItem()){
			minDmg += weapon.getItem().getMinDamage();
			maxDmg += weapon.getItem().getMaxDamage();
		}
		if (ring.hasItem()){
			minDmg += ring.getItem().getMod();
		}
		if (neck.hasItem()){
			totalHealth += neck.getItem().getMod();
		}
		for (int i = 3; i < equipped.size(); i++){
			if (equipped.get(i).hasItem()){
				totalArmor += equipped.get(i).getItem().getMod();
			}
		}
		stats.equippedStats(minDmg, maxDmg, totalArmor, totalHealth);
	}
	public void setZone(String s){
		zone = s;
	}
}
