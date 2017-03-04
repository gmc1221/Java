import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static String score = "0";
	static boolean adding = false;
	public static Screen screen;
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
	boolean spawned = false;
	
	Random rand = new Random();

	static int level;
	
	public Main(){
		
		Screen screen = new Screen(400, 300);
		
		player = new Player(new Vector(200,150,0),new Vector(0,0,0));
		
		world = new World();
		
		objects.add(player);
		
		mainLoop(screen);
		
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
		newObjects.clear();
		for(Entity o : objects){
			newObjects.add(o);
		}
		if(!objects.contains(player)){
			newObjects.clear();
			if(Screen.keys.contains(KeyEvent.VK_R)){
				player = new Player(new Vector(200,150,0), new Vector(0,0,0));
				addObject(player);
				stageTicks = 0;
				spawned = false;
				level = 0;
				score = "0";
			}
		}
		eventSequence();
		Graphics g = Screen.image.getGraphics();
		world.draw(g);
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
		System.out.println("Kill Count: " + killCount);
		System.out.println("Spawn: " + spawn);
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
	
	public static void addScore(int value){
		score = Integer.toString((Integer.parseInt(Main.score) + value));
	}
	
	private void eventSequence(){
					
		if(objects.contains(player)){
	
			if(level == 0){
				spawn = 5;
				spawnTime = 50;
				spawnTotal = 0;
				killCount = 0;
				level++;
			}
			
			if(timer > spawnTime && spawnTotal < spawn){
				levelSplash = false;
				spawnTime = rate(level);
				timer = 0;
				spawnTotal++;
				if(level > 5){
					if(rand.nextInt(9) > 8){
						addObject(new Enemy(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25, 0)));
					}
					else if(rand.nextInt(9) > 4){
						addObject(new Shooter(new Vector(rand.nextInt(260) + 60, rand.nextInt(25) + 25, 0)));
					}
					else{
						addObject(new Seeker(new Vector(rand.nextInt(260) + 60, 0, 0)));
					}
				}
				if(level > 2){
					if(rand.nextInt(9) > 4){
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
				System.out.println("Next Level");
				killCount = 0;
				level++;
				spawn = spawn(level);
				timer = 0;
				spawnTotal = 0;
				spawnTime = 50;
				levelSplash = true;
			}
		}
	}

	private int spawn(int level) {

		int out = (int) (level * 2.5);
		
		return out;
	}

	private int rate(int level) {
		int out;
		if(level > 10){
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

}
