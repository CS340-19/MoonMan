package game.graphics;

import java.awt.image.BufferedImage;

public class SateliteGamingResolver {

		private BufferedImage image;
		
		public SateliteGamingResolver(BufferedImage image) {
			this.image = image;
		}
		
		public BufferedImage grabImage(int col, int row, int width, int height) {
			BufferedImage img = image.getSubimage((col*100)-100, (row*100)-100, width, height);
			return img;
	}
}

