package com.cscguru.client.map;

import org.newdawn.slick.geom.Rectangle;

public class Tile extends Rectangle {
	private static final long serialVersionUID = 1L;

	private TileType type;
	
	public Tile(int x, int y, TileType type){
		super(x,y,16,16);
		this.type = type;
	}
	/**Returns the tile type of this tile.
	 * @return TileType
	 */
	public TileType getTileType(){
		return type;
	}

}
