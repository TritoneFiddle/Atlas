package com.cscguru.client.environment;

import com.cscguru.client.interfaces.IUpdatable;

/**Class used to control the day/night cycle of the game.
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public class Cycle implements IUpdatable{
	private int minutes;
	private int hours;
	private int totalUp;
	private float alpha = 0f;
	private float max = .85f;
	private float min = .0f;
	private int twilight = 120;
	private float rate = (max - min) / twilight ;
	/**Constructs a cycle object.
	 * 
	 */
	public Cycle(){
		hours = 5;
		minutes = 58;
	}
	/**Constructs a cycle object at a specific time.
	 * @param hours
	 * @param minutes
	 */
	public Cycle(int hours, int minutes){
		this.hours = hours;
		this.minutes = minutes;
	}
	@Override
	public void update(int delta) {
		totalUp += delta;
		setAlpha(delta);
		if (totalUp >= 1000){
			minutes += 1;
			totalUp = 0;
			if (minutes >= 60 ){
				minutes = 0;
				hours += 1;
				if (hours == 24){
					hours = 0;
				}
			}
		}
	}
	/**Calculates the alpha during the two-hour swing periods (dawn and dusk).
	 * @param delta
	 */
	private void setAlpha(int delta){
		int swing = 0;
		if (hours >= 20 || hours < 6){
			alpha = max;
		}
		else if (hours >= 6 && hours <= 8){
			swing = -1;
		}
		else if (hours >= 18 && hours < 20){
			swing = 1;	
		}
		else{
			alpha = min;
		}
		alpha += swing * rate * delta / 1000.0;
	}
	/**Returns the alpha transparency of the day/night cycle.
	 * @return float
	 */
	public float getAlpha(){
		return alpha;
	}
	/**Returns the time in 2400 format.
	 * @return String
	 */
	public String getTime(){
		String h = Integer.toString(hours);
		String m = Integer.toString(minutes);
		if (hours < 10){
			h = "0" + h;
		}
		if (minutes < 10){
			m = "0" + m;
		}
		return h + ":" + m;
	}

}
