package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CaveGenerator {

	int width; 
	int height;
	
	Random rand = new Random();
	
	public HashMap<Integer,Cell> cells = new HashMap<Integer,Cell>();
	public HashMap<Integer,Cell> buffer = new HashMap<Integer,Cell>();
	
	public CaveGenerator(int width, int height){
		
		this.width = width;
		this.height = height;
		
	}
	
	public void generate(){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				boolean value;
				if(rand.nextInt(10) > 4){
					value = true;
				} else{
					value = false;
				}
				cells.put(new Vector(x,y).hashCode(),new Cell(x, y, value));
			}
		}
	}
	
	public void step(int loops){
		
		for(int i = 0; i < loops; i++){
		
			buffer.clear();
			
			for(Cell c : cells.values()){
				List<Cell> l = c.getNeighbors();
				
				int alive = 0;
				
				for(Cell q: l){
					if(q != null){
						if(q.status){
							alive++;
						}
					}
				}
				
				boolean outState = false;
				
				if(c.status && alive > 4){
					outState = true;
				}
				if(c.status && alive < 5){
					outState = false;
				}
				if(!c.status && alive > 4){
					outState = true;
				}
				if(!c.status && alive < 5){
					outState = false;
				}
				
				Cell cout = new Cell((int)c.pos.x, (int)c.pos.y, outState);
				buffer.put(cout.pos.hashCode(), cout);
				
			}
			
			cells.clear();
			for(int key : buffer.keySet()){
				Cell value = buffer.get(key);
				cells.put(key, value);
			}
		
		}
		
	}
	
	public ArrayList<Vector> getLargest(){
		
		int maxId = id();

		HashMap<Integer,ArrayList<Cell>> bigMap = new HashMap<Integer,ArrayList<Cell>>();
		
		for(int i = 1; i < maxId + 1; i++){
			
			bigMap.put(i, new ArrayList<Cell>());
			
		}
		
		for(Cell c : cells.values()){
			
			if(c.status){
				bigMap.get(c.caveId).add(c);
			}
			
		}
		
		int largest = 0;
		int id = 0;
		for(ArrayList<Cell> a : bigMap.values()){
			
			if(a.size() > largest){
				largest = a.size();
				id = a.get(0).caveId;
			}
		}
		
		ArrayList<Vector> cave = new ArrayList<Vector>();
		
		int left = Integer.MAX_VALUE;
		int up = Integer.MAX_VALUE;
		
		for(Cell c : bigMap.get(id)){
			
			if(c.pos.x < left){
				left = (int) c.pos.x;
			}
			if(c.pos.y < up){
				up = (int) c.pos.y;
			}
			
		}
		
		Vector offset = new Vector(left, up);
		
		for(Cell c : bigMap.get(id)){
			
			Vector pos = Vector.add(c.pos, Vector.mult(offset, -1));
			cave.add(pos);
			
		}
		
		return cave;
	}
	
	private int id(){
		
		int currentId = 0;
		
		for(Cell c : cells.values()){
			
			if(c.status && c.caveId == 0){
				
				currentId++;
					
				c.caveId = currentId;	
				reId(c, currentId);
				
			}
			
			
		}
		
		return currentId;
		
	}
	
	private void reId(Cell cell, int id){
		
		for(Cell c : cell.getNeighbors()){
			
			if(c != null){
				if(c.status && c.caveId == 0){
					c.caveId = id;
					reId(c, id);
				}
			}
			
		}
		
	}
	
	public class Cell {

		boolean status;
		int caveId = 0;
		Vector pos;
		
		public Cell(int x, int y, boolean state){
			pos = new Vector(x, y);
			this.status = state;
		}
		
		public List<Cell> getNeighbors(){
			
			List<Cell> neighbors = new ArrayList<Cell>();
			
			for(int xV = -1; xV < 2; xV++){
				for(int yV = -1; yV < 2; yV++){
					Vector dir = new Vector(pos.x + xV, pos.y + yV);
					Cell c = CaveGenerator.this.cells.get(dir.hashCode());
					neighbors.add(c);
				}
			}
			
			return neighbors;
			
		}
		
	}
	
}
