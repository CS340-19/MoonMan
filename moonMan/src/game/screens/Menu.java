package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.backend.Game;
import game.graphics.BufferedImageLoader;

public class Menu {

	private static Rectangle play, options, quit;
	private static int centerX = Game.WIDTH /2;
	//public static BufferedImage menuBackground = null;
	
	
	public static void initMenu() {
		//BufferedImageLoader loader = new BufferedImageLoader();
		int y = 150;
		//menuBackground = loader.loadImage("./src/game/graphics/sprites/MoonBackground.png");
		play = new Rectangle(centerX -250, y, 500, 100);
		options = new Rectangle(centerX -250, y+=150, 500, 100);
		quit = new Rectangle(centerX -250, y+=150, 500, 100);
	}
	
	public static void drawButton(Graphics g, Rectangle rect, String text, int offsetX) {
		Font tempFont = new Font("Arial", Font.BOLD, 48);
		g.setFont(tempFont);
		g.setColor(Color.white);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.drawString(text, rect.x + offsetX, rect.y + 60);
	}
	
	public static void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		drawButton(g, play, "Play", 165);
		drawButton(g, options, "Options", 170);
		drawButton(g, quit, "Quit", 165);
		//g.drawImage(menuBackground, 0, 0, Game.WIDTH, Game.HEIGHT, null);
	}
	
	
	
}
