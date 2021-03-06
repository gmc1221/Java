package entities;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gui.Arcade;
import gui.Environment;
import main.Main;
import main.Screen;
import main.Vector;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


//Base class for all entities
@SuppressWarnings("unused")
public class Entity {

	Vector pos;
	Vector vel;
	public Environment env = null;
	int width = 10;
	int height = 10;
	int ticks = 0;
	float c = 0.05f;
	int v = 75;
	public int id = 0;
	public int intId = -1;
	double rot = 0;
	boolean melee = false;
	public boolean enemy = false;
	boolean useRect = true;
	boolean isBoss = false;
	
	Rectangle2D shape;
	
	BufferedImage img;
	
	public Entity(Vector pos, Vector vel){
		//Get the position and velocity vector from the constructor
		this.pos = pos;
		this.vel = vel;
		
	}
	
	//Get the next position by adding the velocity vector (multiplied by the fraction of a second one tick covers) to the current position
	public void getPos(){
		pos = Vector.add(pos, Vector.mult(vel, Main.fps));
	}
	
	//Draws the entity
	public void draw(Graphics2D g){
		//Create a transform to apply rotation and movement effects to our image
		AffineTransform identity = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		trans.setTransform(identity);
		//Translate our transform by the position
		trans.setToTranslation(pos.x, pos.y);
		//Rotate our transform by the rotation
		trans.rotate(Math.toRadians(rot));
		//Draw our image using the transform
		g.drawImage(img, trans, null);
	}
	
	public void checkActionMap(Set<Integer> s){
		
		//Leave Empty for other Class utilizations
		
	}
	
	public void onTick(int ticks){
		
		this.ticks = ticks;
		checkActionMap(Screen.keys);
		AI(ticks);
		//Leave Empty for other Class utilizations
		
	}
	
	public void AI(int ticks){
		
		//Write your AIs here
		
	}
	
	//Linear interpolation
	double lerp(double value, double target, double weight){
		
		double out = (value + target*weight)/(1 + weight);
		
		return out;
	}
	
	//Vector interpolation
	Vector lerp(Vector value, Vector target, double c) {

		Vector out;
		
		out = new Vector(lerp(value.x, target.x, c),lerp(value.y, target.y, c));
		
		return out;
	}
	
	//Removes this entity from the objects list, and thus game
	public void kill(){
		env.newObjects.remove(this);
		if(this.enemy){
			env.killCount++;
		}
	}
	
	//Determines if two entities are collided with each other
	boolean inBounds(Entity o, Entity e){
		
		boolean out = false;
		
		AffineTransform identity = new AffineTransform();
		
		//Gets area from the rotation and position of the provided entities
		Shape rect1 = new Rectangle2D.Double(o.pos.x, o.pos.y, o.width, o.height);
		
		AffineTransform trans1 = new AffineTransform();
		
		trans1.setTransform(identity);
		trans1.rotate(Math.toRadians(o.rot));
		
		rect1 = trans1.createTransformedShape(rect1);
		
		Shape rect2 = new Rectangle2D.Double(e.pos.x, e.pos.y, e.width, e.height);
		

		AffineTransform trans2 = new AffineTransform();
		
		trans2.setTransform(identity);
		trans2.rotate(Math.toRadians(o.rot));
		
		rect2 = trans2.createTransformedShape(rect2);
		
		Area a1 = new Area(rect1);
		Area a2 = new Area(rect2);
		
		//Sets the area of a1 to the area that also intersects a2
		a1.intersect(a2);
		
		//If a1 still has an area (a1 and a2 intersect) the return true
		out = !a1.isEmpty();
		
		return out;
		
	}
	
	//Creates a list of entities collided with c
	List<Entity> collided(Entity c){
		List<Entity> s = new ArrayList<Entity>();
		
		for (Entity o : env.objects){
			if(inBounds(o, c)){
				s.add(o);
			}
		}
		
		return s;
	}
	
	//OUtputs true if c is collided with an entity with id of id
	boolean isCollided(int id, Entity c){
		boolean out = false;
		for(Entity o : env.objects){
			if(inBounds(o, c)){
				if(o.id == id){
					out = true;
				}
			}
		}
		
		return out;
		
	}
	
	//OUuputs the closest entity to o, including or excluding everything with ids in idArray (inclusion/exclusion given by ignore)
	Entity closest(Entity o, int[] idArray, boolean ignore){
		
		double x;
		double y;
		double h;
		double closestDist = Double.MAX_VALUE;
		Entity closest = null;
		
		for(Entity i: env.objects){
			
			//Computes distance to i
			x = i.pos.x - o.pos.x;
			y = i.pos.y - o.pos.y;
			h = Math.sqrt(x*x + y*y);
			
			//If this distance is closer than anything else yet
			if(h < closestDist){
				boolean inArray = false;
				//CHecks to see if i's id is in our array
				for(int q : idArray){
					if(i.id == q){
						inArray = true;
					}
				}
				//If it's in the array and we're not ignoring or it isn't and we are ignoring
				if(inArray != ignore){
					//Set i to our closest object
					closestDist = h;
					closest = i;
				}
			}
		}
		
		return closest;
		
	}
	
	//Finds and return our player from the objects list
	Entity getPlayer(){
		
		Entity player = null;
		
		for(Entity o : env.objects){
			if(o.id == 1){
				player = o;
			}
		}
		
		
		return player;
	}
	
	//Computes the distance between to position vectors
	double dist(Vector pos1, Vector pos2){
		
		double distance = 0;
		
		distance = Math.sqrt(Math.pow((pos1.x - pos2.x), 2) + Math.pow((pos1.y - pos2.y), 2));
		
		return distance;
	}
	
}
