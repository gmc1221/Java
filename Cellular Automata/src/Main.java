import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

public class Main {

	
	int height = 500;
	int width = 500;
	
	private int cellSize = 5;
	private int lastKeyPress;
	
	public long lastTick;
	public long ticks = 0;
	
	private Random rand = new Random();
	
	public static Screen screen;
	public static HashMap<Integer,Cell> cells;
	public static HashMap<Integer,Cell> buffer;
	
	public Main(){
		
		screen = new Screen(width,height);
		
		cells = new HashMap<Integer,Cell>();
		buffer = new HashMap<Integer,Cell>();
		
		generate();
		
		boolean running = true;
		int stepCount = 0;
		
		while(running){
			
			if(System.currentTimeMillis() - lastTick > 20){
				ticks++;
				lastTick = System.currentTimeMillis();
				
				if(screen.keys.contains(KeyEvent.VK_ESCAPE)){
					System.exit(0);
				}
				
				if(screen.keys.contains(KeyEvent.VK_SPACE) && ticks - lastKeyPress > 5){
					lastKeyPress = (int) ticks;
					
					generate();
					stepCount = 0;
				}
				
				if(screen.keys.contains(KeyEvent.VK_C) && ticks - lastKeyPress > 5){
					lastKeyPress = (int) ticks;
					
					color();
				}
				
				if(screen.keys.contains(KeyEvent.VK_S) && ticks - lastKeyPress > 5){
					lastKeyPress = (int) ticks;
					
					save();
				}
				
				if(screen.keys.contains(KeyEvent.VK_ENTER) && ticks - lastKeyPress > 10){
					lastKeyPress = (int) ticks;
					
					step();
					//stepCount = 5;
				}
				
//				if(stepCount <= -1){
//					step();
//					stepCount++;
//				}
				
				draw();
				
			}
			
		}
		
		
	}
	
	private void generate(){
		for(int x = 0; x < width/cellSize; x++){
			for(int y = 0; y < height/cellSize; y++){
				boolean value;
				if(rand.nextInt(10) > 4){
					value = true;
				} else{
					value = false;
				}
				cells.put(new Vector(x,y).hashCode(),new Cell(x, y, value));
			}
		}
		
//		buffer.clear();
//		for(int key : cells.keySet()){
//			Cell value = cells.get(key);
//			buffer.put(key, value);
//		}
	}
	
	private void draw(){
		
		Graphics2D g = screen.image.createGraphics();
		
		for(Cell c : cells.values()){
			
			if(c.c != Color.WHITE){
				g.setColor(c.c);
			}else if(c.status){
				g.setColor(Color.WHITE);
			} else{
				g.setColor(Color.BLACK);
			}
			
			g.fillRect(c.pos.x*cellSize, c.pos.y*cellSize, cellSize, cellSize);
			
		}
		
		g.dispose();
		
		screen.pane.repaint();
		
	}
	
	private void step(){
		
		buffer.clear();
		
		for(Cell c : cells.values()){
			List<Cell> l = c.getNeighbors();
			
			int alive = 0;
			
			for(Cell i: l){
				if(i != null){
					if(i.status){
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
			
//			if(c.status){
//				int i = rand.nextInt(l.size());
//				Cell cout = l.get(i);
//				cout.status = true;
//				buffer.put(cout.pos.hashCode(), cout);
//			}
			
			Cell cout = new Cell(c.pos.x, c.pos.y, outState);
			buffer.put(cout.pos.hashCode(), cout);
			
		}
		
		cells.clear();
		for(int key : buffer.keySet()){
			Cell value = buffer.get(key);
			cells.put(key, value);
		}
		
	}
	
	private void color(){
		
		for(Cell c : cells.values()){
			
			if(c.status && c.c == Color.WHITE){
				
//				Set<Cell> cluster = new HashSet<Cell>();
//				cluster.add(c);
				Color fillColor = Color.getHSBColor(rand.nextFloat(), 1, 1);
					
				c.c = fillColor;	
				colorize(c, fillColor);
				
//				int xAv = 0;
//				int yAv = 0;
//				
//				for(Cell e : cluster){
//					
//					xAv += e.pos.x;
//					yAv += e.pos.y;
//					
//				}
//				
//				xAv = xAv / cluster.size();
//				yAv = yAv / cluster.size();
//				
//				Vector barycenter = new Vector(xAv, yAv);
//				
//				Cell center = cells.get(barycenter.hashCode());
//				center.c = Color.GRAY;
			}
			
			//draw();
			
		}
		
	}
	
	private void colorize(Cell cell, Color color){
		
		for(Cell c : cell.getNeighbors()){
			
			if(c != null){
				if(c.status && c.c == Color.WHITE){
					c.c = color;
					colorize(c, color);
				}
			}
			
		}
		
	}
	
	private void save(){
		Calendar calendar = Calendar.getInstance();
		File outputfile = new File("Snapshot " + calendar.getTime().toString() + ".png");
		try {
			ImageIO.write(screen.image, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Main();
		
	}

}
