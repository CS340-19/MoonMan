package game.backend;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import game.graphics.BufferedImageLoader;
import game.graphics.JetPackFuelResolver;
import game.object.Floor;
import game.object.ID;
import game.object.Player;
import game.object.SquidMan;
import game.screens.Menu;
import game.screens.SplashScreen;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -6112428091888191314L;

	public static int WIDTH = 1920, HEIGHT = 1080;
	public BufferedImage background;
	public BufferedImage foreground;
	public BufferedImage mm_plate;
	public BufferedImage score_plate;
	public BufferedImage jetpack_plate;
	public BufferedImage jetpackfuel_plate = null;
	public BufferedImage jetpackfuel_full_ss = null;
	public JetPackFuelResolver jetpackfuel_ss = null;
	public int enemy_offset;
	private Thread thread;
	private SplashScreen splashscreen;
	private boolean running = false;
	private boolean imageLoaded = false;
	private Handler handler;
	public static GameState state = GameState.SPLASH_SCREEN;
	//public static GameState state = GameState.MENU;
	
	
	public Game() {
		
		handler = new Handler();
		
		new Window(WIDTH, HEIGHT, "Moon Man", this);
		
		Menu.initMenu();
		
		int x = -32;
		for(int i = 0; i <= 60; i++) {
			Handler.addObject(new Floor(x, HEIGHT - 64, WIDTH, 64, ID.Floor));
			x += 32;
		}
		
		this.addKeyListener(new KeyInput(handler));
		
		Handler.addObject(new Player(WIDTH/2 - 32, HEIGHT/2 - 32, 64, 64, ID.Player));
		Random random = new Random();
		enemy_offset = random.nextInt(1000) - random.nextInt(1000);
		Handler.addObject(new SquidMan(WIDTH/2 - 32 - enemy_offset, HEIGHT/2 - 32, 64, 64, ID.Enemy));
		
		
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
		foreground = loader.loadImage("./src/game/graphics/sprites/MoonForegroundHiRes.png");
		background = loader.loadImage("./src/game/graphics/sprites/MoonBackgroundHiRes.png");
		mm_plate = loader.loadImage("./src/game/graphics/sprites/MoonManPlate.png");
		score_plate = loader.loadImage("./src/game/graphics/sprites/Score Plate.png");
		jetpack_plate = loader.loadImage("./src/game/graphics/sprites/JetPackPlate.png");
		jetpackfuel_full_ss = loader.loadImage("./src/game/graphics/sprites/JetPackFuelBar_SS.png");
		jetpackfuel_ss = new JetPackFuelResolver(jetpackfuel_full_ss);
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
				System.out.println("FPS: " + frames);
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
		if( state == GameState.MENU) {
			Menu.tick();
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
				g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
				g.drawImage(foreground, 0, 0, WIDTH, HEIGHT, null);
				g.drawImage(mm_plate, 0, 0, 128, 128, null);
				g.drawImage(score_plate, 0, 0, 256, 256, null);
				g.drawImage(jetpack_plate, 0, 0, 256, 256, null);
				g.drawImage(jetpackfuel_plate, 0, 0, 256, 256, null);
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
				stop();
				break;
			default:
				break;
		}
		
		
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) throws IOException {
		new Game();
	}
}
