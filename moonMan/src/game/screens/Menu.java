package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.backend.Game;
import game.graphics.BufferedImageLoader;
import game.graphics.SpriteSheetResolver;

public class Menu {

	public static Rectangle play, options, quit, changeLeft, changeRight, name;
	//private static int centerX = Game.WIDTH /2;
	//private static int centerY = Game.HEIGHT /2;
	public static BufferedImage menuBackground = null;
	public static BufferedImage menuRock = null;
	public static BufferedImage menuPlay = null;
	public static BufferedImage menuOptions = null;
	public static BufferedImage menuQuit = null;
	public static BufferedImage player = null;
	public static BufferedImage player_ss = null;
	public static BufferedImage menuChangePlayer = null;
	public static SpriteSheetResolver ss;
	public static int sleep_counter = 0;
	public static int which_step = 0;
	public static int w_row = 1;
	public static int w_col = 1;
	public static int y = 150;
	public static String[] text = new String[100];
	public static int[] textX = new int[100];
	public static int[] textY = new int[100];
	public static int[] textSize = new int[100];
	public static int num = 0;
	public static int numSize = 1;
	
	public static void initMenu() {
		BufferedImageLoader loader = new BufferedImageLoader();
		
		menuBackground = loader.loadImage("./src/game/graphics/sprites/MoonBackgroundHiRes_menu.png");
		menuRock = loader.loadImage("./src/game/graphics/sprites/Rock_menu.png");
		menuPlay = loader.loadImage("./src/game/graphics/sprites/Play_menu.png");
		menuOptions = loader.loadImage("./src/game/graphics/sprites/Options_menu.png");
		menuQuit = loader.loadImage("./src/game/graphics/sprites/Quit_menu.png");
		menuChangePlayer = loader.loadImage("./src/game/graphics/sprites/Change_menu.png");
		play = new Rectangle(Game.centerX -235, Game.centerY -240, 500, 100);
		options = new Rectangle(Game.centerX - 380, Game.centerY - 80, 800, 100);
		quit = new Rectangle(Game.centerX -250, Game.centerY + 80, 500, 100);
		changeLeft = new Rectangle(Game.centerX - 380, Game.centerY + 300, 110, 100);
		changeRight = new Rectangle(Game.centerX + 310, Game.centerY + 300, 110, 100);
		name = new Rectangle(Game.centerX - 380, Game.centerY + 300, 800, 100);
		player_ss = loader.loadImage("./src/game/graphics/sprites/MoonManMenu_SS.png");
		ss = new SpriteSheetResolver(player_ss);
		
		//set array
		text[0] = "MoonMan";
		textX[0] = 120;
		textY[0] = 90;
		textSize[0] = 100;
		text[1] = "Elon Musk";
		textX[1] = 120;
		textY[1] = 90;
		textSize[1] = 100;
	}
	
	public static void drawButton(Graphics g, Rectangle rect, String text, int offsetX, int offsetY, float size) {

		     //Returned font is of pt size 1
		     Font font;
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("./src/game/graphics/sprites/nasalization-rg.ttf"));
				Font tempFont = font.deriveFont(size);
				g.setFont(tempFont);
				g.setColor(Color.white);
				g.drawString(text,rect.x + offsetX, rect.y + offsetY);

			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		//g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	public static void render(Graphics g) {
		//g.setColor(Color.DARK_GRAY);
		//g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.drawImage(menuBackground, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g.drawImage(menuRock, Game.WIDTH-(250*3), Game.HEIGHT-(60*3), 250*3, 60*3, null);
		//g.drawImage(menuPlay, Game.centerX - 235, Game.centerY - 240, 500, 100, null);
		//g.drawImage(menuOptions, Game.centerX-380, Game.centerY - 80, 800, 100, null);
		//g.drawImage(menuQuit, Game.centerX-250, Game.centerY + 80, 500, 100, null);
		g.drawImage(menuChangePlayer, Game.centerX-380, Game.centerY + 300, 800, 100, null);
		
		drawButton(g, play, "Play", 105, 100, 140);
		drawButton(g, options, "Options", 100, 100, 140);
		drawButton(g, quit, "Quit", 100, 100, 140);
		drawButton(g, changeLeft, "", 100, 0, 120);
		drawButton(g, changeRight, "", 165, 0, 120);
		
		//set what player
		drawButton(g, name, text[num], textX[num], textY[num], textSize[num]);
		
		
	
		player = ss.grabImage(w_row, w_col, 64, 64);
		//g.drawImage(player,  Game.WIDTH-480, Game.HEIGHT-280, 256, 256, null);
		g.drawImage(player, Game.WIDTH-(250*3/2), Game.HEIGHT-(60*5), 256, 256, null);
		
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
