package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

//import main.World.Zone;

public class Main {
	
	long lastTick = 0;
	public static long ticks = 0;
	public static boolean running = true;
	
	Camera c;
	Generator gen;
	Tile test;
	static World world;
	static Screen screen;
	Set<Integer> loaded = new HashSet<Integer>();
	
	Player p;
	
	public Main(Screen screen){
		
		gen = new Generator(100, 10, new Vector(0, 200), new Vector(400, 200));
		
		List<Vector> h = gen.layerLine(300, 400, 0.2f);
		
		List<Vector> points = gen.generate();
		
		world = new World();
		
		points = Vector.interpolate(points);
		
		for(Vector v : points){
			Tile t = new Tile(v);
			t.color = Color.GREEN;
			world.addTile(t);
			v.print();
			for(int i = (int) v.y + 1; i < 400; i++){
				world.addTile(new Tile(new Vector(v.x, i)));
			}
		}
		
		CaveGenerator caves = new CaveGenerator(100,100);
		Random rand = new Random();
		
		for(int i = 0; i < 3; i++){
		
			caves.generate();
			caves.step(10);
			ArrayList<Vector> cave = caves.getLargest();
			
			Vector offset = new Vector(i*90, rand.nextInt(50) + 250);
			
			for(Vector v : cave){
				
				world.tiles.remove(Vector.add(v, offset).hashCode());
				
			}
		
		}
		
		for(Tile t :world.tiles.values()){
			if(t.color == Color.WHITE){
				if(t.neighbor(0,5) == null){
					t.color = Color.getHSBColor(30, 0.88f, 0.38f);
				} else if(t.neighbor(0,-1) != null){
					t.color = Color.GRAY;
				} else{
					t.color = Color.DARK_GRAY;
				}
			}
		}
		
//		for(Pair v : h){
//			int hash = v.hashCode();
//			
//			world.tiles.get(hash).color = Color.RED;
//			
//		}
		
//		System.out.println(world.tiles.toString());
		
//		loaded.add(0);
		
//		Graphics2D g = screen.image.createGraphics();
//		
//		g.setColor(Color.white);
//		
//		for(Point p : lPoints){
//			g.drawLine((int)p.x, (int)p.y, (int)p.x, 800);
//		}
//		
//		g.dispose();
//		
//		screen.pane.repaint();
		
		c = new Camera(screen);
		p = new Player(new Vector(0,150));
		world.addTile(p);
		
		loop();
	}
	
	private void loop(){
		
		while(running){
			
			if(System.currentTimeMillis() - lastTick >= 20){
				ticks++;
				lastTick = System.currentTimeMillis();
				
//				world.activeTilesBuffer.clear();
//				for(Tile t : world.activeTiles){
//					world.activeTilesBuffer.add(t);
//				}
				
				p.simulate();
				
				Graphics2D g = screen.image.createGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 800);
				
//				for(Zone z : world.getZones(c)){
//					for(Tile t : z.zone){
//						if(t.inViewRange(c)){
//							c.draw(t, g);
//						}
//					}
//				}
				
				for(int i : c.getCoords()){
					c.draw(world.tiles.get(i), g);
				}
				
//				for(Tile t : world.activeTiles){
//					t.simulate();
//				}
				
				g.dispose();
				screen.pane.repaint();
				
//				world.activeTiles.clear();
//				for(Tile t : world.activeTilesBuffer){
//					world.activeTiles.add(t);
//				}
				
//				System.out.println(Screen.keys.toString());
				
//				p.pos.print();
//				c.pos.print();
				
				
				
				if(Screen.keys.contains(KeyEvent.VK_S)){
					c.pos.y += 1;
				}
				if(Screen.keys.contains(KeyEvent.VK_W)){
					c.pos.y -= 1;
				}
				if(Screen.keys.contains(KeyEvent.VK_D)){
					c.pos.x += 1;
				}
				if(Screen.keys.contains(KeyEvent.VK_A)){
					c.pos.x -= 1;
				}
				if(Screen.keys.contains(KeyEvent.VK_Q)){
					screen.frame.dispose();
					System.exit(0);
				}
			
//				if(!loaded.contains(Math.floorDiv((int) c.x,800))){
//					Generator q = new Generator(300, 10, new Point(Math.floorDiv((int) c.x,800)*800, 400), new Point(Math.floorDiv((int) c.x,800)*800+800, 400));
//					List<Point> points = q.generate();
//					lists.add(Point.interpolate(points));
//					loaded.add(Math.floorDiv((int) c.x,800));
//				}
//				if(!loaded.contains(Math.floorDiv((int) c.x,800) + 1)){
//					Generator q = new Generator(300, 10, new Point(Math.floorDiv((int) c.x,800)*800+800, 400), new Point(Math.floorDiv((int) c.x,800)*800+1600, 400));
//					List<Point> points = q.generate();
//					lists.add(Point.interpolate(points));
//					loaded.add(Math.floorDiv((int) c.x,800) + 1);
//				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		String os = System.getProperty("os.name");
		
		if(os.equals("Mac OS X")){
			try {
				Runtime.getRuntime().exec("defaults write NSGlobalDomain ApplePressAndHoldEnabled -bool false");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		screen = new Screen(800, 800);
		new Main(screen);
		if(os.equals("Mac OS X")){
			try {
				Runtime.getRuntime().exec("defaults write NSGlobalDomain ApplePressAndHoldEnabled -bool true");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
