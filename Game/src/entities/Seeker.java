package entities;
import java.io.IOException;

import javax.imageio.ImageIO;

import gui.Arcade;
import main.Main;
import main.Vector;

public class Seeker extends Entity{

	int spawnTime;
	int speed = 100;
	
	public Seeker(Vector pos) {
		super(pos, new Vector(0,0));
		this.id = 5;
		this.melee = true;
		this.enemy = true;
		spawnTime = Main.ticks;
		
		//Load texture from resources
		try {
		    img = ImageIO.read(getClass().getResource("/Textures/Enemy1.png"));
		} catch (IOException e) {
			System.out.println(this);
			System.out.println(e);
		}
	}
	
	@Override
	public void AI(int ticks){
		//If we're past level 3, increase the speed
		if(Arcade.level > 3){
			speed = 150;
		}
		//Wait 20 ticks after spawn before we do anything
		if(ticks - spawnTime > 20){
		
			Vector playerPos;

			//If there's still a player
			if(getPlayer() != null){
				playerPos = getPlayer().pos;
				//Get a vector cossosponding to the difference in position between this entity and the player
				Vector dir = Vector.add(Vector.mult(this.pos, -1), playerPos);
				//Reduce that vector such that it's largest component is 1
				dir = Vector.reduce(dir);
				//Set this entity's velocity to that vector multiplied by its speed
				this.vel = lerp(Vector.mult(dir, speed), this.vel, this.c);
			}
		}
		
		//If we're collided with a player bullet, die
		if(isCollided(2, this)){
			kill();
			Arcade.score = Arcade.score + 10;
		}
	}
}
