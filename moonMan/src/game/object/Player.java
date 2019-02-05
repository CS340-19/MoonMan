package game.object;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import game.backend.GameObject;
import game.backend.Handler;

public class Player extends GameObject {
	
	private static LinkedList<GameObject> floorBlocks = Handler.getObjects();
	
	public static final int WIDTH = 1280, HEIGHT = 720;	
	public BufferedImage moonMan;
	private Floor floor;
	Random r = new Random();
	private Boolean jumping = false;
	private Boolean falling = true;
	public int gravity = 1;
	
	
	public Player(int x, int y, int width, int height, ID id) {
		super(x, y, id);
		
		try {
			moonMan = ImageIO.read(new File("./src/game/graphics/sprites/MoonMan.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		fall();
		checkCollision();
	}
	
	public void render(Graphics g) {
		g.drawImage(moonMan, x, y, 64, 64, null);
		//g.fillRect(x, y, 50, 50);
	}
	
	private void checkCollision() {
		for(GameObject obj : floorBlocks) {
			if(obj.getID() == ID.Floor) {
				floor = (Floor) obj;
				if(getBottomBounds().intersects(floor.getTopBounds())) {
					velY = 0;
					y = floor.getY() - height;
					setJumping(false);
				}
			}
		}
	}
	
	public void fall() {
		if(falling) {
			velY += gravity;
		}
	}
	
	public Rectangle getBottomBounds() {
		return new Rectangle(x, y + height/2, 30, height/2);
	}

	public Boolean isJumping() {
		return jumping;
	}

	public void setJumping(Boolean jumping) {
		this.jumping = jumping;
	}	
	
}
