package game.backend;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
