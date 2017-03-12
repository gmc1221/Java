package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import gui.Arcade;
import main.Vector;

public class Boss1 extends Entity{

	private int lastSpawn;
	private int fireRate = 10;
	private double theta = Math.PI/4;
	boolean fire = true;
	double dir = 0.01;
	double startAngle;
	Random rand = new Random();
	public int hp = 100;
	
	public Boss1(Vector pos) {
		super(pos, new Vector(0,0,0));
		
		try {
		    img = ImageIO.read(getClass().getResource("/Textures/Enemy2.png"));
		} catch (IOException e) {
			System.out.println(this);
			System.out.println(e);
		}
		
	}

	@Override
	public void draw(Graphics2D g){
		AffineTransform identity = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		trans.setTransform(identity);
		trans.setToTranslation(pos.x, pos.y);
		trans.rotate(Math.toRadians(rot));
		g.drawImage(img, trans, null);
		g.setColor(Color.RED);
		g.fillRect((int)this.pos.x - hp/2 + width/2, (int)this.pos.y - 10, hp, 5);
	}
	
	@Override
	public void AI(int ticks){

//		dir = -0.0075*hp + 0.75;
		
		theta = theta + dir;
		
		if(hp < 0){
			kill();
		}
		
//		if(fire && (startAngle - theta < 2*Math.PI && startAngle - theta > -2*Math.PI)){
//			
//			if(ticks%2 == 0){
//				theta = theta + dir;
//				
//				Arcade.addObject(new EnemyBullet(this.pos, theta, 200));
//			}
//		} else{
//			fire = false;
//		}
		
		for(Entity o : collided(this)){
			if(o.id == 2){
				o.kill();
				hp--;
			}
		}
		
	}
	
	@Override
	public void checkActionMap(Set<Integer> s){
		if(ticks - lastSpawn > fireRate){
//			Arcade.addObject(new SpiralBullet (this.pos, 0 + theta));
//			Arcade.addObject(new SpiralBullet (this.pos, Math.PI/2 + theta));
//			Arcade.addObject(new SpiralBullet (this.pos, Math.PI + theta));
//			Arcade.addObject(new SpiralBullet (this.pos, 3*Math.PI/2 + theta));
			Arcade.addObject(new PhaseBullet (this.pos, 0 + theta));
			Arcade.addObject(new PhaseBullet (this.pos, Math.PI/2 + theta));
			Arcade.addObject(new PhaseBullet (this.pos, Math.PI + theta));
			Arcade.addObject(new PhaseBullet (this.pos, 3*Math.PI/2 + theta));
			lastSpawn = ticks;
		}
//		if(ticks-lastSpawn > fireRate){
//			fire = true;
//			lastSpawn = ticks;
//			dir = dir * -1;
//			theta = rand.nextDouble() * Math.PI * 2;
////			theta = Math.PI/2;
//			startAngle = theta;
//		}
	}
	
}
