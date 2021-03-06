package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
	
	boolean gen2d = true;
	int depth = 10;
	float variation = 400;
	long lastTick = 0;
	public static boolean running = true;
	
	Camera c;
	Generator gen;
	static Screen screen;
	Set<Integer> loaded = new HashSet<Integer>();
	List<List<Point>> lists = new ArrayList<List<Point>>();
	
	public Main(Screen screen){
		
		
		if(gen2d == true){
		//DO NOT PUT DEPTH ABOVE 9
		gen = new Generator(0.5f, 8, new Point(0,0), new Point(0,0));
		
		Point3[][] list = gen.generate2d(new Point3(0,0,0.5), new Point3(1,0,0.5), new Point3(0,1,0.5), new Point3(1,1,0.5));
		
		double highest = Double.MIN_VALUE;
		double lowest = Double.MAX_VALUE;
		
		for(int x = 0; x < list.length; x++){
			for(int y = 0; y < list.length; y++){
				if(list[x][y].z > highest){
					highest = list[x][y].z;
				}
				if(list[x][y].z < lowest){
					lowest = list[x][y].z;
				}
			}
		}
		
		double range = highest - lowest;
		System.out.println(highest);
		System.out.println(lowest);
		System.out.println(range);
		
		Graphics2D g = screen.image.createGraphics();
		
		boolean covered[][] = new boolean[list.length][101];
		
		for(int x = 0; x < list.length; x++){
			for(int y = 0; y < list.length; y++){
				
					int val = (int) (((list[x][y].z - lowest)/range)*100);				
					System.out.println(val);
//					int val = (int) Math.round(list[x][y].z*100);
					
					if(!covered[x][val]){
						
						Color c = Color.getHSBColor(0, 0, 1 - ((float)y/(float)list.length));
						
						g.setColor(c);
						
						g.fillRect(x*(800/gen.l), val*(800/gen.l), 800/gen.l, 800/gen.l);
						
						covered[x][val] = true;
						
					}
					
//					if(val < 0){
//						val = 0;
//					} else if(val > 1){
//						val = 1;
//					}
//				
//					Color c = Color.getHSBColor(0, 0, val);
//				
//					g.setColor(c);
//					
//					g.fillRect(x*(800/gen.l), y*(800/gen.l), 800/gen.l, 800/gen.l);
//				
				}
			}
		
		g.dispose();
		screen.pane.repaint();
	}
		
//		float threshold;
//		while(running){
//			
//			if(System.currentTimeMillis() - lastTick >= 50){
//				lastTick = System.currentTimeMillis();
//				
//				threshold = ((float)screen.slider.getValue()/100);
//				
//				g = screen.image.createGraphics();
//				
//				for(int x = 0; x < list.length; x++){
//					for(int y = 0; y < list.length; y++){
//							
//							float val = (float) ((list[x][y].z - lowest)/range);
//							
//							if(val >= threshold){
//								val = 1;
//							}
//							if(val < threshold){
//								val = 0;
//							}
//						
//							Color c = Color.getHSBColor(0, 0, val);
//						
//							g.setColor(c);
//							
//							g.fillRect(x*(800/gen.l), y*(800/gen.l), 800/gen.l, 800/gen.l);
//						
//						}
//					}
//				
//				g.dispose();
//				
//				screen.pane.repaint();
//				
//			}
//		}
		else{
		gen = new Generator(variation, depth, new Point(0, 400), new Point(800, 400));
		
		List<Point> points = gen.generate1d();
		
		lists.add(Point.interpolate(points));
		
		loaded.add(0);
		
		Graphics2D g = screen.image.createGraphics();
		
		g.setColor(Color.white);
		
		for(Point p : lists.get(0)){
			g.drawLine((int)p.x, (int)p.y, (int)p.x, 800);
		}
		
		g.dispose();
		
		screen.pane.repaint();
		
		c = new Camera(screen);
		
		loop();
	}
	}
	
	private void loop(){
		
		while(running){
			
			if(System.currentTimeMillis() - lastTick >= 20){
				lastTick = System.currentTimeMillis();
				
				Graphics2D g = screen.image.createGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 800);
				
				for(List<Point> l : lists){
					c.draw(l, g);
				}
				
				g.dispose();
				screen.pane.repaint();
				
				if(Screen.keys.contains(KeyEvent.VK_D)){
					c.x += 10;
				}
				if(Screen.keys.contains(KeyEvent.VK_A)){
					c.x -= 10;
				}
				if(Screen.keys.contains(KeyEvent.VK_Q)){
					System.exit(0);
				}
			
				if(!loaded.contains(Math.floorDiv((int) c.x,800))){
					Generator q = new Generator(variation, depth, new Point(Math.floorDiv((int) c.x,800)*800, 400), new Point(Math.floorDiv((int) c.x,800)*800+800, 400));
					List<Point> points = q.generate1d();
					lists.add(Point.interpolate(points));
					loaded.add(Math.floorDiv((int) c.x,800));
				}
				if(!loaded.contains(Math.floorDiv((int) c.x,800) + 1)){
					Generator q = new Generator(variation, depth, new Point(Math.floorDiv((int) c.x,800)*800+800, 400), new Point(Math.floorDiv((int) c.x,800)*800+1600, 400));
					List<Point> points = q.generate1d();
					lists.add(Point.interpolate(points));
					loaded.add(Math.floorDiv((int) c.x,800) + 1);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		screen = new Screen(800, 800);
		new Main(screen);

	}

}
