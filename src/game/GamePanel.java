package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import gameobject.Ball;
import gameobject.Paddle;
import resource.FontManager;
import ui.Label;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	/* Variáveis estáticas de configuração */
	private static final int INPUT_FOCUS_TYPE = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final int MAX_SCORE        = 5;
	
	/*
	 * gameState pode assumir três estados:
	 * 	- GameState.PAUSED, quando o jogo está pausado
	 * 	- GameState.RUNNING, quando o jogo está sendo executado normalmente
	 * 	- GameState.QUIT, quando o jogo foi encerrado
	 * 
	 * havendo um método de draw e update para cada estado.
	 * */
	private GameState gameState;
	
	/* Game Objects */
	private Paddle player1;
	private Paddle player2;
	private Ball ball;
	
	/* UI Components */
	private Label titleLabel;
	private Label player1ScoreLabel;
	private Label player2ScoreLabel;
	private Label escToQuitLabel;
	private Label spaceToStartPauseLabel;
	private Label generalMessageLabel;
	
	public GamePanel() {
		FontManager.getInstance().initAllFonts();
		
		this.gameState = GameState.PAUSED;
		
		this.player1 = new Paddle(10);
		this.player2 = new Paddle(GameSettings.WINDOW_WIDTH - (Paddle.PADDLE_WIDTH * 2) - 10);
		this.ball    = new Ball();
		
		this.player1.setName("Jogador 1");
		this.player2.setName("Jogador 2");
		
		this.titleLabel             = new Label("PONG", 20, (GameSettings.WINDOW_WIDTH / 2) - 50, (GameSettings.WINDOW_HEIGHT / 2) - 50);
		this.player1ScoreLabel      = new Label("0", 16, 50, 50);
		this.player2ScoreLabel      = new Label("0", 16, GameSettings.WINDOW_WIDTH - 66, 50);
		this.escToQuitLabel         = new Label("Aperte ESC para sair.", 11, 50, GameSettings.WINDOW_HEIGHT - 61);
		this.spaceToStartPauseLabel = new Label("Aperte SPACE para iniciar.", 11, GameSettings.WINDOW_WIDTH - 330, GameSettings.WINDOW_HEIGHT - 61);
		this.generalMessageLabel    = new Label("", 11, (GameSettings.WINDOW_WIDTH / 2) - 50, (GameSettings.WINDOW_HEIGHT / 2) + 30);
		
		this.initInputMap();
		this.initActionMap();
	}
	
	/* Chama o método correspondente do estado atual para desenhar os objetos na tela */
	@Override
	public void paintComponent(Graphics g) {
		this.clearScreen(g);
		
		switch (this.gameState) {
			case PAUSED:
				this.drawPaused(g);
				break;
				
			case RUNNING:
				this.drawRunning(g);
				break;
				
			case QUIT:
				break;
		}
	}
	
	/* Chama o método correspondente do estado atual para atualizar os objetos na tela */
	public void updateGame() {
		switch (this.gameState) {
			case PAUSED:
				this.updatePaused();
				break;
				
			case RUNNING:
				this.updateRunning();
				break;
				
			case QUIT:
				break;
		}
	}
	
	public boolean isRunning() {
		return this.gameState != GameState.QUIT;
	}
	
	/* Desenha e atualiza os objetos quando o jogo está pausado */
	private void drawPaused(Graphics g) {
		this.titleLabel.draw(g);
		this.spaceToStartPauseLabel.draw(g);
		this.generalMessageLabel.draw(g);
	}
	
	private void updatePaused() {
		
	}
	
	/* Desenha e atualiza os objetos quando o jogo está sendo executado */
	private void drawRunning(Graphics g) {
		this.player1ScoreLabel.draw(g);
		this.player2ScoreLabel.draw(g);
		this.escToQuitLabel.draw(g);
		this.spaceToStartPauseLabel.draw(g);
		// this.generalMessageLabel.draw(g);
		
		this.player1.draw(g);
		this.player2.draw(g);
		this.ball.draw(g);
	}
	
	private void updateRunning() {
		this.player1.update();
		this.player2.update();
		
		this.ball.update();
		this.ball.checkCollisions(this.player1);
		this.ball.checkCollisions(this.player2);
		
		if (this.ball.isOffboundsRight()) {
			this.scorePaddle(this.player1);
			this.player1ScoreLabel.setText("" + this.player1.getScore());
		}
		
		if (this.ball.isOffboundsLeft()) {
			this.scorePaddle(this.player2);
			this.player2ScoreLabel.setText("" + this.player2.getScore());;
		}
		
		if (this.player1.getScore() >= MAX_SCORE)
			this.finishGame(this.player1);
		
		if (this.player2.getScore() >= MAX_SCORE)
			this.finishGame(this.player2);
	}
	
	/* Alterna o estado do jogo entre em execução e pausado  */
	private void changeState() {
		this.gameState = this.gameState == GameState.PAUSED ? GameState.RUNNING : GameState.PAUSED;
	}
	
	/* Termina o jogo */
	private void finishGame(Paddle winnerPaddle) {
		this.generalMessageLabel.setText(winnerPaddle.getName() + " ganhou!");
		this.player1ScoreLabel.setText("0");
		this.player2ScoreLabel.setText("0");
		
		this.player1.resetState();
		this.player2.resetState();
		this.ball.reset();
		
		this.changeState();
	}
	
	/* Métodos auxiliares */
	private void clearScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT);
	}
	
	private void resetAllPositions() {
		this.player1.resetPosition();
		this.player2.resetPosition();
		this.ball.reset();
	}
	
	private void scorePaddle(Paddle paddle) {
		paddle.scoreUp();
		this.resetAllPositions();
	}
	
	private void quit() {
		this.gameState = GameState.QUIT;
	}
	
	/* Métodos de manipulação de eventos do teclado */
	private void initInputMap() {
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "player1_moveup");
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "player1_movedown");
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true),  "player1_stop");
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true),  "player1_stop");
		
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,   0, false), "player2_moveup");
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "player2_movedown");
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,   0, true),  "player2_stop");
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),  "player2_stop");
		
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,  0, false), "change_state");
		this.getInputMap(INPUT_FOCUS_TYPE).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "quit");
	}
	
	private void initActionMap() {
		this.getActionMap().put("player1_moveup",   this.doAction(() -> { this.player1.moveUp();   } ));
		this.getActionMap().put("player1_movedown", this.doAction(() -> { this.player1.moveDown(); } ));
		this.getActionMap().put("player1_stop",     this.doAction(() -> { this.player1.stop();     } ));
		
		this.getActionMap().put("player2_moveup",   this.doAction(() -> { this.player2.moveUp();   } ));
		this.getActionMap().put("player2_movedown", this.doAction(() -> { this.player2.moveDown(); } ));
		this.getActionMap().put("player2_stop",     this.doAction(() -> { this.player2.stop();     } ));
		
		this.getActionMap().put("change_state",     this.doAction(() -> { this.changeState(); } ));
		this.getActionMap().put("quit",             this.doAction(() -> { this.quit(); } ));
	}
	
	/*
	 * Interface GameAction e o método doAction(GameAction) foram criados
	 * para facilitar a escrita do método initActionMap, permitindo a utilização de
	 * funções lambda, como por exemplo:
	 * 		this.doAction(() -> { this.player1.moveUp();   } )
	 * 
	 * Desta forma, simplifica-se a sintaxe da definição dos métodos de ação 
	 * para cada evento do teclado. 
	 * */
	private AbstractAction doAction(GameAction a) {
		return new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				a.action();
				
			}
		};
	}
	
	private interface GameAction {
		void action();
	}
	
	/* Enum GameState, define cada estado de jogo possível */
	private enum GameState {
		PAUSED,
		RUNNING,
		QUIT
	}
}
