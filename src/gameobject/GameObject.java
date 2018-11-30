package gameobject;

import java.awt.Graphics;

public abstract class GameObject {
	protected int x, y;
	protected int w, h;
	protected double velX, velY;
	
	public GameObject() {}
	
	public GameObject(int x, int y) {
		this.setPos(x, y);
	}
	
	public GameObject(int x, int y, int w, int h) {
		this.setPos(x, y);
		this.setSize(w, h);
	}
	
	public boolean checkCollisions(GameObject obj) {
		boolean rightCollision = this.x + this.w >= obj.x && this.x + this.w <= obj.x + obj.w;
		boolean leftCollision  = this.x <= obj.x + obj.w  && this.x >= obj.x;
		
		boolean topCollision    = this.y + this.h >= obj.y && this.y + this.h <= obj.y + obj.h;
		boolean bottomCollision = this.y <= obj.y + obj.h  && this.y >= obj.y;
		
		boolean collided = (leftCollision || rightCollision) && (bottomCollision || topCollision);
		
		if (collided) {
			this.onCollision(obj);
			obj.onCollision(this);
		}
		
		return collided;
	}
	
	public void setPos(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void setSize(int w, int h) {
		this.setW(w);
		this.setH(h);
	}
	
	public void setVel(double velX, double velY) {
		this.setVelX(velX);
		this.setVelY(velY);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public double getVelX() {
		return this.velX;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	public double getVelY() {
		return this.velY;
	}
	
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	public abstract void draw(Graphics g);
	public abstract void update();
	public abstract void onCollision(GameObject collided);
}
