package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Set;

import org.json.JSONObject;

import entities.Beamer;
import entities.Dodger;
import entities.Entity;
import entities.Player;
import entities.Seeker;
import entities.Shooter;
import main.FileHandler;
import main.Leaderboards;
import main.Main;
import main.Screen;
import main.Vector;

public class Arcade extends Environment{
	
	public static int score = 0;
	public static int highScore = 0;
	boolean scoreSaved = false;
	boolean highscored = false;
	float screenScale;
	int spawnTotal;
	int spawnTime;
	int spawn;
	public static int level; 
	boolean spawned = false;
	boolean levelSplash = true;
	
	public Arcade(){
		
		super();
		
		//create a new file reader to read all saved scores
		fh = new FileHandler("highscore.json");
		
		//Load read data into a JSON Handler
		JSONObject scores = fh.read();

		//Load JSON data into a score reader
		lb = new Leaderboards(scores);
		
		//Get the highest score found in that file
		highScore = lb.getHighScore();
		
	}
	
	@Override
	protected void draw(Graphics2D g){
		//If the player has been killed (not in the objects list) save the score, Draw a death screen, and request a re-spawn
		if(!objects.contains(player)){
			//If our score hasn't been saved
			if(!scoreSaved){
				//Save it
				lb.addScore(score);
				fh.write(lb.get());
				scoreSaved = true;
				
			}
			//Don't draw new level screen since we're dead
			levelSplash = false;
			newObjects.clear();
			//Render a death screen with our graphics object
			drawKillScreen(g);
			//If R is pressed, re-spawn the player and reset the game
			if(Screen.keys.contains(KeyEvent.VK_R)){
				player = new Player(new Vector(200,150), new Vector(0,0));
				addObject(player);
				spawned = false;
				level = 0;
				score = 0;
				highscored = false;
				scoreSaved = false;
			}
		}
		//If we've advanced to a new level
		if(levelSplash){
			//Draw a New level banner
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			world.drawCenter("Level " + level, 150, g);
		}
	}
	
	//This controls enemy spawning
	@Override
	protected void eventSequence() {
		
		//If our player is still alive
		if(objects.contains(player)){
	
			//If we're on level "0", set various parameters to base values
			if(level == 0){
				spawn = 3;
				spawnTime = 10;
				spawnTotal = 0;
				killCount = 0;
				level++;
				levelSplash = true;
			}
			
			//If our designated spawn time has elapsed and we haven't reached our maximum enemy spawn count
			if(timer > spawnTime && spawnTotal < spawn){
				levelSplash = false;
				//Get a new spawn time based on the current level
				spawnTime = rate(level);
				//Reset our timer;
				timer = 0;
				//Increment our spawn counter
				spawnTotal++;
				//If we're past level 8
				if(level > 8){
					//Let there be a 1/20 chance to spawn a dodger
					if(rand.nextInt(20) > 18){
						addObject(new Dodger(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25)));
					}
					//Otherwise a 2/5 chance to spawn a beamer
					else if(rand.nextInt(10) > 7){
						addObject(new Beamer(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25)));
					}
					//Otherwise a 1/2 chance to spawn a shooter
					else if(rand.nextInt(10) > 4){
						addObject(new Shooter(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25)));
					}
					//And otherwise, spawn a seeker
					else{
						addObject(new Seeker(new Vector(rand.nextInt(260) + 60, 0)));
					}
				}
				//Otherwise, if we're past level 5
				else if(level > 5){
					//Let there be a 1/10 chance to spawn a Beamer
					if(rand.nextInt(10) > 8){
						addObject(new Beamer(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25)));
					}
					//Otherwise, a 1/2 chance to spawn a shooter
					else if(rand.nextInt(10) > 4){
						addObject(new Shooter(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25)));
					}
					//Otherwise spawn a seeker
					else{
						addObject(new Seeker(new Vector(rand.nextInt(260) + 60, 0)));
					}
				}
				//Otherwise, if we've past level 2
				else if(level > 2){
					//Let there be a 1/2 chance to spawn a shooter
					if(rand.nextInt(10) > 4){
						addObject(new Shooter(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25)));
					}
					//Otherwise, spawn a seeker
					else{
						addObject(new Seeker(new Vector(rand.nextInt(260) + 60, 0)));
					}
				}
				//Otherwise, just spawn a seeker
				else{
					addObject(new Seeker(new Vector(rand.nextInt(260) + 60, 0)));
				}
			}
			//If the player's killed everything we've spawned
			if(killCount >= spawn){
				//Remove any enemy bullets
				for (Entity o: objects){
					if(o.id == 4 || o.enemy){
						o.kill();
					}
				}
				//Reset our counters and increment the level
				killCount = 0;
				level++;
				spawn = spawn(level);
				timer = 0;
				spawnTotal = 0;
				spawnTime = 200;
				levelSplash = true;
			}
		}
	}
	
	@Override
	protected void checkActionMap(Set<Integer> s){
		//If Q is being pressed, return to the menu and kill the session;
		if(Screen.keys.contains(KeyEvent.VK_Q)){
			Main.arcadeActive = false;
			Main.menuActive = true;
			exit();
			return;
		}
	}
	
	//Draw a death screen
	private void drawKillScreen(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		//If we reached a highscore, say so
		if(score > highScore || highscored){
			highscored = true;
			highScore = score;
			world.drawCenter("New Highscore! " + score, 140, g);
		}
		//Otherwise, just write our score
		else{
			world.drawCenter("Score: " + score, 140, g);
		}
		//Tell the player they've dies and to press r to continue
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		world.drawCenter("You Died!", 170, g);
		world.drawCenter("Press R to Respawn", 190, g);
	}

	//Return a number of enemies to spawn as a random linear function of the level
	private int spawn(int level) {

		int out = (int) (level * 2.5) + rand.nextInt(level * 2);
		
		return out;
	}

	//Return a spawn rate as a random function of the level
	private int rate(int level) {
		int out;
		if(level > 7){
			out = rand.nextInt(10) + 10;
		}
		else if(level > 4){
			out = rand.nextInt(15) + 20;
		}
		else{
			out = rand.nextInt(30) + 30;
		}
		
		return out;
	}
	
	//Initialize our environment
	@Override
	protected void init(){
		//Get the all time high score
		highScore = lb.getHighScore();
		//Spawn in the player
		player = new Player(new Vector(200, 200), new Vector(0,0));
		objects.add(player);
		player.env = this;
		doneInit = true;
	}
	
	//Exit the session
	@Override
	protected void exit(){
		//Clear our objects list
		objects.clear();
		//Reset the score
		score = 0;
		//Make sure to initialize then next time we start playing
		doneInit = false;
	}
	
}
