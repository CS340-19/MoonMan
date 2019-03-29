package game.object;
import game.backend.GameObject;
import game.backend.Handler;

import java.lang.Math;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.graphics.BufferedImageLoader;
import game.backend.Game;

public class Laser extends GameObject{
	private static int mouseX, mouseY;
	Rectangle rect = new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT);
	BufferedImageLoader loader = new BufferedImageLoader();
	BufferedImage image;
	double angle, speed;
	SquidMan squidman;
	public static Boolean enemyKilled = false;
	
	public Laser(int x, int y, int mouseY, int mouseX, int width, int height, ID id) {
		super(x, y, id);
		remove_me = 0;
		Laser.mouseX = mouseX;
		Laser.mouseY = mouseY;
		
		image = loader.loadImage("./src/game/graphics/sprites/Laser MM.png");
		
		speed = 19;
		angle = Math.atan2(mouseX - x - 120, mouseY - y - 80);
		setVelX((int) (speed * Math.sin(angle)));
		setVelY((int) (speed * Math.cos(angle)));
	}
	
	public void tick() {
		checkCollision();
		x += velX;
		y += velY;
		
	}
	
	private void checkCollision() {
		for(int i = 0; i < Handler.getObjects().size(); i++) {
			if(Handler.getObjects().get(i).getID() == ID.Squidman) {
				squidman = (SquidMan) Handler.getObjects().get(i);
				if(getBottomBounds().intersects(squidman.getBottomBounds())) {
					try {
						game.sounds.audioPlayer.main("./src/game/sounds/wav_files/enemy_hit.wav");
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					remove_me = 1;
					Handler.removeObject(squidman);
					Game.score += 5;
					Game.enemiesRemaining--;
				}else enemyKilled = false;
			}
		}
	}
	
	public Rectangle getBottomBounds() {
		return new Rectangle(x + 110, y + 60, 20, 10);
	}

	public void render(Graphics g, int row, int col) {
		g.drawImage(image, x, y, 128, 128, null);
		//g.setColor(Color.white);
		//g.drawRect(x + 110, y + 60, 20, 10);
	}
}
