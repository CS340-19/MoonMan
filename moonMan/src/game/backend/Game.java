package game.backend;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.graphics.BufferedImageLoader;
import game.graphics.JetPackFuelResolver;
import game.object.Floor;
import game.object.Foreground;
import game.object.ID;
import game.object.Laser;
import game.object.Player;
import game.object.SquidMan;
import game.screens.Menu;
import game.screens.SplashScreen;
import game.sounds.audioPlayer;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -6112428091888191314L;
	public static int WIDTH = 1920, HEIGHT = 1080;
	public static int centerX, centerY;
	public BufferedImage background;
	public BufferedImage foreground;
	public BufferedImage ship;
	public static int counter = 0;
	public BufferedImage mm_plate;
	public BufferedImage score_plate;
	public BufferedImage jetpack_plate;
	public BufferedImage jetpackfuel_plate = null;
	public BufferedImage jetpackfuel_full_ss = null;
	public JetPackFuelResolver jetpackfuel_ss = null;
	public int enemy_offset;
	public int offsetX = 0;
	public int tickCount = 0;
	public int maxTick = 500;
	public int minTick = 300;
	public static int game_background = 0;
	public static int loaded = 0;
	private Thread thread;
	private SplashScreen splashscreen;
	private boolean running = false;
	private boolean imageLoaded = false;
	private boolean firstWave = true;
	private boolean secondWave = false;
	private Handler handler;
	public static GameState state = GameState.SPLASH_SCREEN;
	public static int Right_MW = centerX + (int) ((WIDTH/1.5)/2);
	public static int Left_MW = centerX - (int) ((WIDTH/1.5)/2);
	public static boolean Pause = false;
	public static boolean Option = false;
	public static int Score = 0;
	public static int enemiesRemaining = 10;
	public static int score = 0;
	public static Rectangle scoreRect;
	public static int spawnflag[];
	public static audioPlayer Music;
	public static int dificulty = 0;

	//public static GameState state = GameState.MENU;
	
	
	public Game() {
		
		handler = new Handler();
		
		new Window(WIDTH, HEIGHT, "Moon Man", this);
		
		Menu.initMenu();
		
		int x = -32;
		for(int i = 0; i <= 180; i++) {
			Handler.addObject(new Floor(x, HEIGHT - 64, 40, 64, ID.Floor));
			x += 32;
		}
		
		this.addKeyListener(new KeyInput(handler));
		Handler.addObject(new Foreground(0, 0, ID.ForeGround));
		Handler.addObject(new Player(150, HEIGHT-100, 64, 64, ID.Player));
		Random random = new Random();
		enemy_offset = random.nextInt(WIDTH/2);
		//Handler.addObject(new SquidMan(centerX - 32 + enemy_offset, HEIGHT - 100, 64, 64, ID.Enemy));
		//for(int i = 0; i < enemiesRemaining; i++) {
		//	Handler.addObject(new SquidMan(1800, HEIGHT/2 - 32, 64, 64, ID.Squidman));
		//}
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
	
	//Starts thread for game
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		BufferedImageLoader loader = new BufferedImageLoader();
		background = loader.loadImage("./src/game/graphics/sprites/MoonBackgroundHiRes.png");
		ship = loader.loadImage("./src/game/graphics/sprites/Ship.png");
		Foreground.init();
		mm_plate = loader.loadImage("./src/game/graphics/sprites/MoonManPlate.png");
		score_plate = loader.loadImage("./src/game/graphics/sprites/Score Plate.png");
		jetpack_plate = loader.loadImage("./src/game/graphics/sprites/JetPackPlate.png");
		jetpackfuel_full_ss = loader.loadImage("./src/game/graphics/sprites/JetPackFuelBar_SS.png");
		jetpackfuel_ss = new JetPackFuelResolver(jetpackfuel_full_ss);
		scoreRect = new Rectangle(Game.centerX - (int) (Game.WIDTH/2.42), Game.centerY - (int) (Game.HEIGHT/2.35), 20, 20);
		spawnflag = new int[5];
		spawnflag[0] = 0;
		spawnflag[1] = 0;
		spawnflag[2] = 0;
		spawnflag[3] = 0;
		spawnflag[4] = 0;
	}
	
	//Main game loop
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		init();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running) {
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
				
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
		if(SplashScreen.done == false) {
			SplashScreen.tick();
		}
		if(state == GameState.MENU) {
			Menu.tick();
		}
		if(state == GameState.GAME) {
			if( Foreground.whereYourAt >= 10 && Foreground.whereYourAt <= 60 ) {
				if(spawnflag[0] == 0) {
					Handler.addObject(new SquidMan(1500 + (counter*80), HEIGHT - 100 , 64, 64, ID.Squidman));
					counter++;
					if(counter == 5) {
						spawnflag[0] = 1;
						counter = 0;
					}
				}
			}
			if( Foreground.whereYourAt >= 810 && Foreground.whereYourAt <= 860 ) {
				if(spawnflag[1] == 0) {	
					Handler.addObject(new SquidMan(2300 + (counter*80), HEIGHT - 100 , 64, 64, ID.Squidman));
					counter++;
					if(counter == 6) {
						spawnflag[1] = 1;
						counter = 0;
					}
				}
			}
			if( Foreground.whereYourAt >= 1610 && Foreground.whereYourAt <= 1660 ) {
				if(spawnflag[2] == 0) {	
					Handler.addObject(new SquidMan(3100 + (counter*80), HEIGHT - 100 , 64, 64, ID.Squidman));
					counter++;
					if(counter == 8) {
						spawnflag[2] = 1;
						counter = 0;
					}
				}
			}
			if( Foreground.whereYourAt >= 2410 && Foreground.whereYourAt <= 2460 ) {
				if(spawnflag[3] == 0) {
					Handler.addObject(new SquidMan(3900 + (counter*80), HEIGHT - 100 , 64, 64, ID.Squidman));
					counter++;
					if(counter == 10) {
						spawnflag[3] = 1;
						counter = 0;
					}
				}
			}
			if( Foreground.whereYourAt >= 3210 && Foreground.whereYourAt <= 3260 ) {
				if(spawnflag[4] == 0) {
					Handler.addObject(new SquidMan(4700 + (counter*80), HEIGHT - 100 , 64, 64, ID.Squidman));
					counter++;
					if(counter == 12) {
						spawnflag[4] = 1;
						counter = 0;
					}
				}
			}
			
//			if(Laser.enemyKilled) {
//				for(int i = 0; i < enemiesRemaining; i++) {
//					Random random = new Random();
//					enemy_offset = random.nextInt(WIDTH/2);
//					Handler.addObject(new SquidMan(1000 + enemy_offset, HEIGHT - 100, 64, 64, ID.Enemy));
//				}
//			}
			
			/*
			if( tickCount >= minTick && tickCount < maxTick && tickCount % 20 == 0 ) {
				Random random = new Random();
				enemy_offset = random.nextInt(WIDTH/2);
				enemy_offset += 700;
				//offsetX = random.nextInt(3000);
				//offsetX += 1000;
				
				///*
				if( Player.X < (WIDTH/3) ) Handler.addObject(new SquidMan( Player.X + enemy_offset, HEIGHT - 3000, 64, 64, ID.Squidman));
				else if ( Player.X > ( (2 * WIDTH)/ 3 ) ) Handler.addObject(new SquidMan( Player.X - enemy_offset, HEIGHT - 3000, 64, 64, ID.Squidman));
				else {
					if( random.nextInt() >= 0 ) {
						Handler.addObject(new SquidMan( Player.X + enemy_offset, HEIGHT - 3000, 64, 64, ID.Squidman));
					}
					else {
						Handler.addObject(new SquidMan( Player.X - enemy_offset, HEIGHT - 3000, 64, 64, ID.Squidman));
					}
				}
			}
			*/
			if( tickCount >= maxTick ) {
				tickCount = 0;
				maxTick *= 2;
				minTick *= 2;
			}
			
			for(int j = 0; j < Handler.getObjects().size(); j++) {
				if(Handler.getObjects().get(j).getID() == ID.Laser) {
					Laser laser = (Laser) Handler.getObjects().get(j);
					if(laser.remove_me == 1) {
						Handler.removeObject(laser);
					}
				}
			}
		}
		jetpackfuel_plate = jetpackfuel_ss.grabImage(Player.jf_w, Player.jf_h, 256, 256);
	}
	
	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		switch(state) {
			case SPLASH_SCREEN:
				if(SplashScreen.done) {
					state = GameState.MENU;
				}else {
					SplashScreen.render(g);
				}
				break;
			case GAME:
				if(game_background == 1) {
					loaded = 1;
				try {
					game.sounds.audioPlayer.main("./src/game/sounds/wav_files/Main_background_music.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 game_background = 0;
				}
				g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
				//g.setColor(Color.white);
				//g.drawRect(centerX - (int) ((WIDTH/1.3)/2), 0, (int) (WIDTH/1.3), HEIGHT);
				g.drawImage(Foreground.foreground1, Foreground.X, Foreground.Y-47, WIDTH, HEIGHT, null);
				g.drawImage(Foreground.foreground2, Foreground.X+WIDTH, Foreground.Y-47, WIDTH, HEIGHT, null);
				g.drawImage(Foreground.foreground3, Foreground.X+(WIDTH*2), Foreground.Y-47, WIDTH, HEIGHT, null);
				g.drawImage(Foreground.foreground4, Foreground.X+(WIDTH*3), Foreground.Y-47, WIDTH, HEIGHT, null);
				g.drawImage(Foreground.foreground5, Foreground.X+(WIDTH*4), Foreground.Y-47, WIDTH, HEIGHT, null);
				g.drawImage(ship, centerX-(WIDTH/2)+Foreground.X, HEIGHT-576, 256, 512, null);
				g.drawImage(mm_plate, 0, 0, 128, 128, null);
				g.drawImage(score_plate, 0, 0, 256, 256, null);
				g.drawImage(jetpack_plate, 0, 0, 256, 256, null);
				g.drawImage(jetpackfuel_plate, 0, 0, 256, 256, null);
				drawButton(g, scoreRect, String.valueOf(score), 0, 0, 30);
				handler.render(g, 1, 1);
				break;
			case MENU:
				Menu.render(g);
				this.addMouseListener(new MouseInput());
				break;
			case OPTIONS:
				break;
			case PAUSE:
				break;
			case QUIT:
				//stop();
				System.exit(0);
				break;
			default:
				break;
		}
		
		
		
		g.dispose();
		bs.show();
		tickCount++;
	}
	
	public static void main(String args[]) throws IOException {
		new Game();
	}
}
