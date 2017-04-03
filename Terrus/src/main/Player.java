package main;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Player extends Tile{

	long lastMove = 0;
	
	public Player(Vector pos) {
		super(pos);
		this.color = Color.YELLOW;
	}
	
	@Override
	public void simulate(){
		Vector oldPos = this.pos;
//		if(Screen.keys.contains(KeyEvent.VK_I)){
//			if(Main.world.tiles.get(new Vector(this.pos.x, this.pos.y -1).hashCode()) == null){
//				this.pos = new Vector(this.pos.x, this.pos.y -1);
//				updateReferences(oldPos);
//			}
//		}
		if(Screen.keys.contains(KeyEvent.VK_K) && Main.ticks - lastMove >= 20){
			lastMove = Main.ticks;
			if(Main.world.tiles.get(new Vector(this.pos.x, this.pos.y +1).hashCode()) == null){
				this.pos = new Vector(this.pos.x, this.pos.y +1);
				updateReferences(oldPos);
			}
		}
//		if(Screen.keys.contains(KeyEvent.VK_J)){
//			if(Main.world.tiles.get(new Vector(this.pos.x -1, this.pos.y).hashCode()) == null){
//				this.pos = new Vector(this.pos.x -1, this.pos.y);
//				updateReferences(oldPos);
//			}
//		}
//		if(Screen.keys.contains(KeyEvent.VK_L)){
//			if(Main.world.tiles.get(new Vector(this.pos.x +1, this.pos.y).hashCode()) == null){
//				this.pos = new Vector(this.pos.x +1, this.pos.y);
//				updateReferences(oldPos);
//			}
//		}
	}

}
