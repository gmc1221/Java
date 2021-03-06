package main;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Player extends Tile{

	long lastMove = 0;
	
	public Player(Vector pos) {
		super(pos);
		this.isPlayer = true;
		this.color = Color.YELLOW;
	}
	
	@Override
	public void simulate(){
		Vector oldPos = this.pos;
		if(Screen.keys.contains(KeyEvent.VK_K) && Main.ticks - lastMove >= 5){
			lastMove = Main.ticks;
			if(neighbor(0, -1) == null){
				this.pos = new Vector(this.pos.x, this.pos.y +1);
				updateReferences(oldPos);
			}
		}
		if(Screen.keys.contains(KeyEvent.VK_L) && Main.ticks - lastMove >= 5){
			lastMove = Main.ticks;
			if(neighbor(-1, 0) == null){
				this.pos = new Vector(this.pos.x +1, this.pos.y);
				updateReferences(oldPos);
			}
		}
		if(Screen.keys.contains(KeyEvent.VK_J) && Main.ticks - lastMove >= 5){
			lastMove = Main.ticks;
			if(neighbor(1, 0) == null){
				this.pos = new Vector(this.pos.x -1, this.pos.y);
				updateReferences(oldPos);
			}
		}
		if(Screen.keys.contains(KeyEvent.VK_I) && Main.ticks - lastMove >= 5){
			lastMove = Main.ticks;
			if(neighbor(0, 1) == null){
				this.pos = new Vector(this.pos.x, this.pos.y -1);
				updateReferences(oldPos);
			}
		}
	}

}
