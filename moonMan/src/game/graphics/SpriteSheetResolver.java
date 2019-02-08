package game.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheetResolver {

	private BufferedImage image;
	
	public SpriteSheetResolver(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col*64)-64, (row*64)-64, width, height);
		return img;
	}
}