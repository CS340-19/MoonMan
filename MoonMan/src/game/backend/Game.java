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

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -6112428091888191314L;

	public static final int WIDTH = 1280, HEIGHT = 720;
	public BufferedImage background;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	
	
	
	
	
	public Game() {
		
		handler = new Handler();
		
		new Window(WIDTH, HEIGHT, "Moon Man", this);

		int x = -32;
		for(int i = 0; i <= 60; i++) {
			Handler.addObject(new Floor(x, HEIGHT - 64, 64, 64, ID.Floor));
			x += 32;
		}
		
		this.addKeyListener(new KeyInput(handler));
		
		Handler.addObject(new Player(WIDTH/2 - 32, HEIGHT/2 - 32, 64, 64, ID.Player));
		
		
		
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

	//Main game loop
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
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
	}
	
	private void render() {
		/*
		try {
			background = ImageIO.read(new File("C:\\Users\\ztop\\Desktop\\MoonManCharacters\\MoonBackground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//g.setColor(Color.black);
		//g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.black);
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) throws IOException {
		new Game();
	}
}
