package game.backend;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.object.*;
import game.screens.Menu;

public class MouseInput extends MouseAdapter {
	
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
					Handler.addObject(new Laser(player.x, player.y, e.getY(), e.getX(), 40, 40, ID.Laser));
					break;
				case MENU:
					break;
				case OPTIONS:
					break;
				case PAUSE:
					break;
				case SPLASH_SCREEN:
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
							Handler.removeObject(Handler.getObjects().get(i));
						}
					}
					break;
				case MENU:
					if(rect.intersects(Menu.play)) {
						Game.state = GameState.GAME;
					} else if(rect.intersects(Menu.quit)) {
						Game.state = GameState.QUIT;
					}
					break;
				case OPTIONS:
					break;
				case PAUSE:
					break;
				case SPLASH_SCREEN:
					break;
				default:
					break;
			}
		}
	}
}
