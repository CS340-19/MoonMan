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
import game.backend.Handler;
import game.graphics.BufferedImageLoader;
import game.graphics.SpriteSheetResolver;

public class SquidMan extends GameObject {
	
	private static LinkedList<GameObject> floorBlocks = Handler.getObjects();
	
	public BufferedImage squidMan_ss = null;
	public BufferedImage squidMan = null;
	public SpriteSheetResolver ss;
	private Floor floor;
	Random r = new Random();
	private static Boolean jumping = false;
	private static Boolean falling = true;
	public static int gravity = 1;
	public static Boolean facing_right = true;
	public static Boolean walking = false;
	public static Boolean in_air = true;
	public static Boolean jumped = false;
	public static Boolean justSpawned = true;
	public static int walk_sleep_counter = 0;
	public static int which_step = 0;
	public static int w_row = 1;
	public static int w_col = 1;
	public static int player_x = 0;
	public static int player_y = 0;
	public static int squidman_x = 0;
	public static int squidman_y = 0;
	public static int width;
	public static int height;
	public static int jumpTimer = 60;
	private static int x_save;
	private static int y_save;
	private static int width_save;
	private static int height_save;
	
	public SquidMan(int x, int y, int tmp_width, int tmp_height, ID id) {
		super(x, y, id);
		x_save = x;
		y_save = y;
		width_save = tmp_width;
		height_save = tmp_height;
		width = tmp_width;
		height = tmp_height;
	
		BufferedImageLoader loader = new BufferedImageLoader();
		squidMan_ss = loader.loadImage("./src/game/graphics/sprites/SquidMan_SS.png");
		ss = new SpriteSheetResolver(squidMan_ss);
		
	}
	
	public void reset() {
		x = x_save;
		y = y_save;
		jumping = false;
		falling = true;
		gravity = 1;
		facing_right = true;
		walking = false;
		in_air = false;
		jumped = false;
		walk_sleep_counter = 0;
		which_step = 0;
		w_row = 1;
		w_col = 1;
		player_x = 0;
		player_y = 0;
		squidman_x = 0;
		squidman_y = 0;
		width = width_save;
		height = height_save;
		jumpTimer = 60;
	}
	
	public void tick() {
			
			x += velX;
			y += velY;
			
			fall();
			checkCollision();
			
			Player player = null;
			for(int i = 0; i < Handler.getObjects().size(); i++) {
				if(Handler.getObjects().get(i).getID() == ID.Player) {
					player = (Player) Handler.getObjects().get(i);
				}
			}
			if(player.getID() == ID.Player) {
				player_x = player.getX();
				player_y = player.getY();
			}
			
			SquidMan squidman = null;
			for(int i = 0; i < Handler.getObjects().size(); i++) {
				if(Handler.getObjects().get(i).getID() == ID.Enemy) {
					squidman = (SquidMan) Handler.getObjects().get(i);
				}
			}
			if(squidman.getID() == ID.Enemy) {
				squidman_x = squidman.getX();
				squidman_y = squidman.getY();
			}
			
			if( squidman_x == player_x ) {
				squidman.setVelX(0);
				squidman.setWalking(false);
			}
			else if(squidman_x > player_x && abs( squidman_x - player_x) > 3) {
				if(player.stay) {
					squidman.setVelX(1);
				}else {
					squidman.setVelX(-3);
				}
				squidman.setFacing_right(false);
				squidman.setWalking(true);
			}else if(squidman_x < player_x && abs( squidman_x - player_x) > 3) {
				if(player.stay) {
					squidman.setVelX(-1);
				}else {
					squidman.setVelX(3);
				}
				squidman.setFacing_right(true);
				squidman.setWalking(true);
			}else {
				squidman.setVelX(0);
				squidman.setWalking(false);
			}
			/*
			if( squidman_y > player_y && abs( squidman_x - player_x ) <= 200 && jumped == false ) {
				if( jumpTimer >= 90 ) {
					jumping = true;
					jumped = true;
					squidman.setVelY(-25);
					jumpTimer = 0;
				}
			}
			else {
				jumping = false;
			}
			jumpTimer++;
			
			if(jumping == true) {
				in_air = true;
				//walking = false;
			}else {
				in_air = false;
			}
			*/
	
			if(walking == true) {
				if(walk_sleep_counter == 0) {
					if(which_step == 0) {
						w_row = 1;
						w_col = 1;
					} else if(which_step == 1) {
						w_row = 2;
						w_col = 1;				
					} else if(which_step == 2) {
						w_row = 3;
						w_col = 1;
					} else if(which_step == 3) {
						w_row = 4;
						w_col = 1;
						which_step = 1;
					} else {
						w_row = 1;
						w_col = 1;
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



	private int abs(int i) {
		// TODO Auto-generated method stub
		if( i >= 0 ) return i;
		else return i *= -1;
	}

	public void render(Graphics g, int row, int col ) {
		
		if(in_air == true) {
			row = 4;
			col = 4;
			//walking = false;
		}
		if(walking == true) {
			row = w_row;
			col = w_col;
		}

		
		squidMan = ss.grabImage(row, col, 64, 64);
		
		if(facing_right == false) {
			// Flip the image horizontally
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-squidMan.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			squidMan = op.filter(squidMan, null);	
		}	
		g.drawImage(squidMan,  x, y - 25, 128, 128, null);
	}
	
	private void checkCollision() {
		for(GameObject obj : floorBlocks) {
			if(obj.getID() == ID.Floor) {
				floor = (Floor) obj;
				if(getBottomBounds().intersects(floor.getTopBounds())) {
					velY = 0;
					y = floor.getY() - height;
					setJumping(false);
					jumped = false;
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
		return new Rectangle(x, y - 10, 70, 87);
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
