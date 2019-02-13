package game.graphics;

import java.awt.image.BufferedImage;

public class JetPackFuelResolver {
	private BufferedImage image;
	
	public JetPackFuelResolver(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col*256)-256, (row*256)-256, width, height);
		return img;
}
}
