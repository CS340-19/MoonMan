package game.object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.backend.Game;
import game.backend.GameObject;
import game.backend.Handler;
import game.graphics.BufferedImageLoader;

public class Foreground extends GameObject{

	public static BufferedImage foreground1 = null;
	public static BufferedImage foreground2 = null;
	public static BufferedImage foreground3 = null;
	public static BufferedImage foreground4 = null;
	public static BufferedImage foreground5 = null;
	public static int Right = Game.Right_MW;
	public static int Left = Game.Left_MW;
	public static int X = 0;
	public static int Y = 0;
	public static int totalWidth = Game.WIDTH*5;
	public static int whereYourAt = 0;
	
	public Foreground(int x, int y, ID id) {
		super(x, y, id);
	}

	public static void init() {
		BufferedImageLoader loader = new BufferedImageLoader();
		foreground1 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		foreground2 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		foreground3 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		foreground4 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		foreground5 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
	}
	
	@Override
	public void tick() {
		//checkCollision();
	}

	@Override
	public void render(Graphics g, int row, int col) {		
	}
	
	public static void checkCollision() {
		for(int i = 0; i < Handler.getObjects().size(); i++) {
			if(Handler.getObjects().get(i).getID() == ID.Player) {
				Player player = (Player) Handler.getObjects().get(i);
				if(getLeftBounds().intersects(player.getBottomBounds())) {
					if(Player.X+whereYourAt > (int)(Game.WIDTH/10)) {
						X += 7;
						whereYourAt -= 7;
						//System.out.println("whereYourAT: " + whereYourAt + "   total: " + Player.X+whereYourAt);
					}
					
				}else if(getRightBounds().intersects(player.getBottomBounds())) {
					if(Player.X+whereYourAt < totalWidth-(int)(Game.WIDTH/6.5)) {
						X -= 7;
						whereYourAt += 7;
						//System.out.println("whereYourAT: " + whereYourAt + "   total: " + Player.X+whereYourAt);
					}
				}
			}
		}
	}
	
	public static Rectangle getLeftBounds() {
		return new Rectangle(Game.centerX - (int) ((Game.WIDTH/1.3)/2)-32, 0, 2, Game.HEIGHT);
	}
	
	public static Rectangle getRightBounds() {
		return new Rectangle(Game.centerX + (int) ((Game.WIDTH/1.3)/2)-64, 0, 2, Game.HEIGHT);
	}

}
