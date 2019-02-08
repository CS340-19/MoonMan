package game.backend;
import java.awt.Graphics;
import java.util.LinkedList;

//Loops through all objects in game, updates them then renders to the screen
public class Handler {
	
	//make linked list to hold game objects
	private static LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	//loops through list
	public void tick() {
		for(int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			object.tick();
		}
	}
	
	public void render(Graphics g, int row, int col) {
		for(int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			object.render(g, row, col);
		}
	}
	
	public static void addObject(GameObject object) {
		objects.add(object);
	}
	
	public static void removeObject(GameObject object) {
		objects.remove(object);
	}
	
	public static LinkedList<GameObject> getObjects() {
		return objects;
	}
	
}