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

	Button arcade;
	Button settings;
	Button quit;
	Button credits;
	Button selected;
	Button campaign;
	
	List<Button> buttons = new ArrayList<Button>();
	
	int index = 0;
	int moveTime = 0;
	
	public Menu(){
		
		//Create a bunch of menu buttons
		
		arcade = new Button("Play Arcade", 0, 120, 30);
		campaign = new Button("Play Campaign", 1, 160, 30);
		settings = new Button("Settings", 2, 200, 30);
		quit = new Button("Quit", 3, 240, 30);
		credits = new Button("Credits", 4, 280, 30);
		
		//Add the to our list
		
		buttons.add(arcade);
		buttons.add(campaign);
		buttons.add(settings);
		buttons.add(quit);
		buttons.add(credits);
		
	}
	
	//Graphically render our menu
	public void menuCycle(){
		Graphics2D g = Screen.image.createGraphics();
		
		g.scale(Main.screenScale, Main.screenScale);
		
		//Query our buttons for selection, movement of selection, and activation
		action();
		//Render our buttons
		draw(g);

		g.dispose();
		Screen.pane.repaint();
	}
	
	//Render our menu Screen
	public void draw(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 400, 300);
		
		//Draw the title
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		drawCenter("Game", 60, g);
		
		//For each button, render it
		for (Button b : buttons){
			b.draw(g);
		}
	}
	
	//Draws a string at the center of the screen, given a height and color
	static void drawCenter(String str, int height, Color c, Graphics g){
		
		int width = g.getFontMetrics().stringWidth(str);
		g.setColor(c);
		g.drawString(str, 200 - width/2, height);
		
	}
	
	//Same as above, except without a provided color
	void drawCenter(String str, int height, Graphics g){
		
		int width = g.getFontMetrics().stringWidth(str);
		g.setColor(Color.WHITE);
		g.drawString(str, 200 - width/2, height);
		
	}
	
	//CHecks Key Inputs and updates the menu accordingly
	public void action(){
		
		//If W is pressed and we're not selecting the top button, move the selection up by one
		if(Screen.keys.contains(KeyEvent.VK_W) && index > 0 && Main.ticks - moveTime > 15){
			index--;
			moveTime = Main.ticks;
			
		}
		//If S is pressed and we're not selecting the bottom button, move the selection down by one
		if(Screen.keys.contains(KeyEvent.VK_S) && index < 3 && Main.ticks - moveTime > 15){
			index++;
			moveTime = Main.ticks;
		}
		
		//Search the buttons list for one whose index matches the selected one
		for (Button b : buttons){
			if(b.index == this.index && b.state != State.DISABLED){
				//Set that button's state to selected and assign it to a variable
				b.state = State.SELECTED;
				selected = b;
			} else{
				//Set everything else to unselected
				b.state = State.UNSELECTED;
			}
		}
		
		//If the enter key is being pressed
		if(Screen.keys.contains(KeyEvent.VK_ENTER)){
			
			//And the selected button is arcade
			if(selected == arcade){
				//Switch from the menu to the arcade
				Main.arcadeActive = true;
				Main.menuActive = false;
			}
			//If the selected button is quit, exit the program
			if(selected == quit){
				System.exit(0);
			}
			
			if(selected == campaign){
				Main.campaignActive = true;
				Main.menuActive = false;
			}
			
		}
		
	}
	
	public enum State {SELECTED, UNSELECTED, PRESSED, DISABLED}
	
	//Create a button class to use for the menu
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
			
			//Draw the button using different colors depending on it's state
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
