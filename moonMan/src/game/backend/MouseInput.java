package game.backend;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.object.*;
import game.screens.Menu;
import game.screens.Pause;

public class MouseInput extends MouseAdapter {
	
	public static int los = 1;
	
	@Override
	public void mousePressed(MouseEvent e) {
		int mouse = e.getButton();
		Player player = null;
		
		if(mouse == MouseEvent.BUTTON1) {
			switch(Game.state) {
				case GAME:
					for(int i = 0; i < Handler.getObjects().size(); i++) {
						if(Handler.getObjects().get(i).getID() == ID.Player) {
							player = (Player) Handler.getObjects().get(i);
						}
					}
					if(los == 1) {
						try {
							game.sounds.audioPlayer.main("./src/game/sounds/wav_files/laser.wav");
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Handler.addObject(new Laser(player.x, player.y - 40, e.getY(), e.getX(), 10, 10, ID.Laser));
						los = 0;
					}
					break;
				case MENU:
					break;
				case OPTIONS:
					break;
				case PAUSE:
					break;
				case SPLASH_SCREEN:
					break;
				case QUIT:
					break;
				default:
					break;
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int mouse = e.getButton();
		Rectangle rect = new Rectangle(e.getX(), e.getY(), 1, 1);
		
		if(mouse == MouseEvent.BUTTON1) {
			switch(Game.state) {
				case GAME:
					for(int i = 0; i < Handler.getObjects().size(); i++) {
						if(Handler.getObjects().get(i).getID() == ID.Laser) {
							los = 1;
						}
					}
					break;
				case MENU:
					if(rect.intersects(Menu.play)) {
						try {
							game.sounds.audioPlayer.main("./src/game/sounds/wav_files/select.wav");
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Game.state = GameState.GAME;
						for(int i = 0; i < Handler.getObjects().size(); i++) {
							if(Handler.getObjects().get(i).getID() == ID.Player) {
								Player player = (Player) Handler.getObjects().get(i);
								player.reset();
							}
							if(Handler.getObjects().get(i).getID() == ID.ForeGround) {
								Foreground foreground = (Foreground) Handler.getObjects().get(i);
								foreground.reset();
							}
							if(Handler.getObjects().get(i).getID() == ID.Enemy) {
								SquidMan squidman = (SquidMan) Handler.getObjects().get(i);
								squidman.reset();
							}
						}
					} else if(rect.intersects(Menu.quit)) {
						Game.state = GameState.QUIT;
					}
					break;
				case OPTIONS:
					break;
				case PAUSE:
					if(rect.intersects(Menu.play)) {
						Game.state = GameState.GAME;
						for(int i = 0; i < Handler.getObjects().size(); i++) {
							if(Handler.getObjects().get(i).getID() == ID.Pause) {
								Pause pause = (Pause) Handler.getObjects().get(i);
								Handler.removeObject(pause);
							}
						}
						Game.Pause =false;
						Game.state = GameState.GAME;
					} else if(rect.intersects(Menu.quit)) {
						Game.state = GameState.QUIT;
					}
					break;
				case SPLASH_SCREEN:
					break;
				case QUIT:
					System.exit(0);
					break;
				default:
					break;
			}
		}
	}
}
