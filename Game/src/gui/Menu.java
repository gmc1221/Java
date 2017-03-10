package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import main.Main;
import main.Screen;

public class Menu {

	Button play;
	Button settings;
	Button quit;
	Button credits;
	Button selected;
	
	List<Button> buttons = new ArrayList<Button>();
	
	int index = 0;
	int moveTime = 0;
	
	public Menu(){
		
		play = new Button("Play", 0, 150, 30);
		settings = new Button("Settings", 1, 190, 30);
		quit = new Button("Quit", 2, 230, 30);
		credits = new Button("Credits", 3, 270, 30);
		
		buttons.add(play);
		buttons.add(settings);
		buttons.add(quit);
		buttons.add(credits);
		
	}
	
	public void menuCycle(){
		Graphics2D g = (Graphics2D) Screen.image.getGraphics();
		
		g.scale(Main.screenScale, Main.screenScale);
		
		action();
		draw(g);

		g.dispose();
		Screen.pane.repaint();
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 400, 300);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		drawCenter("Game", 60, g);
		
		for (Button b : buttons){
			b.draw(g);
		}
	}
	
	static void drawCenter(String str, int height, Color c, Graphics g){
		
		int width = g.getFontMetrics().stringWidth(str);
		g.setColor(c);
		g.drawString(str, 200 - width/2, height);
		
	}
	
	void drawCenter(String str, int height, Graphics g){
		
		int width = g.getFontMetrics().stringWidth(str);
		g.setColor(Color.WHITE);
		g.drawString(str, 200 - width/2, height);
		
	}
	
	public void action(){
		
		if(Screen.keys.contains(KeyEvent.VK_W) && index > 0 && Main.ticks - moveTime > 15){
			index--;
			moveTime = Main.ticks;
			
		}
		if(Screen.keys.contains(KeyEvent.VK_S) && index < 3 && Main.ticks - moveTime > 15){
			index++;
			moveTime = Main.ticks;
		}
		
		for (Button b : buttons){
			if(b.index == this.index && b.state != State.DISABLED){
				b.state = State.SELECTED;
				selected = b;
			} else{
				b.state = State.UNSELECTED;
			}
		}
		
		if(Screen.keys.contains(KeyEvent.VK_ENTER)){
			
			if(selected == play){
				Main.arcadeActive = true;
				Main.menuActive = false;
			}
			if(selected == quit){
				System.exit(0);
			}
			
		}
		
	}
	
	public enum State {SELECTED, UNSELECTED, PRESSED, DISABLED}
	
	class Button{
		
		String text;
		int index;
		int y;
		int fontSize;
		int width;
		Color c;
		
		State state = State.UNSELECTED;
		
		public Button(String n, int i, int y, int h){
			
			this.text = n;
			this.index = i;
			this.y = y;
			this.fontSize = h;
			Font font = new Font("TimesRoman", Font.PLAIN, fontSize);
			Canvas c = new Canvas();
			FontMetrics metrics = c.getFontMetrics(font);
			this.width = metrics.stringWidth(text);
			
		}
		
		public void draw(Graphics2D g){
			
			switch(this.state){
			
				case SELECTED:
					c = Color.RED;
					break;
				
				case UNSELECTED:
					c = Color.WHITE;
					break;
			
				case DISABLED:
					c = Color.GRAY;
					break;
				
				case PRESSED:
					c = Color.ORANGE;
					break;
			
			}
			
			g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			
			Menu.drawCenter(text, y, c, g);
			
		}
		
	}
	
}