package game.sounds;

import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException;

import game.backend.Game;
import game.backend.GameState;

public class audioPlayer {
	Clip clip;
	AudioInputStream audioInputStream;
	static String filePath;
	
	public audioPlayer() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException   
	{
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
	}
	
	public static void main(String file) throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException
	{
		filePath = file;
		audioPlayer audioP = new audioPlayer();
		if(Game.game_background == 1 && Game.loaded == 1) {
			Game.Music = audioP;
		}
		audioP.play();
	}
	
	public void play() {
		clip.start();
	}
	
	public void stop() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException  
    { 
        clip.stop(); 
        clip.close(); 
    } 
}
