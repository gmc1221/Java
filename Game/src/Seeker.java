import java.awt.Color;
import java.awt.Graphics;

public class Seeker extends Entity{

	int spawnTime;
	int speed = 100;
	
	public Seeker(Vector pos) {
		super(pos, new Vector(0,0,0));
		this.id = 5;
		this.melee = true;
		this.enemy = true;
		spawnTime = Main.ticks;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Graphics g){
		g.setColor(Color.GREEN);
		g.fillOval((int)pos.x, (int)pos.y, this.width, this.height);
	}
	
	@Override
	public void AI(int ticks){
		if(Main.level > 3){
			speed = 150;
		}
		if(ticks - spawnTime > 20){
		
			Vector playerPos;

			if(getPlayer() != null){
				playerPos = getPlayer().pos;
				Vector dir = Vector.add(Vector.mult(this.pos, -1), playerPos);
				dir = Vector.reduce(dir);
				this.vel = lerp(Vector.mult(dir, speed), this.vel, this.c);
			}
		}
		
		if(isCollided(2, this)){
			kill();
			Main.score = Main.score + 10;
		}
	}
}
