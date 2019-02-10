package game.backend;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends Canvas {
	private static final long serialVersionUID = 9034494958129720942L;
	
	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setUndecorated(false); //full screen or windowed
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Game.WIDTH = screenSize.width;
		//Game.HEIGHT = screenSize.height;
		Game.WIDTH = width;
		Game.HEIGHT = height;
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.toFront();
		game.start();
		
	}
	
}
