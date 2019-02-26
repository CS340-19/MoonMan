package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.backend.Game;
import game.backend.GameObject;
import game.graphics.BufferedImageLoader;
import game.object.ID;

public class Pause extends GameObject {

	public static BufferedImage menuPlay = null;
	public static BufferedImage menuOptions = null;
	public static BufferedImage menuQuit = null;
	
	public Pause(int x, int y, ID id) {
		super(x, y, id);
		initPauseMenu();
		// TODO Auto-generated constructor stub
	}

	public static Rectangle play, options, quit;
	
	public static void initPauseMenu( ) {
		BufferedImageLoader loader = new BufferedImageLoader();
		menuPlay = loader.loadImage("./src/game/graphics/sprites/Play_menu.png");
		menuOptions = loader.loadImage("./src/game/graphics/sprites/Options_menu.png");
		menuQuit = loader.loadImage("./src/game/graphics/sprites/Quit_menu.png");
		play = new Rectangle(Game.centerX -235, Game.centerY -240, 500, 100);
		options = new Rectangle(Game.centerX - 380, Game.centerY - 80, 800, 100);
		quit = new Rectangle(Game.centerX -250, Game.centerY + 80, 500, 100);
	}
	
	public static void drawButton(Graphics g, Rectangle rect, String text, int offsetX) {
		//Font tempFont = new Font("Bauhaus 93", Font.BOLD, 48);
		//g.setFont(tempFont);
		g.setColor(Color.black);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);	
		//g.drawString(text, rect.x + offsetX, rect.y + 60);
	}

	@Override
	public void tick() {		
	}

	@Override
	public void render(Graphics g, int row, int col) {
		g.drawImage(menuPlay, Game.centerX - 235, Game.centerY - 240, 500, 100, null);
		g.drawImage(menuOptions, Game.centerX-380, Game.centerY - 80, 800, 100, null);
		g.drawImage(menuQuit, Game.centerX-250, Game.centerY + 80, 500, 100, null);
		drawButton(g, play, "Play", 165);
		drawButton(g, options, "Options", 170);
		drawButton(g, quit, "Quit", 165);
		
	}
}
