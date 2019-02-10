package game.object;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import game.backend.GameObject;
import game.backend.Handler;
import game.graphics.BufferedImageLoader;
import game.graphics.SpriteSheetResolver;

public class Player extends GameObject {
	
	private static LinkedList<GameObject> floorBlocks = Handler.getObjects();
	
	public static final int WIDTH = 1280, HEIGHT = 720;	
	public BufferedImage moonMan_ss = null;
	public BufferedImage moonMan = null;
	public SpriteSheetResolver ss;
	private Floor floor;
	private SquidMan squidman;
	Random r = new Random();
	private Boolean jumping = false;
	private Boolean falling = true;
	public int gravity = 1;
	public Boolean facing_right = true;
	public Boolean walking = false;
	public Boolean in_air = false;
	public int walk_sleep_counter = 0;
	public int which_step = 0;
	public int w_row = 1;
	public int w_col = 1;
	public int width;
	public int height;
	
	public Player(int x, int y, int tmp_width, int tmp_height, ID id) {
		super(x, y, id);
		width = tmp_width;
		height = tmp_height;
	
		BufferedImageLoader loader = new BufferedImageLoader();
		moonMan_ss = loader.loadImage("./src/game/graphics/sprites/MoonMan_SS.png");
		ss = new SpriteSheetResolver(moonMan_ss);
		
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		fall();
		checkCollision();
		
		if(jumping == true) {
			in_air = true;
			walking = false;
		}else {
			in_air = false;
		}

		if(walking == true) {
			if(walk_sleep_counter == 0) {
				if(which_step == 0) {
					w_row = 1;
					w_col = 5;
				} else if(which_step == 1) {
					w_row = 2;
					w_col = 5;				
				} else if(which_step == 2) {
					w_row = 3;
					w_col = 5;
				} else if(which_step == 3) {
					w_row = 4;
					w_col = 5;
					which_step = 0;
				} else {
					w_row = 1;
					w_col = 5;
				}
				which_step++;
			}
			walk_sleep_counter++;
			if(walk_sleep_counter == 12) {
				walk_sleep_counter = 0;
			}
		} else {
			which_step = 0;
			walk_sleep_counter = 0;
		}

	}

	public void render(Graphics g, int row, int col ) {
		
		if(in_air == true) {
			row = 4;
			col = 4;
			walking = false;
		}
		if(walking == true) {
			row = w_row;
			col = w_col;
		}

		
		moonMan = ss.grabImage(row, col, 64, 64);
		
		if(facing_right == false) {
			// Flip the image horizontally
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-moonMan.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			moonMan = op.filter(moonMan, null);	
		}	
		g.drawImage(moonMan,  x, y -20, 128, 128, null);
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
	
	public Boolean isFacing_right() {
		return facing_right;
	}
	
	public void setFacing_right(Boolean facing_right) {
		this.facing_right = facing_right;
	}
	
	public Boolean isWalking() {
		return walking;
	}
	
	public void setWalking(Boolean walking) {
		this.walking = walking;
	}
	
	public Boolean isIn_air() {
		return in_air;
	}
	
	public void setIn_air(Boolean in_air) {
		this.in_air = in_air;
	}
}
