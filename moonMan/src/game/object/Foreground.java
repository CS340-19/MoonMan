package game.object;

import java.awt.Color;
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
	public static boolean Begining = true;
	public static boolean Ending = false;
	public static boolean LeftWall = false;
	public static boolean RightWall = false;
	private int x_save;
	private int y_save;
	private ID id_save;
	
	public Foreground(int x, int y, ID id) {
		super(x, y, id);
		Y = y;
		x_save = x;
		y_save = y;
		id_save = id;
	}

	public static void init() {
		BufferedImageLoader loader = new BufferedImageLoader();
		foreground1 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		foreground2 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		foreground3 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		foreground4 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		foreground5 = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
	}
	
	public void reset( ) {
		x = x_save;
		y = y_save;
		id = id_save;
		Right = Game.Right_MW;
		Left = Game.Left_MW;
		X = 0;
		Y = 0;
		totalWidth = Game.WIDTH*5;
		whereYourAt = 0;
		Begining = true;
		Ending = false;
		LeftWall = false;
		RightWall = false;
	}
	
	@Override
	public void tick() {
		//checkCollision();
	}

	@Override
	public void render(Graphics g, int row, int col) {	
		//g.setColor(Color.white);
		//g.drawRect((totalWidth)-whereYourAt, 0, 20, Game.HEIGHT);
	}
	
	public static void checkCollision() {
		for(int i = 0; i < Handler.getObjects().size(); i++) {
			if(Handler.getObjects().get(i).getID() == ID.Player) {
				Player player = (Player) Handler.getObjects().get(i);
				if(whereYourAt <= 0) {
					Begining = true;
					Ending = false;
				}else {
					Begining = false;
				}
				if(whereYourAt >= totalWidth-1520) {
					Ending = true;
					Begining = false;
				}else {
					Ending = false;
				}
				if(getLeftBounds().intersects(player.getBottomBounds())) {
					if((Player.X+whereYourAt > (int)(Game.WIDTH/10)) && Begining == false) {
						X += 7;
						whereYourAt -= 7;
						//System.out.println("whereYourAT: " + whereYourAt + "   total: " + Player.X+whereYourAt);
					}
					
				}else if(getRightBounds().intersects(player.getBottomBounds())) {
					if((Player.X+whereYourAt < totalWidth-(int)(Game.WIDTH/6.5)) && Ending == false) {
						X -= 7;
						whereYourAt += 7;
						//System.out.println("whereYourAT: " + whereYourAt + "   total: " + Player.X+whereYourAt);
					}
				}
			}
		}
	}
	
	public static Rectangle getBeginingBounds( ) {
		return new Rectangle(0, 0, 20, Game.HEIGHT);
	}
	
	public static Rectangle getEndingBounds( ) {
		return new Rectangle((totalWidth)-whereYourAt, 0, 20, Game.HEIGHT);
	}
	
	public static Rectangle getLeftBounds() {
		return new Rectangle(Game.centerX - (int) ((Game.WIDTH/1.3)/2)-32, 0, 10, Game.HEIGHT);
	}
	
	public static Rectangle getRightBounds() {
		return new Rectangle(Game.centerX + (int) ((Game.WIDTH/1.3)/2)-64, 0, 10, Game.HEIGHT);
	}

}
