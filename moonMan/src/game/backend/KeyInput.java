package game.backend;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.object.Foreground;
import game.object.ID;
import game.object.Player;
import game.screens.Pause;

public class KeyInput extends KeyAdapter {

	private Boolean[] keyPressed = new Boolean[2];
	Player player;
	public KeyInput(Handler handler) {

	}
		
	public static boolean jumped = false;
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
				if(key == KeyEvent.VK_SPACE && key != lastKey && player.is_floating == false && player.in_air == false) {
					lastKey = key;
					if(!player.isJumping() && jumped == false) {
						player.setJumping(true);
						try {
							game.sounds.audioPlayer.main("./src/game/sounds/wav_files/jump.wav");
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
					player.pressingA = true;
					Foreground.checkCollision();
					if((Foreground.getLeftBounds().intersects(player.getBottomBounds())) && Foreground.Begining == false) {
						player.setVelX(0);
						player.stay = true;
					}else if((Foreground.getBeginingBounds().intersects(player.getBottomBounds())) && Foreground.Begining == true) {
						player.setVelX(0);
						player.stay = false;
					}else{
						player.setVelX(-5);
						player.stay = false;
					}
					
					player.setFacing_right(false);
					player.setWalking(true);
					keyPressed[0] = true;
					if(keyPressed[1]) keyPressed[1] = false;
				}
				if(key == KeyEvent.VK_D) {
					player.pressingD = true;
					Foreground.checkCollision();
					if((Foreground.getRightBounds().intersects(player.getBottomBounds())) && Foreground.Ending == false) {
						player.setVelX(0);
						player.stay = true;
					}else if((Foreground.getEndingBounds().intersects(player.getBottomBounds())) && Foreground.Ending == true) {
						Game.state = GameState.MENU;
						//player.setVelX(0);
						//player.stay = false;
					}else {
						player.setVelX(5);
						player.stay = false;
					}
					
					player.setFacing_right(true);
					player.setWalking(true);
					keyPressed[1] = true;
					if(keyPressed[0]) keyPressed[0] = false;
				}
				if(key == KeyEvent.VK_ESCAPE) {
					if(Game.Pause == false) {
						Game.Pause = true;
						Handler.addObject(new Pause(Game.centerX, Game.centerY, ID.Pause));
						Game.state = GameState.PAUSE;
					}
					
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
					player.pressingA = false;
					keyPressed[0] = false;
				}
				if(key == KeyEvent.VK_D) {
					player.setVelX(0);
					player.pressingD = false;
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
