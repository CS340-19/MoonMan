package game.object;
import game.backend.GameObject;
import java.lang.Math;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.graphics.BufferedImageLoader;

public class Laser extends GameObject{
	private static int mouseX, mouseY;
	BufferedImageLoader loader = new BufferedImageLoader();
	BufferedImage image;
	
	public Laser(int x, int y, int mouseY, int mouseX, int width, int height, ID id) {
		super(x, y, id);
		setVelX(10);
		setVelY(10);
		Laser.mouseX = mouseX;
		Laser.mouseY = mouseY;
		image = loader.loadImage("./src/game/graphics/sprites/HoodMan.png");
	}
	
	public void tick() {
		int xdiff = Math.abs(x - mouseX);
		int ydiff = Math.abs(y - mouseY);
		// mouse click is left of moonman
		if (mouseX < x) {
			// mouse click above moonman
			if (mouseY < y) {
				if (ydiff > xdiff) {
					y -= velY;
					x -= (xdiff / ydiff)*velX;
				}
				else {
					x -= velX;
					y -= (ydiff / xdiff)*velY;
				}
			}
			// mouse click below moonman
			else if (mouseY > y) {
				if (ydiff > xdiff) {
					y += velY;
					x -= (xdiff / ydiff)*velX;
				}
				else {
					x -= velX;
					y += (ydiff / xdiff)*velY;
				}
			}
		}
		// mouse click right of moonman
		else if (mouseX > x) {
			// mouse click above moonman
			if (mouseY < y) {
				if (ydiff > xdiff) {
					y -= velY;
					x += (xdiff / ydiff)*velX;
				}
				else {
					x += velX;
					y -= (ydiff / xdiff)*velY;
				}
			}
			// mouse click below moonman
			else if (mouseY > y) {
				if (ydiff > xdiff) {
					y += velY;
					x += (xdiff / ydiff)*velX;
				}
				else {
					x += velX;
					y += (ydiff / xdiff)*velY;
				}
			}
		}
	}

	public void render(Graphics g, int row, int col) {
		g.drawImage(image, x, y, 128, 128, null);
	}
}
