import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

public class Main {

	long lastTick;
	
	Screen screen;
	boolean running = true;
	
	public Main(){
		
		screen = new Screen(800,800);
		
		while(running){
		
			if(screen.keys.contains(KeyEvent.VK_Q)){
				running = false;
			}
			
			if(screen.keys.contains(KeyEvent.VK_G)){
		
				Generator gen = new Generator(0.5f, 10, new Point(0, 0.5f), new Point(800, 0.5f));
				
				List<Point> lx = gen.generate1d();
				
				lx = Point.interpolate(lx);
				
				for(Point p : lx){
					p.print();
				}
				
				List<Point> ly = gen.generate1d();
				
				ly = Point.interpolate(ly);
				
				Graphics2D g = screen.image.createGraphics();
				
				Color val = Color.getHSBColor(0, 0, 0);
				
				for(int x = 0; x < 800; x++){
					for(int y = 0; y < 800; y++){
						
						Point px = Point.searchFor(lx, x);
						Point py = Point.searchFor(ly, y);
						
						float xVal = px.y;
						
						float yVal = py.y;
						
						float nVal = xVal + yVal;
						
						val = Color.getHSBColor(nVal, 1, 1);
						
						g.setColor(val);
						
						g.fillRect(x, y, 1, 1);
						
					}
				}
				
				g.dispose();
				
				screen.pane.repaint();
			}
		}
		
		System.exit(0);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Main();
		
	}

}
