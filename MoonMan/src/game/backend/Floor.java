package game.backend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Floor extends GameObject {
	
	public Floor(int x, int y, int width, int height, ID id) {
		super(x, y, id);
		
	}

	public void tick() {		
	}


	public void render(Graphics g) {
		g.fillRect(x, y, width, height);

		g.setColor(Color.gray);
	}
	
	public Rectangle getTopBounds() {
		return new Rectangle(x, y, width, height/2);
	}
	
	
	
}
  