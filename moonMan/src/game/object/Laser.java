package game.object;
import game.backend.GameObject;
import java.lang.Math;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import game.graphics.BufferedImageLoader;
import game.backend.Game;

public class Laser extends GameObject{
	private static int mouseX, mouseY;
	Rectangle rect = new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT);
	BufferedImageLoader loader = new BufferedImageLoader();
	BufferedImage image;
	double angle, speed;
	
	public Laser(int x, int y, int mouseY, int mouseX, int width, int height, ID id) {
		super(x, y, id);
		Laser.mouseX = mouseX;
		Laser.mouseY = mouseY;
		
		image = loader.loadImage("./src/game/graphics/sprites/HoodMan.png");
		
		speed = 25;
		angle = Math.atan2(mouseX - x, mouseY - y);
		setVelX((int) (speed * Math.sin(angle)));
		setVelY((int) (speed * Math.cos(angle)));
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
	}

	public void render(Graphics g, int row, int col) {
		g.drawImage(image, x, y, 128, 128, null);
	}
}
