package gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import game.GameSettings;

public class Ball extends GameObject {
	private double radAngle;
	private double degAngle;
	private long launchDelayTime;
	private long lastLaunch;
	private Color color;
	private Random generator;
	
	public Ball() {
		super();
		
		this.launchDelayTime = 350000000L;
		this.lastLaunch      = System.nanoTime();
		this.generator       = new Random();
		
		this.setSize(15, 15);
		this.setInitialPosition();
		this.setInitialVel();
		this.setColor(Color.WHITE);
		
		this.generateAngle();
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.x, this.y, this.w, this.h);
	}

	@Override
	public void update() {
		// Impede que a bola seja lançada logo no início da rodada, tendo um atraso especificado em
		// this.launchDelayTime
		if (System.nanoTime() - this.lastLaunch < this.launchDelayTime)
			return;
		
		// Calcula as próximas coordenadas, a partir da velocidade atual, e do ângulo da bola
		this.x = (int) (this.x + this.velX *  Math.cos(radAngle));
		this.y = (int) (this.y + this.velY * -Math.sin(radAngle));
		
		// Inverte o ângulo da bola, se esta colidiu com os limites superior ou inferior da tela.
		if (this.y <= 0 || this.y >= GameSettings.WINDOW_HEIGHT - this.h - 40)
			this.setAngle(-this.degAngle);
	}
	
	/* Método executado sempre que colidir com um jogador */
	@Override
	public void onCollision(GameObject collided) {
		// Inverte o ângulo
		this.setAngle(-degAngle - 180.0);
		
		// Aumenta a velocidade em um valor randômico
		this.velX = this.velX + generator.nextDouble() * 2;
		this.velY = this.velY + generator.nextDouble() * 2;
	}
	
	public void reset() {
		this.lastLaunch = System.nanoTime();
		
		this.setInitialPosition();
		this.setInitialVel();
		this.generateAngle();
	}
	
	/* Checa se a bola ultrapassou os limites esquerdo ou direito da tela */
	public boolean isOffboundsLeft() {
		return this.x <= 0;
	}
	
	public boolean isOffboundsRight() {
		return this.x >= GameSettings.WINDOW_WIDTH;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	/* Gera um ângulo aleatório */
	private void generateAngle() {
		this.setAngle((90 * (generator.nextInt(4) + 1)) + 45);
	}
	
	private void setAngle(double degAngle) {
		this.degAngle = degAngle;
		this.radAngle = Math.toRadians(degAngle);
	}
	
	private void setInitialPosition() {
		this.setPos((GameSettings.WINDOW_WIDTH  / 2) - (this.w / 2), 
			    (GameSettings.WINDOW_HEIGHT / 2) - (this.h / 2));
	}
	
	private void setInitialVel() {
		this.setVel(7.0, 7.0);
	}
}
