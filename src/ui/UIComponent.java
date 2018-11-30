package ui;

import java.awt.Color;
import java.awt.Graphics;

public abstract class UIComponent {
	protected int x, y;
	protected int w, h;
	protected boolean isVisible;
	protected Color color;
	
	protected UIComponent() {
		this.color     = Color.WHITE;
		this.isVisible = true;
	}
	
	public void setPos(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void setSize(int w, int h) {
		this.setW(w);
		this.setH(h);
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
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}
	
	public abstract void draw(Graphics g);
}
