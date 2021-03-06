import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Cell {

	boolean status;
	
	Vector pos;
	Color c = Color.WHITE;
	
	public Cell(int x, int y, boolean state){
		pos = new Vector(x, y);
		this.status = state;
	}
	
	public List<Cell> getNeighbors(){
		
		List<Cell> neighbors = new ArrayList<Cell>();
		
		for(int xV = -1; xV < 2; xV++){
			for(int yV = -1; yV < 2; yV++){
				Vector dir = new Vector(pos.x + xV, pos.y + yV);
				Cell c = Main.cells.get(dir.hashCode());
				if(c != null){
					neighbors.add(c);
				}
			}
		}
		
		return neighbors;
		
	}
	
}
