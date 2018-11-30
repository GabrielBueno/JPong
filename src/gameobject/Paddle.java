package gameobject;

import java.awt.Color;
import java.awt.Graphics;

import game.GameSettings;

public class Paddle extends GameObject {
	public static final int PADDLE_WIDTH  = 15;
	public static final int PADDLE_HEIGHT = 100;
	
	private String name;
	private int initialX;
	private int score;
	private Color color;
	
	public Paddle(int initialX) {
		super();
		
		this.initialX = initialX;
		this.score    = 0;
		this.color    = Color.WHITE;
		
		this.setSize(PADDLE_WIDTH, PADDLE_HEIGHT);
		this.setPos(this.initialX, (GameSettings.WINDOW_HEIGHT / 2) - (this.h / 2));
		this.setVel(0.0, 0.0);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(this.x, this.y, this.w, this.h);
	}

	@Override
	public void update() {
		this.y += this.velY;
		
		// Impede que o jogador se movimente fora das restrições do tamanho da tela
		if (this.y <= 0)
			this.y = 5;
		
		if (this.y >= GameSettings.WINDOW_HEIGHT - this.h - 50)
			this.y  = GameSettings.WINDOW_HEIGHT - this.h - 50;
	}
	
	@Override
	public void onCollision(GameObject collided) {
		
	}
	
	public void moveUp() {
		this.velY = -8.0;
	}
	
	public void moveDown() {
		this.velY = 8.0;
	}
	
	public void stop() {
		this.velY = 0.0;
	}
	
	public void resetPosition() {
		this.x = this.initialX;
	}
	
	public void resetScore() {
		this.score = 0;
	}
	
	public void resetState() {
		this.resetPosition();
		this.resetScore();
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public int scoreUp() {
		return (this.score++);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
