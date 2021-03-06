import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class KeyTest {

	Screen screen;
	
	public KeyTest(){
		
		screen = new Screen(80,80);
		
		MoveAction up = new MoveAction(1);
		MoveAction down = new MoveAction(3);
		MoveAction left = new MoveAction(2);
		MoveAction right = new MoveAction(0);
		
		Quit quit = new Quit();
		
		KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);
		KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
		KeyStroke leftKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
		KeyStroke rightKey = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);
		KeyStroke exitKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		
		InputMap input = screen.pane.getInputMap();
		ActionMap action = screen.pane.getActionMap();
		
		input.put(upKey, "up");
		input.put(downKey, "down");
		input.put(leftKey, "left");
		input.put(rightKey, "right");
		input.put(exitKey, "quit");
		
		action.put("up", up);
		action.put("down", down);
		action.put("left", left);
		action.put("right", right);
		action.put("quit", quit);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new KeyTest();
		
		while(true){
			
		}
		
	}
	
	private class Quit extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			screen.frame.dispose();
			System.exit(0);
			
		}
		
	}
	
	private class MoveAction extends AbstractAction{
		
		int direction;
		
		public MoveAction(int dir){
			
			this.direction = dir;
			System.out.println(this.direction);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println(this.direction);
			
			switch (this.direction){
			
				case 0:
					System.out.println("Right");
					break;
					
				case 1:
					System.out.println("Up");
					break;
					
				case 2:
					System.out.println("Left");
					break;
					
				case 3:
					System.out.println("Down");
					break;
			
			}
			
		}
		
	}

}
