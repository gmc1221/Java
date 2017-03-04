import java.awt.event.KeyEvent;
import java.util.Set;

public class Player extends Entity{

	int lastSpawn = 0;
	int lastSpawnEnemy;
	int fireRate = 5;
	
	public Player(Vector pos, Vector vel) {
		super(pos, vel);
		this.id = 1;
		this.intId = 0;
		this.c = 0.1f;
	}

	@Override
	public void checkActionMap(Set<Integer> s){
		
		if(s.contains(KeyEvent.VK_A)){
			vel.x = -v;
		}
		else if(s.contains(KeyEvent.VK_D)){
			vel.x = v;
		}
		else{
			vel.x = lerp(vel.x, 0, c);
		}
		
		if(s.contains(KeyEvent.VK_W)){
			vel.y = -v;
		}
		else if (s.contains(KeyEvent.VK_S)){
			vel.y = v;
		}
		else{
			vel.y = lerp(vel.y, 0, c);
		}
		
		if(s.contains(KeyEvent.VK_SPACE) && ticks - lastSpawn > fireRate){
			Main.addObject(new Bullet (new Vector(pos.x, pos.y, 0), new Vector(0, -400, 0)));
			lastSpawn = ticks;
		}
		
		if(s.contains(KeyEvent.VK_Q)){
			System.exit(0);
		}
		
		if(s.contains(KeyEvent.VK_E) && ticks - lastSpawnEnemy > 20){
			Main.addObject(new Shooter(new Vector(pos.x, pos.y - 100, 0)));
			lastSpawnEnemy = ticks;
		}
	}
	
	@Override
	public void onTick(int ticks){

		this.ticks = ticks;
		
		checkActionMap(Screen.keys);
		
		if(pos.x + this.width > 400){
			if(vel.x > 0){
				vel.x = 0;
			}
		}
		
		if(pos.x < 0){
			if(vel.x < 0){
				vel.x = 0;
			}
		}
		
		if(pos.y + this.height > 300){
			if(vel.y > 0){
				vel.y = 0;
			}
		}
		
		if(pos.y < 0){
			if(vel.y < 0){
				vel.y = 0;
			}
		}
		
		for(Entity o : collided(this)){
			if(o.melee){
				kill();
				break;
			}
		}
	}
}

