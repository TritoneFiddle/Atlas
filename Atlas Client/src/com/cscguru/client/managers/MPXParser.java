package com.cscguru.client.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import com.cscguru.client.entities.MobStat;

/**Handles the parsing of .MPX files created from Mob Factory.
 * @author Bryan Bennett
 * @date Dec 26, 2013
 */
public class MPXParser {
	private HashMap<String, MobStat> map;
	private String path;
	
	/**Initializes and parses the .MPX file based on the reference string.
	 * @param ref
	 * @throws FileNotFoundException
	 */
	public MPXParser(String ref) throws FileNotFoundException{
		path = ref;
		map = new HashMap<String, MobStat>();
		loadMobs();
	}
	private void loadMobs() throws FileNotFoundException{
		Scanner s = new Scanner(new File(path));
		int num = Integer.parseInt(s.nextLine());
		for (int i = 0; i < num; i++){
			MobStat ms = new MobStat();
			String temp = s.nextLine();
			ms.setName(temp);
			temp = s.nextLine();
			ms.setLevel(Integer.parseInt(temp));
			temp = s.nextLine();
			ms.setHP(Integer.parseInt(temp));
			temp = s.nextLine();
			ms.setMinDmg(Integer.parseInt(temp));
			temp = s.nextLine();
			ms.setMaxDmg(Integer.parseInt(temp));
			temp = s.nextLine();
			ms.setBonusXP(Integer.parseInt(temp));
			temp = s.nextLine();
			ms.setBonusGold(Integer.parseInt(temp));
			temp = s.nextLine();
			ms.setMissChance(Float.parseFloat(temp));
			temp = s.nextLine();
			ms.setHostile(Boolean.parseBoolean(temp));
			temp = s.nextLine();
			ms.setSight(Integer.parseInt(temp));
			temp = s.nextLine();
			ms.setHasItem(Boolean.parseBoolean(temp));
			temp = s.nextLine();
			ms.setItemName(temp);
			temp = s.nextLine();
			ms.setItemChance(Float.parseFloat(temp));
			s.nextLine(); //throw away, full image path not necessary
			temp = s.nextLine();
			ms.setImageName(temp);
			map.put(ms.getName(), ms);
		}
		s.close();
	}
	/**Returns the MobStat that holds all necessary information for a mob based on the reference string.
	 * @param ref
	 * @return MobStat
	 */
	public MobStat getMobStat(String ref){
		return map.get(ref);
	}
}
