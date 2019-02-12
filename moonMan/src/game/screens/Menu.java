package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import game.backend.Game;
import game.graphics.BufferedImageLoader;
import game.graphics.SpriteSheetResolver;

public class Menu {

	public static Rectangle play, options, quit;
	private static int centerX = Game.WIDTH /2;
	public static BufferedImage menuBackground = null;
	public static BufferedImage menuPlay = null;
	public static BufferedImage menuOptions = null;
	public static BufferedImage menuQuit = null;
	public static BufferedImage player = null;
	public static BufferedImage player_ss = null;
	public static SpriteSheetResolver ss;
	public static int sleep_counter = 0;
	public static int which_step = 0;
	public static int w_row = 1;
	public static int w_col = 1;
	
	public static void initMenu() {
		BufferedImageLoader loader = new BufferedImageLoader();
		int y = 150;
		menuBackground = loader.loadImage("./src/game/graphics/sprites/MoonBackgroundHiRes_menu.png");
		menuPlay = loader.loadImage("./src/game/graphics/sprites/Play_menu.png");
		menuOptions = loader.loadImage("./src/game/graphics/sprites/Options_menu.png");
		menuQuit = loader.loadImage("./src/game/graphics/sprites/Quit_menu.png");
		play = new Rectangle(centerX -235, y + 150, 500, 100);
		options = new Rectangle(centerX - 380, y+=310, 800, 100);
		quit = new Rectangle(centerX -250, y+=160, 500, 100);
		player_ss = loader.loadImage("./src/game/graphics/sprites/MoonManMenu_SS.png");
		ss = new SpriteSheetResolver(player_ss);
	}
	
	public static void drawButton(Graphics g, Rectangle rect, String text, int offsetX) {
		//Font tempFont = new Font("Bauhaus 93", Font.BOLD, 48);
		//g.setFont(tempFont);
		//g.setColor(Color.white);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		//g.drawString(text, rect.x + offsetX, rect.y + 60);
	}
	
	public static void render(Graphics g) {
		//g.setColor(Color.DARK_GRAY);
		//g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.drawImage(menuBackground, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g.drawImage(menuPlay, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g.drawImage(menuOptions, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g.drawImage(menuQuit, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		drawButton(g, play, "Play", 165);
		drawButton(g, options, "Options", 170);
		drawButton(g, quit, "Quit", 165);
		player = ss.grabImage(w_row, w_col, 64, 64);
		g.drawImage(player,  Game.WIDTH-480, Game.HEIGHT-280, 256, 256, null);
		
	}
	
	public static void tick() {
		if(sleep_counter == 0) {
			if(which_step >= 0 && which_step <= 30) {
				w_row = 1;
				w_col = 1;
			} else if(which_step == 31) {
				w_row = 2;
				w_col = 1;				
			} else if(which_step == 32) {
				w_row = 3;
				w_col = 1;
			} else if(which_step == 33) {
				w_row = 4;
				w_col = 1;
			} else if(which_step == 34) {
				w_row = 5;
				w_col = 1;
			} else if(which_step == 35) {
				w_row = 6;
				w_col = 1;
			} else if(which_step == 36) {
				w_row = 7;
				w_col = 1;
			} else if(which_step == 37) {
				w_row = 1;
				w_col = 2;
			} else if(which_step == 38) {
				w_row = 2;
				w_col = 2;
			} else if(which_step == 39) {
				w_row = 3;
				w_col = 2;
			} else if(which_step == 40) {
				w_row = 4;
				w_col = 2;
			} else if(which_step == 41) {
				w_row = 5;
				w_col = 2;
			} else if(which_step == 42) {
				w_row = 6;
				w_col = 2;
			} else if(which_step == 43) {
				w_row = 7;
				w_col = 2;
			} else if(which_step == 44) {
				w_row = 1;
				w_col = 3;
			} else if(which_step == 45) {
				w_row = 2;
				w_col = 3;
			} else if(which_step >= 46 && which_step <= 76) {
				w_row = 1;
				w_col = 1;
			} else if(which_step == 77) {
				w_row = 4;
				w_col = 3;
			} else if(which_step == 78) {
				w_row = 5;
				w_col = 3;
			} else if(which_step == 79) {
				w_row = 6;
				w_col = 3;
			} else if(which_step == 80) {
				w_row = 7;
				w_col = 3;
			} else if(which_step == 81) {
				w_row = 1;
				w_col = 4;
			} else if(which_step == 82) {
				w_row = 2;
				w_col = 4;				
			} else if(which_step == 83) {
				w_row = 3;
				w_col = 4;
			} else if(which_step == 84) {
				w_row = 4;
				w_col = 4;
			} else if(which_step == 85) {
				w_row = 5;
				w_col = 4;
			} else if(which_step == 86) {
				w_row = 6;
				w_col = 4;
			} else if(which_step == 87) {
				w_row = 7;
				w_col = 4;
			} else if(which_step == 88) {
				w_row = 1;
				w_col = 5;
			} else if(which_step == 89) {
				w_row = 2;
				w_col = 5;
			} else if(which_step == 90) {
				w_row = 3;
				w_col = 5;
			} else if(which_step >= 91 && which_step <= 120) {
				w_row = 1;
				w_col = 1;
			} else if(which_step == 121) {
				w_row = 5;
				w_col = 5;
			} else if(which_step == 122) {
				w_row = 6;
				w_col = 5;
			} else if(which_step == 123) {
				w_row = 7;
				w_col = 5;
			} else if(which_step == 124) {
				w_row = 1;
				w_col = 6;
			} else if(which_step == 125) {
				w_row = 2;
				w_col = 6;
			} else if(which_step == 126) {
				w_row = 3;
				w_col = 6;
			} else if(which_step == 127) {
				w_row = 4;
				w_col = 6;
			} else if(which_step == 128) {
				w_row = 5;
				w_col = 6;
			} else if(which_step == 129) {
				w_row = 7;
				w_col = 6;
			} else if(which_step == 130) {
				w_row = 1;
				w_col = 7;
			} else if(which_step == 131) {
				w_row = 2;
				w_col = 7;
			} else if(which_step == 132) {
				w_row = 3;
				w_col = 7;
			} else if(which_step == 133) {
				w_row = 4;
				w_col = 7;
			} else if(which_step == 134) {
				w_row = 5;
				w_col = 7;
			} else if(which_step == 135) {
				w_row = 6;
				w_col = 7;
			} else if(which_step == 136) {
				w_row = 7;
				w_col = 7;
			} else if(which_step == 137) {
				w_row = 1;
				w_col = 8;
				which_step = -1;
			} 
			which_step++;
		}
		sleep_counter++;
		if(sleep_counter == 12) {
			sleep_counter = 0;
		}
	}
	
	
	
}
