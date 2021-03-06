package entities;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Vector;

public class Bullet extends Entity{
	
	public Bullet(Vector pos, Vector vel) {
		super(pos, vel);
		this.id = 2;
		this.width = 3;
		this.height = 5;
		
		try {
		    img = ImageIO.read(getClass().getResource("/Textures/Bullet1.png"));
		} catch (IOException e) {
			System.out.println(this);
			System.out.println(e);
		}
	}

	@Override
	public void onTick(int ticks){
		
		this.ticks = ticks;
		
		if(pos.y > 400 || pos.y < 0){
			kill();
		}
		
	}
	
}
