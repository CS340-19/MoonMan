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

public class Menu {

	public static Rectangle play, options, quit;
	private static int centerX = Game.WIDTH /2;
	public static BufferedImage menuBackground = null;
	public static BufferedImage menuPlay = null;
	public static BufferedImage menuOptions = null;
	public static BufferedImage menuQuit = null;
	
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
	}
	
	public static void drawButton(Graphics g, Rectangle rect, String text, int offsetX) {
		//Font tempFont = new Font("Bauhaus 93", Font.BOLD, 48);
		//g.setFont(tempFont);
		//g.setColor(Color.white);
		//g.drawRect(rect.x, rect.y, rect.width, rect.height);
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
		
	}
	
	
	
}
