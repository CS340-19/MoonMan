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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.object.Foreground;

import game.backend.Game;
import game.backend.GameObject;
import game.backend.GameState;
import game.backend.Handler;
import game.backend.KeyInput;
import game.graphics.BufferedImageLoader;
import game.graphics.SpriteSheetResolver;
import game.object.Foreground;

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
	public static Boolean walking = false;
	public Boolean in_air = false;
	public static Boolean facing_right = false;
	public Boolean is_floating = false;
	public Boolean is_flying = false;
	public Boolean firstCall = false;
	public Boolean pressingA = false;
	public Boolean pressingD = false;
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
		Game.counter = 0;
		
		Game.spawnflag[0] = 0;
		Game.spawnflag[1] = 0;
		Game.spawnflag[2] = 0;
		Game.spawnflag[3] = 0;
		Game.spawnflag[4] = 0;
	}
	
	public void tick() {
		
		if((x >= (Game.centerX + (int) ((Game.WIDTH/1.7)/2))) && Foreground.Ending == false && velX > 0) {	
			y += velY;
		}else if((x <= (Game.centerX - (int) ((Game.WIDTH/1.15)/2))) && Foreground.Begining == false && velX < 0) {
			y += velY;
		}else if((x <= (Game.centerX - (int) ((Game.WIDTH/.96)/2))) && velX < 0) {
			y += velY;
		}else if((x >= (Game.centerX + (int) ((Game.WIDTH/1.2)/2))) && velX > 0) {
			Game.state = GameState.MENU;
			try {
				Game.Music.stop();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Game.game_background = 0;
			Game.loaded = 0;
			Game.dificulty++;
		}else {
			x += velX;
			X = x;
			y += velY;
		}
		
		fall();
		try {
			checkCollision();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (walking == true) {
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
		
		
		
		
		if (jumping == true) {
			in_air = true;
			walking = false;
		}else {
			in_air = false;
			if (velX == 0 && !((Foreground.getLeftBounds().intersects(getBottomBounds())) && Foreground.Begining == false) 
					  && !((Foreground.getBeginingBounds().intersects(getBottomBounds())) && Foreground.Begining == true)
					  && !((Foreground.getRightBounds().intersects(getBottomBounds())) && Foreground.Ending == false) 
					  && !((Foreground.getEndingBounds().intersects(getBottomBounds())) && Foreground.Ending == true)) {
			walking = false;
			}
			else {
				if(pressingA || pressingD) {
					walking = true;	
				}
			}
		}
		if (!pressingA && !pressingD) {
			walking = false;
		}
		
		/* Floating mechanics needed for jetpack */
		if(is_floating == true) {
			
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
			if (jetpack_fuel > 0) {
				falling = false;
				if(firstCall == true) velY = 0;
				if(floating_sleep_counter%30 == 0) secCount++;
				if(secCount == 1) {
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
		/* else start recharging the jet pack */
		else {
			
			if (jetpack_fuel < 100) {
				if (floating_sleep_counter%30 == 0) secCount++;
				if ( secCount == 1 ) {
					jetpack_fuel += 10;
					secCount = 0;
				}
				floating_sleep_counter++;
			}
			
		}
	}

	public void render(Graphics g, int row, int col ) {
		
		if (in_air == true) {
			row = 4;
			col = 4;
			walking = false;
		}
		if (walking == true) {
			row = w_row;
			col = w_col;
		}

		moonMan = ss.grabImage(row, col, 64, 64);
		
		if (facing_right == false) {
			// Flip the image horizontally
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-moonMan.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			moonMan = op.filter(moonMan, null);	
		}	
		g.drawImage(moonMan,  x, y -20, 128, 128, null);
	}
	
	private void checkCollision() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		for (GameObject obj : floorBlocks) {
			if (obj.getID() == ID.Floor) {
				floor = (Floor) obj;
				if (getBottomBounds().intersects(floor.getTopBounds())) {
					velY = 0;
					y = floor.getY() - height;
					setJumping(false);
					in_air = false;
					is_floating = false;
					falling = true;
					KeyInput.jumped = false;
				}
			}
		}
		for (int i = 0; i < Handler.getObjects().size(); i++) {
			if (Handler.getObjects().get(i).getID() == ID.Squidman) {
				squidman = (SquidMan) Handler.getObjects().get(i);
				if (getBottomBounds().intersects(squidman.getBottomBounds())) {
					Game.state = GameState.MENU;
					Game.Music.stop();
					Game.game_background = 0;
					Game.loaded = 0;
					
					for (int i1 = 0; i1 < Handler.getObjects().size(); i1++) {
						if (Handler.getObjects().get(i1).getID() == ID.Squidman) {
							Handler.removeObject(squidman);
							Game.enemiesRemaining--;
						}
					}
					Game.dificulty = 0;
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
