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
				/* Space key = Jump */
				if(key == KeyEvent.VK_SPACE && key != lastKey && player.is_floating == false) {
					lastKey = key;
					if(!player.isJumping() && jumped == false) {
						player.setJumping(true);
						player.setVelY(-20);
						jumped = true;
					}
				}
				/* W key = fly up */
				if(key == KeyEvent.VK_W && key != lastKey && player.is_floating == true) {
					lastKey = key;
					if(player.is_floating == true) {
						player.velY = -3;
					}
				}
				/* S key = Fly Down */
				if(key == KeyEvent.VK_S && key != lastKey && player.is_floating == true) {
					lastKey = key;
					if(player.is_floating == true) {
						player.velY = 3;
					}
				}
				/* W key = start hovering */
				if(key == KeyEvent.VK_W && key != lastKey) {
					lastKey = key;
					if(player.isIn_air()) {
						player.setFloating(true);
						player.firstCall = true;
						jumped = true;
					}
				}
				if(key == KeyEvent.VK_Q && key != lastKey && player.is_floating == true) {
					lastKey = key;
					player.setFloating(false);
					player.setFalling(true);
					jumped = true;
					player.fall();
				}
				if(key == KeyEvent.VK_A) {
					player.setVelX(-5);
					player.setFacing_right(false);
					player.setWalking(true);
					keyPressed[0] = true;
					if(keyPressed[1]) keyPressed[1] = false;
				}
				if(key == KeyEvent.VK_D) {
					player.setVelX(5);
					player.setFacing_right(true);
					player.setWalking(true);
					keyPressed[1] = true;
					if(keyPressed[0]) keyPressed[0] = false;
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
				if(key == KeyEvent.VK_SPACE) {
					lastKey = -1;
					jumped = false;
				}
				if(key == KeyEvent.VK_W) {
					lastKey = -1;
					player.setVelY(0);
				}
				if(key == KeyEvent.VK_Q) {
					lastKey = -1;
				}
				if(key == KeyEvent.VK_A) {
					player.setVelX(0);
					player.setWalking(false);
					keyPressed[0] = false;
				}
				if(key == KeyEvent.VK_D) {
					player.setVelX(0);
					player.setWalking(false);
					keyPressed[1] = false;
				}
				if(key == KeyEvent.VK_S) {
					lastKey = -1;
					player.setVelY(0);
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
