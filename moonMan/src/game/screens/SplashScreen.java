package game.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import game.backend.Game;
import game.graphics.BufferedImageLoader;
import game.graphics.SateliteGamingResolver;

public class SplashScreen extends JFrame{
	private static final long serialVersionUID = -7209047797000850627L;
	public static BufferedImage SG_ss = null;
	public static BufferedImage pic = null;
	public static SateliteGamingResolver ss;
	public static Boolean running = true;
	public static int which_step = 0;
	public static int w_row;
	public static int w_col;
	public static int sleep_counter = 0;
	public static Boolean done = false;
	
	public SplashScreen() {
		
	}
	
	public static void tick() {
		if(sleep_counter == 0) {
			if(which_step == 0) {
				w_row = 1;
				w_col = 1;
			} else if(which_step == 1) {
				w_row = 2;
				w_col = 1;				
			} else if(which_step == 2) {
				w_row = 3;
				w_col = 1;
			} else if(which_step == 3) {
				w_row = 4;
				w_col = 1;
			} else if(which_step == 4) {
				w_row = 5;
				w_col = 1;
			} else if(which_step == 5) {
				w_row = 1;
				w_col = 2;
			} else if(which_step == 6) {
				w_row = 2;
				w_col = 2;
			} else if(which_step == 7) {
				w_row = 3;
				w_col = 2;
			} else if(which_step == 8) {
				w_row = 4;
				w_col = 2;
			} else if(which_step == 9) {
				w_row = 5;
				w_col = 2;
			} else if(which_step == 10) {
				w_row = 1;
				w_col = 3;
			} else if(which_step == 11) {
				w_row = 2;
				w_col = 3;
			} else if(which_step == 12) {
				w_row = 3;
				w_col = 3;
			} else if(which_step == 13) {
				w_row = 4;
				w_col = 3;
			} else if(which_step == 14) {
				w_row = 5;
				w_col = 3;
			} else if(which_step == 15) {
				w_row = 1;
				w_col = 4;
			} else if(which_step == 16) {
				w_row = 2;
				w_col = 4;
			} else if(which_step == 17) {
				w_row = 3;
				w_col = 4;
			} else if(which_step == 18) {
				w_row = 4;
				w_col = 4;
			} else if(which_step == 19) {
				w_row = 5;
				w_col = 4;
			} else if(which_step == 20) {
				w_row = 1;
				w_col = 5;
			} else if(which_step == 21) {
				w_row = 2;
				w_col = 5;
			} else if(which_step >= 22) {
				done = true;
			}
			which_step++;
		}
		sleep_counter++;
		if(sleep_counter == 12) {
			sleep_counter = 0;
		}
	}
	
	public static void render(Graphics g) {
		BufferedImageLoader loader = new BufferedImageLoader();
		SG_ss = loader.loadImage("./src/game/graphics/sprites/Satellite Gaming_SS.png");
		ss = new SateliteGamingResolver(SG_ss);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		pic = ss.grabImage(w_row, w_col, 100, 100 );
		g.drawImage(pic, (Game.WIDTH/2)-300, (Game.HEIGHT/2)-300, 600, 600, null);
	}
}
	
	



