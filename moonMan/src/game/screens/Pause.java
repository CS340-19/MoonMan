package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.backend.Game;
import game.backend.GameObject;
import game.graphics.BufferedImageLoader;
import game.object.ID;

public class Pause extends GameObject {
	public Pause(int x, int y, ID id) {
		super(x, y, id);
		initPauseMenu();
		// TODO Auto-generated constructor stub
	}

	public static Rectangle play, options, quit;
	
	public static void initPauseMenu( ) {
		play = new Rectangle(Game.centerX -235, Game.centerY -240, 500, 100);
		options = new Rectangle(Game.centerX - 380, Game.centerY - 80, 800, 100);
		quit = new Rectangle(Game.centerX -250, Game.centerY + 80, 500, 100);
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

	@Override
	public void tick() {		
	}

	@Override
	public void render(Graphics g, int row, int col) {
		drawButton(g, play, "Play", 105, 100, 140);
		drawButton(g, options, "Options", 100, 100, 140);
		drawButton(g, quit, "Quit", 100, 100, 140);	
	}
}
