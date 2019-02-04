package game.backend;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class SquidMan extends GameObject {
	
	public static final int WIDTH = 1280, HEIGHT = 720;
	
	public BufferedImage squidMan;
	AffineTransform tx = new AffineTransform();


	Random r = new Random();
	public int temp;
	
	
	public SquidMan(int x, int y, ID id) {
		super(x, y, id);
		
		try {
			squidMan = ImageIO.read(new File("C:\\Users\\Dylan\\Downloads\\SquidMan.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		velX = (r.nextInt(5) + 1); 
		velY = (r.nextInt(5) + 1);
	}
	
	public void tick() {
		/*
		tx.rotate(.05, squidMan.getWidth() / 2, squidMan.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		squidMan = op.filter(squidMan, null);
		*/
		x += velX;
		y += velY;
		if(x > WIDTH) {
			x = 0;
		}
		if(y > HEIGHT) {
			y = 0;
		}
	}
	
	public void render(Graphics g) {

		g.drawImage(squidMan, x, y, null);
		//g.fillRect(x, y, 50, 50);
	}
}
