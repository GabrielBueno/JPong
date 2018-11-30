package ui;

import java.awt.Font;
import java.awt.Graphics;

import resource.FontManager;

public class Label extends UIComponent {
	private Font font;
	private String text;
	private int size;
	
	public Label() {
		this("");
	}
	
	public Label(String text) {
		this(text, 14);
	}
	
	public Label(String text, int size) {
		this(text, size, 0, 0);
	}
	
	public Label(String text, int size, int x, int y) {
		super();
		
		this.setText(text);
		this.setSize(size);
		this.setPos(x, y);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
	public String getText() {
		return this.text;
	}
	
	public void setSize(int size) {
		this.size = size;
		this.font = FontManager.getInstance().getFont(size);
	}
	
	public int getSize() {
		return this.size;
	}
	
	@Override
	public void draw(Graphics g) {
		if (!this.isVisible)
			return;
		
		g.setColor(this.color);
		g.setFont(this.font);
		
		g.drawString(this.text, this.x, this.y);
	}
}
