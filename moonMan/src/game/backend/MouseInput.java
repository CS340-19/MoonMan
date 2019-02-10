package game.backend;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.screens.Menu;

public class MouseInput extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		int mouse = e.getButton();
		Rectangle rect = new Rectangle(e.getX(), e.getY(), 1, 1);
		
		if(mouse == MouseEvent.BUTTON1) {
			switch(Game.state) {
			case GAME:
				break;
			case MENU:
				if(rect.intersects(Menu.play)) {
					Game.state = GameState.GAME;
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
