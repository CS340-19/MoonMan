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
	public Boolean is_floating = false;
	public Boolean is_flying = false;
	public Boolean firstCall = false;
	public int secCount = 0;
	public int walk_sleep_counter = 0;
	public int which_step = 0;
	public int w_row = 1;
	public int w_col = 1;
	public int width;
	public int height;
	public int floating_sleep_counter = 0; /* Needed so that floating only lasts for a set amount of time */
	public int jetpack_fuel = 100;
	public static int jf_w = 1;
	public static int jf_h = 1;
	public static int X = 0;
	
	public Player(int x, int y, int tmp_width, int tmp_height, ID id) {
		super(x, y, id);
		X = x;
		width = tmp_width;
		height = tmp_height;
	
		BufferedImageLoader loader = new BufferedImageLoader();
		moonMan_ss = loader.loadImage("./src/game/graphics/sprites/MoonMan_SS.png");
		ss = new SpriteSheetResolver(moonMan_ss);
		
	}
	
	public void tick() {
		x += velX;
		X = x;
		y += velY;
		
		fall();
		checkCollision();
		
		if(jetpack_fuel > 90 && jetpack_fuel <= 100) {
			jf_w = 1;
			jf_h = 1;
		}else if(jetpack_fuel > 80 && jetpack_fuel <= 90) {
			jf_w = 2;
			jf_h = 1;
		}else if(jetpack_fuel > 70 && jetpack_fuel <= 80) {
			jf_w = 3;
			jf_h = 1;
		}else if(jetpack_fuel > 60 && jetpack_fuel <= 70) {
			jf_w = 1;
			jf_h = 2;
		}else if(jetpack_fuel > 50 && jetpack_fuel <= 60) {
			jf_w = 2;
			jf_h = 2;
		}else if(jetpack_fuel > 40 && jetpack_fuel <= 50) {
			jf_w = 3;
			jf_h = 2;
		}else if(jetpack_fuel > 30 && jetpack_fuel <= 40) {
			jf_w = 1;
			jf_h = 3;
		}else if(jetpack_fuel > 20 && jetpack_fuel <= 30) {
			jf_w = 2;
			jf_h = 3;
		}else if(jetpack_fuel > 10 && jetpack_fuel <= 20) {
			jf_w = 3;
			jf_h = 3;
		}else if(jetpack_fuel > 0 && jetpack_fuel <= 10) {
			jf_w = 1;
			jf_h = 4;
		}else if(jetpack_fuel == 0) {
			jf_w = 2;
			jf_h = 4;
		}
		
		
		
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
		
		/* Floating mechanics needed for jetpack */
		if( is_floating == true ) {
			
			/* if the floating mechanic has been going for less than 3 seconds 
			 * set falling false, call the floating animation, and increment the sleep counter
			 */
			//if(floating_sleep_counter < 180 ) {
				//falling = false;
				//if( floating_sleep_counter == 0 ) velY = 0;
				//floating_animation();
				//floating_sleep_counter++;
			//}
			/* else the player is done floating, 
			 * reset variables and start a counter for the jetpack cooldown 
			*/
			//else {
				//falling = true;
				//is_floating = false;
				//floating_sleep_counter = 0;
				
			//}
		
			/* Using jet pack fuel and a 30 second timer instead for floating */
			if( jetpack_fuel > 0 ) {
				falling = false;
				if(firstCall == true) velY = 0;
				if(floating_sleep_counter%60 == 0) secCount++;
				if(secCount == 3) {
					jetpack_fuel -= 10;
					secCount = 0;
				}
				firstCall = false;
				floating_sleep_counter++;
			}
			else {
				falling = true;
				is_floating = false;
				floating_sleep_counter = 0;
				
			}
		}
	}
	
	
	/* Dependant on the current floating sleep counter number,
	 * Set the players y velocity to -1, 0, or 1.
	 * This has the effect of the player going up and down
	 */
	/*private void floating_animation() {
		if( floating_sleep_counter == 0 ) velY = 0;
		if( floating_sleep_counter == 3 ) velY = 1;
		if( floating_sleep_counter == 6 ) velY = -1;
		if( floating_sleep_counter == 9 ) velY = 0;
		if( floating_sleep_counter == 12 ) velY = 1;
		if( floating_sleep_counter == 15 ) velY = -1;
		if( floating_sleep_counter == 18 ) velY = 0;
		if( floating_sleep_counter == 21 ) velY = 1;
		if( floating_sleep_counter == 24 ) velY = -1;
		if( floating_sleep_counter == 27 ) velY = 0;
		if( floating_sleep_counter == 30 ) velY = 1;
		if( floating_sleep_counter == 33 ) velY = -1;
		if( floating_sleep_counter == 36 ) velY = 0;
		if( floating_sleep_counter == 39 ) velY = 1;
		if( floating_sleep_counter == 42 ) velY = -1;
		if( floating_sleep_counter == 45 ) velY = 0;
		if( floating_sleep_counter == 48 ) velY = 1;
		if( floating_sleep_counter == 51 ) velY = -1;
		if( floating_sleep_counter == 54 ) velY = 0;
		if( floating_sleep_counter == 57 ) velY = 1;
		if( floating_sleep_counter == 60 ) velY = -1;
		if( floating_sleep_counter == 63 ) velY = 0;
		if( floating_sleep_counter == 66 ) velY = 1;
		if( floating_sleep_counter == 69 ) velY = -1;
		if( floating_sleep_counter == 72 ) velY = 0;
		if( floating_sleep_counter == 75 ) velY = 1;
		if( floating_sleep_counter == 78 ) velY = -1;
		if( floating_sleep_counter == 81 ) velY = 0;
		if( floating_sleep_counter == 84 ) velY = 1;
		if( floating_sleep_counter == 87 ) velY = -1;
		if( floating_sleep_counter == 90 ) velY = 0;
		if( floating_sleep_counter == 93 ) velY = 1;
		if( floating_sleep_counter == 96 ) velY = -1;
		if( floating_sleep_counter == 99 ) velY = 0;
		if( floating_sleep_counter == 102 ) velY = 1;
		if( floating_sleep_counter == 105 ) velY = -1;
		if( floating_sleep_counter == 108 ) velY = 0;
		if( floating_sleep_counter == 111 ) velY = 1;
		if( floating_sleep_counter == 114 ) velY = -1;
		if( floating_sleep_counter == 117 ) velY = 0;
		if( floating_sleep_counter == 120 ) velY = 1;
		if( floating_sleep_counter == 123 ) velY = -1;
		if( floating_sleep_counter == 126 ) velY = 0;
		if( floating_sleep_counter == 129 ) velY = 1;
		if( floating_sleep_counter == 132 ) velY = -1;
		if( floating_sleep_counter == 135 ) velY = 0;
		if( floating_sleep_counter == 138 ) velY = 1;
		if( floating_sleep_counter == 141 ) velY = -1;
		if( floating_sleep_counter == 144 ) velY = 0;
		if( floating_sleep_counter == 147 ) velY = 1;
		if( floating_sleep_counter == 150 ) velY = -1;
		if( floating_sleep_counter == 153 ) velY = 0;
		if( floating_sleep_counter == 156 ) velY = 1;
		if( floating_sleep_counter == 159 ) velY = -1;
		if( floating_sleep_counter == 162 ) velY = 0;
		if( floating_sleep_counter == 165 ) velY = 1;
		if( floating_sleep_counter == 168 ) velY = -1;
		if( floating_sleep_counter == 171 ) velY = 0;
		if( floating_sleep_counter == 174 ) velY = 1;
		if( floating_sleep_counter == 177 ) velY = -1;
		if( floating_sleep_counter == 180 ) velY = 0;
	} */

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
					in_air = false;
					is_floating = false;
					falling = true;
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
	
	//public Rectangle getRightBounds( ) {
	//	return new Rectangle(x + width/2, y, width/2, 30);
	//}
	
	//public Rectangle getLeftBounds( ) {
	//	return new Rectangle(x - width/2, y, width/2, 30);
	//}

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
	
	public void setFloating(boolean floating) {
		this.is_floating = floating;
	}
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
}
