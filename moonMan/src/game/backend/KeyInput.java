package game.backend;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.object.ID;
import game.object.Player;

public class KeyInput extends KeyAdapter {

	private Boolean[] keyPressed = new Boolean[2];
	Player player;

	public KeyInput(Handler handler) {

	}
		
	boolean jumped = false;
	int lastKey = -1;
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < Handler.getObjects().size(); i++) {
			if(Handler.getObjects().get(i).getID() == ID.Player) {
				player = (Player) Handler.getObjects().get(i);
			}
		}
			if(player.getID() == ID.Player) {
				if(key == KeyEvent.VK_W && key != lastKey) {
					lastKey = key;
					if(!player.isJumping() && jumped == false) {
						player.setJumping(true);
						player.setVelY(-20);
						jumped = true;
					}
				}
				if(key == KeyEvent.VK_A) {
					player.setVelX(-5);
					keyPressed[0] = true;
				}
				if(key == KeyEvent.VK_D) {
					player.setVelX(5);
					keyPressed[1] = true;
				}
			}
		
	}
		
	public void keyReleased(KeyEvent e) {
		int key = e.getExtendedKeyCode();
		
		for(int i = 0; i < Handler.getObjects().size(); i++) {
			if(Handler.getObjects().get(i).getID() == ID.Player) {
				player = (Player) Handler.getObjects().get(i);
			}
		}
			
			if(player.getID() == ID.Player) {
				if(key == KeyEvent.VK_W) {
					lastKey = -1;
					jumped = false;
				}
				if(key == KeyEvent.VK_A) {
					player.setVelX(0);
					keyPressed[0] = false;
				}
				if(key == KeyEvent.VK_D) {
					player.setVelX(0);
					keyPressed[1] = false;
				}
				
				if(keyPressed[0] && !keyPressed[1]) {
					player.setVelX(-5);
				}
				if(!keyPressed[0] && keyPressed[1]) {
					player.setVelX(5);
				}
				if(!keyPressed[0] && !keyPressed[1]) {
					player.setVelX(0);
				}
				
			}
	}
}
