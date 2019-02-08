package game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import game.backend.GameObject;


public class Floor extends GameObject {
	public int Width;
	public int Height;

	public Floor(int x, int y, int width, int height, ID id) {
		super(x, y, id);
		
		Width = width;
		Height = height;
	}

	public void tick() {		
	}


	public void render(Graphics g, int row, int col) {
		g.fillRect(x, y, Width, Height);

		g.setColor(Color.gray);
	}
	
	public Rectangle getTopBounds() {
		return new Rectangle(x, y, Width, Height/2);
	}
	
	
	
}
  