import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Main {

	public static Screen screen;
	public boolean running = true;
	int time;
	int lastTick;
	int tickCount = 50;
	int ticks;
	Draw draw;
	int x = 200;
	int y = 0;
	
	static Object ball;
	
	public Main(){
		
		Screen screen = new Screen(400, 300);
		draw = new Draw();
		
		ball = new Object(new Vector(200,0),new Vector(0,1));
		
//		Font f = new Font(Font.MONOSPACED, Font.PLAIN, 14);
//		
//		draw.g.setFont(f);
//		
//		draw.g.drawString("A@B", 0, 10);
//		draw.g.drawLine(0, 14, 400, 14);
//		draw.g.drawLine(7, 0, 7, 300);
//		draw.g.drawLine(14, 0, 14, 300);
		
		Graphics g = draw.getDraw();
		
		g.drawString("Terrus", 200, 100);
		g.drawString("v 0.0", 200, 200);
		
		g.dispose();
		
		mainLoop();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();

	}
	
	private void mainLoop(){
		lastTick = (int) System.currentTimeMillis();
		while(running){
			time = (int) System.currentTimeMillis();
			if (time - lastTick > tickCount){
				lastTick = time;
				tickUpdate();
				ticks++;
				System.out.println(ticks);
			}
		}
	}
	
	public void tickUpdate(){
//		Graphics g = draw.getDraw();
//		g.setColor(Color.BLACK);
//		g.drawRect(0, 0, 400, 300);
//		g.setColor(Color.RED);
//		g.fillRect(x, y, 10, 10);
//		g.dispose();
		Screen.screen.repaint();
		ball.getPos();
		ball.draw();
		
	}

}
