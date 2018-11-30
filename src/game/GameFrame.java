package game;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private GamePanel gamePanel;
	private Thread gameThread;
	private long frameInterval;
	private long lastFrame;
	
	public GameFrame() {
		super();
		
		this.gamePanel     = new GamePanel();
		this.gameThread    = new Thread(() -> mainLoop());
		this.frameInterval = 1000000000L / GameSettings.FPS;
		this.lastFrame     = System.nanoTime();
		
		this.setTitle(GameSettings.GAME_TITLE);
		this.setSize(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.gamePanel.setFocusable(true);
		
		this.add(this.gamePanel);
	}
	
	public void run() {
		this.gameThread.start();
	}
	
	private void mainLoop() {
		while (this.gamePanel.isRunning()) {
			// Conta o intervalo entre a execução desta iteração e a execução do último quadro.
			// Somente executa o próximo quadro se o intervalo for maior ou igual que o valor 
			// especificado em this.frameInterval
			if (System.nanoTime() - this.lastFrame < this.frameInterval)
				continue;
			
			this.gamePanel.updateGame();
			this.gamePanel.repaint();
			
			this.lastFrame = System.nanoTime();
		}
		
		this.dispose();
	}
}
