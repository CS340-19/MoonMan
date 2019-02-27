package game.object;
import java.awt.Color;
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

import game.backend.Game;
import game.backend.GameObject;
import game.backend.GameState;
import game.backend.Handler;
import game.graphics.BufferedImageLoader;
import game.graphics.SpriteSheetResolver;

public class Player extends GameObject {
	
	private static LinkedList<GameObject> floorBlocks = Handler.getObjects();

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
	public static boolean stay = false;
	private int x_save;
	private int y_save;
	private ID id_save;
	private int width_save;
	private int height_save;
	
	public Player(int x, int y, int tmp_width, int tmp_height, ID id) {
		super(x, y, id);
		X = x;
		x_save = x;
		y_save = y;
		id_save = id;
		width_save = tmp_width;
		height_save = tmp_height;
		width = tmp_width;
		height = tmp_height;
	
		BufferedImageLoader loader = new BufferedImageLoader();
		moonMan_ss = loader.loadImage("./src/game/graphics/sprites/MoonMan_SS.png");
		ss = new SpriteSheetResolver(moonMan_ss);
		
	}
	
	public void reset( ) {
		x = x_save;
		y = y_save;
		id = id_save;
		jumping = false;
		falling = true;
		gravity = 1;
		facing_right = true;
		walking = false;
		in_air = false;
		is_floating = false;
		is_flying = false;
		firstCall = false;
		secCount = 0;
		walk_sleep_counter = 0;
		which_step = 0;
		w_row = 1;
		w_col = 1;
		width = width_save;
		height = height_save;
		floating_sleep_counter = 0; /* Needed so that floating only lasts for a set amount of time */
		jetpack_fuel = 100;
		jf_w = 1;
		jf_h = 1;
		X = x;
		stay = false;
	}
	
	public void tick() {
		if (velX == 0) walking = false;
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
		g.setColor(Color.white);
		g.drawRect(x + 30, y - 10, 60, 87);
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
		for(int i = 0; i < Handler.getObjects().size(); i++) {
			if(Handler.getObjects().get(i).getID() == ID.Enemy) {
				squidman = (SquidMan) Handler.getObjects().get(i);
				if(getBottomBounds().intersects(squidman.getBottomBounds())) {
					Game.state = GameState.MENU;
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
		return new Rectangle(x + 30, y - 10, 60, 87);
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
