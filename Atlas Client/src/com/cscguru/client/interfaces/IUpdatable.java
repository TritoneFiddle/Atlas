package com.cscguru.client.interfaces;

/**Interface used for items that need updates (animations, day/night cycles, etc).
 * @author Bryan Bennett
 * @date Dec 1, 2013
 */
public interface IUpdatable {

	
/**Provides a method in which a delta can be provided to update certain effects.
 * @param delta
 */
public void update(int delta);
}
