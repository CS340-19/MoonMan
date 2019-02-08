package game.backend;
import java.awt.Graphics;

import game.object.ID;

//abstract type for any game object, player, enemy, etc..
//seems like an interface from C++
public abstract class GameObject {
	
	protected int x, y;
	protected ID id;
	protected int velX, velY;	//control speed in x, y direction
	protected int width;
	protected int height;
	
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
		width = 32;
		height = 32;
	}
	
	public GameObject(int x, int y, int width, int height, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	public abstract void tick();

	public abstract void render(Graphics g, int row, int col);
	
	//getters and setters
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setID(ID id) {
		this.id = id;
	}
	public ID getID() {
		return id;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public int getVelX() {
		return velX;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	public int getVelY() {
		return velY;
	}
}
