import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Created by cosmicTabulator (cT)
 * Friday March 5, 2017
 * 
 * V0.1.2
 * -I've forgotten to track changes prior to this version, Sorry!
 * 
 * ~I'll also keep a dev's note here from now on
 * 
 */


public class Main {
	
	public static final String version = "0.1.2";
	
	public static int score = 0;
	public static int highScore = 0;
	static boolean adding = false;
	public static Screen screen;
	float screenScale = 1;
	public boolean running = true;
	static int time;
	int lastTick;
	int timer;
	int spawnTotal;
	int spawnTime;
	int spawn;
	boolean levelSplash = true;
	static int killCount;
	static int tickCount = 20;
	public static float fps = (float)tickCount/1000;
	static int ticks;
	static int stageTicks = 0;
	static List<Entity> objects = new ArrayList<Entity>();
	static List<Entity> newObjects = new ArrayList<Entity>();
	static Player player;
	static World world;
	//static FileHandler fh;
	boolean spawned = false;
	
	Random rand = new Random();

	static int level;
	
	public Main(){
		
		Screen screen = new Screen(400, 300);
		
		player = new Player(new Vector(200,150,0),new Vector(0,0,0));
		
		world = new World();
		
		objects.add(player);
		
		mainLoop(screen);
		
		//fh = new FileHandler("highscore.txt");
		
		//fh.check();
		//highScore = Integer.parseInt(fh.read());
		
		
	}
	
	public static void main(String[] args) {
		new Main();

	}
	
	private void mainLoop(Screen screen){
		lastTick = (int) System.currentTimeMillis();
		while(running){
			time = (int) System.currentTimeMillis();
			if (time - lastTick > tickCount){
				lastTick = time;
				tickUpdate(screen);
				ticks++;
				stageTicks++;
				timer++;
				//System.out.println(ticks);
			}
		}
	}
	
	public void tickUpdate(Screen screen){
		if(Screen.keys.contains(KeyEvent.VK_Q)){
			System.exit(0);
		}
		newObjects.clear();
		for(Entity o : objects){
			newObjects.add(o);
		}
		Graphics2D g = (Graphics2D) Screen.image.getGraphics();
		g.scale(screenScale, screenScale);
		world.draw(g);
		if(!objects.contains(player)){
			levelSplash = false;
			newObjects.clear();
			drawKillScreen(g);
			if(Screen.keys.contains(KeyEvent.VK_R)){
				player = new Player(new Vector(200,150,0), new Vector(0,0,0));
				addObject(player);
				stageTicks = 0;
				spawned = false;
				level = 0;
				if(score > highScore){
					highScore = score;
					//fh.write(Integer.toString(highScore));
				}
				score = 0;
			}
		}
		eventSequence();
		if(levelSplash){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			world.drawCenter("Level " + level, 150, g);
		}
		for(Entity o : objects){
			o.onTick(ticks);
			o.getPos();
			o.draw(g);
		}
		g.dispose();
		screen.pane.repaint();
		//System.out.println(Screen.keys.toString());
		objects.clear();
		for(Entity o : newObjects){
			objects.add(o);
		}
	}
	
	public static void addObject(Entity o){
		int id = 1;
		boolean idTaken = true;
		while(idTaken){
			idTaken = false;
			for(Entity q : newObjects){
				if(q.id == id && !idTaken){
					idTaken = true;
					id++;
				}
			}
		}
		o.intId = id;
		newObjects.add(o);
	}
	
	private void eventSequence(){
					
		if(objects.contains(player)){
	
			if(level == 0){
				spawn = 5;
				spawnTime = 200;
				spawnTotal = 0;
				killCount = 0;
				level++;
				levelSplash = true;
			}
			
			System.out.println("Killcount " + killCount);
			System.out.println("Spawn " + spawn);
			System.out.println("Spawn Total " + spawnTotal);
			
			if(timer > spawnTime && spawnTotal < spawn){
				levelSplash = false;
				spawnTime = rate(level);
				timer = 0;
				spawnTotal++;
				if(level > 5){
					if(rand.nextInt(10) > 8){
						addObject(new Enemy(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25, 0)));
					}
					else if(rand.nextInt(10) > 4){
						addObject(new Shooter(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25, 0)));
					}
					else{
						addObject(new Seeker(new Vector(rand.nextInt(260) + 60, 0, 0)));
					}
				}
				else if(level > 2){
					if(rand.nextInt(10) > 4){
						addObject(new Shooter(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25, 0)));
					}
					else{
						addObject(new Seeker(new Vector(rand.nextInt(260) + 60, 0, 0)));
					}
				}
				else{
					addObject(new Seeker(new Vector(rand.nextInt(260) + 60, 0, 0)));
				}
			}
			
			if(killCount == spawn){
				for (Entity o: objects){
					if(o.id == 4 || o.enemy){
						o.kill();
					}
				}
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

	private int spawn(int level) {

		int out = (int) (level * 2.5) + rand.nextInt(level * 2);
		
		return out;
	}

	private int rate(int level) {
		int out;
		if(level > 9){
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
	
	void drawKillScreen(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		if(score > highScore){
			world.drawCenter("New Highscore! " + score, 140, g);
		}
		else{
			world.drawCenter("Score: " + score, 140, g);
		}
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		world.drawCenter("You Died!", 170, g);
		world.drawCenter("Press R to Respawn", 190, g);
	}

}
