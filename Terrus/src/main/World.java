package main;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class World {

	Set<Tile> fullTiles = new HashSet<Tile>();
	HashMap<Integer,Tile> tiles = new HashMap<Integer,Tile>();
	Set<Tile> activeTilesBuffer = new HashSet<Tile>();
	Set<Tile> activeTiles = new HashSet<Tile>();

	
	public World(){
		
	}
	
	public void addTile(Tile t){
		
		Vector key = new Vector(t.pos.x, t.pos.y);
		
		tiles.put(key.hashCode(), t);
	}
	
}
