package com.cscguru.client.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.cscguru.client.enums.ItemType;
import com.cscguru.client.enums.Quality;
import com.cscguru.client.items.ItemInfo;

/**Parses IPX file formats.
 * @author Bryan Bennett
 * @date Dec 5, 2013
 */
public class IPXParser {
	private ArrayList<ItemInfo> common;
	private ArrayList<ItemInfo> uncommon;
	private ArrayList<ItemInfo> rare;
	private ArrayList<ItemInfo> legendary;
	private ArrayList<ItemInfo> basic;
	/**Constructs the IPXParser.  The first string parameter is the reference path to the ipx files, and the second string is the reference path to the image directory.
	 * @param ipxRef
	 * @param imgRef 
	 * @throws FileNotFoundException 
	 * @throws SlickException 
	 */
	public IPXParser(String ipxRef, String imgRef) throws FileNotFoundException, SlickException{
		long delta = System.currentTimeMillis();
		common = new ArrayList<ItemInfo>();
		uncommon = new ArrayList<ItemInfo>();
		rare = new ArrayList<ItemInfo>();
		legendary = new ArrayList<ItemInfo>();
		basic = new ArrayList<ItemInfo>();
		Scanner s = new Scanner(new File(ipxRef));
		String in = s.nextLine();
		int numOfItems = Integer.parseInt(in);
		for (int i = 0; i < numOfItems; i++){
			ItemInfo info = new ItemInfo();
			in = s.nextLine();  //gets quality
			if (in.equals("Common")){
				info.setQuality(Quality.COMMON);
				common.add(info);
			}
			else if (in.equals("Uncommon")){
				info.setQuality(Quality.UNCOMMON);
				uncommon.add(info);
			}
			else if (in.equals("Rare")){
				info.setQuality(Quality.RARE);
				rare.add(info);
			}
			else if (in.equals("Legendary")){
				info.setQuality(Quality.LEGENDARY);
				legendary.add(info);
			}
			else{
				info.setQuality(Quality.BASIC);
				basic.add(info);
			}
			in = s.nextLine();  //gets item type
			if (in.equals("Weapon")){
				info.setItemType(ItemType.WEAPON);
			}
			else if (in.equals("Armor")){
				info.setItemType(ItemType.ARMOR);
			}
			else if (in.equals("Boots")){
				info.setItemType(ItemType.BOOTS);
			}
			else if (in.equals("Offhand")){
				info.setItemType(ItemType.OFFHAND);
			}
			else if (in.equals("Helm")){
				info.setItemType(ItemType.HEAD);
			}
			else if (in.equals("Potion/HP")){
				info.setItemType(ItemType.POTIONHP);
			}
			else if (in.equals("Potion/MP")){
				info.setItemType(ItemType.POTIONMP);
			}
			else if (in.equals("Ring")){
				info.setItemType(ItemType.RING);
			}
			else if (in.equals("Necklace")){
				info.setItemType(ItemType.NECKLACE);
			}
			in = s.nextLine(); //name
			info.setName(in);
			in = s.nextLine(); //mod
			info.setMod(Integer.parseInt(in));
			in = s.nextLine();
			info.setMax(Integer.parseInt(in));
			in = s.nextLine(); //value
			info.setValue(Integer.parseInt(in));
			in = s.nextLine(); //level
			info.setLvlReq(Integer.parseInt(in));
			in = s.nextLine(); //description
			info.setDesc(in);
			in = s.nextLine();
			info.setImage(new Image(imgRef.concat(in)));
			s.nextLine(); //throw away this line to ignore the image path used in Shielder
		}
		s.close();
		long delta2 = System.currentTimeMillis() - delta;
		System.out.println("Common items: " + common.size());
		System.out.println("Uncommon items: " + uncommon.size());
		System.out.println("Rare items: " + rare.size());
		System.out.println("Legendary items: " + legendary.size());
		System.out.println("Basic items: " + basic.size() + "\n---------------------------------------------------------");
		System.out.println("Item informations loaded in: " + delta2 + " ms.");
		delta2 = System.currentTimeMillis();
		selectionSort(common);
		delta2 = System.currentTimeMillis() - delta2;
		System.out.println("Common items sorted in: " + delta2 + " ms.");
		delta2 = System.currentTimeMillis();
		selectionSort(uncommon);
		delta2 = System.currentTimeMillis() - delta2;
		System.out.println("Uncommon items sorted in: " + delta2 + " ms.");
		delta2 = System.currentTimeMillis();
		selectionSort(rare);
		delta2 = System.currentTimeMillis() - delta2;
		System.out.println("Rare items sorted in: " + delta2 + " ms.");
		delta2 = System.currentTimeMillis();
		selectionSort(legendary);
		delta2 = System.currentTimeMillis() - delta2;
		System.out.println("Legendary items sorted in: " + delta2 + " ms.");
		delta2 = System.currentTimeMillis();
		selectionSort(basic);
		delta2 = System.currentTimeMillis() - delta2;
		System.out.println("Basic items sorted in: " + delta2 + " ms.\n---------------------------------------------------------");
		delta = System.currentTimeMillis() - delta;
		System.out.println("Total time: " + delta + " ms.");
	}
	/**Returns the appropriate array list containing a set of items based on quality.
	 * @param q
	 * @return ArrayList<ItemInfo>
	 */
	public ArrayList<ItemInfo> getListOf(Quality q){
		if (q == Quality.BASIC){
			return basic;
		}
		else if (q == Quality.COMMON){
			return common;
		}
		else if (q == Quality.UNCOMMON){
			return uncommon;
		}
		else if (q == Quality.RARE){
			return rare;
		}
		else{
			return legendary;
		}
	}
	private void selectionSort(ArrayList<ItemInfo> list){
		ItemInfo selection;
		int lowest;
		for (int i = 0; i < list.size(); i++){
			selection = list.get(i);
			lowest = i;
			for (int j = i; j < list.size(); j++){
				ItemInfo target = list.get(j);
				if (target.getLvlReq() < list.get(lowest).getLvlReq()){
					lowest = j;
				}
			}
			ItemInfo low = list.get(lowest);
			list.set(i, low);
			list.set(lowest,  selection);
		}
	}
}
