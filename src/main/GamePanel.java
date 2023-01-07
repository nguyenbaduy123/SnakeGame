package main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 100;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int appleX, appleY;
	int point;
	Timer timer;
	int bodyParts;
	char direction;
	Random random;
	Score score = new Score(this);
	
	int gameState;
	final int startState = 0;
	final int playState = 1;
	final int overState = 2;
	Sound sound = new Sound();
	
	public GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new GameKeyAdapter(this));		
		startGame();
	}

	public void startGame() {
		resetGame();
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void resetGame() {
		bodyParts = 6;
		x[0] = SCREEN_WIDTH/2/UNIT_SIZE * UNIT_SIZE;
		y[0] = SCREEN_HEIGHT/2/UNIT_SIZE * UNIT_SIZE;
		for(int i=1; i<=bodyParts; i++) {
			y[i] = y[i-1] + UNIT_SIZE;
			x[i] = x[0];
		}
		point = 0;
		direction = 'U';
		newApple();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(gameState == overState) {
			String text = "Game Over!";
			g.setFont(new Font("TimesRoman", Font.BOLD, 100));
			int width = g.getFontMetrics().stringWidth(text)/2;
			int textX = SCREEN_WIDTH/2 - width;
			int textY = SCREEN_HEIGHT/2;
			g.drawString(text, textX, textY);
			
			g.setFont(new Font("TimesRoman", Font.BOLD, 30));
			text = "Try Again?";
			width = g.getFontMetrics().stringWidth(text)/2;
			textX = SCREEN_WIDTH/2 - width;
			textY = SCREEN_HEIGHT/2 + 20;
			
			g.drawString(text, textX, textY + UNIT_SIZE);
			timer.stop();
		} else if(gameState == startState) {
			String text = "Press Any To Start Game!";
			g.setFont(new Font("TimesRoman", Font.BOLD, 50));
			int width = g.getFontMetrics().stringWidth(text)/2;
			int textX = SCREEN_WIDTH/2 - width;
			int textY = SCREEN_HEIGHT/2;
			g.drawString(text, textX, textY);
			
		}
		
//		if(running) {		
//			for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//			}
		//set Color of head
		
		g.setColor(Color.green);
		g.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
		
			for(int i=1; i<bodyParts; i++) {
				g.setColor(Color.white);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			
			//draw apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
//		}
			g.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
			g.drawString(point+"", 10, 40);
	
	}
			
	public void move() {
		
		if(gameState == playState) {
			
			for(int i = bodyParts; i>0; i--) {
				x[i] = x[i-1];
				y[i] = y[i-1];
			}
			if(direction == 'U') {
				y[0] -= UNIT_SIZE;
			} else if (direction == 'D') {
				y[0] += UNIT_SIZE;
			} else if (direction == 'L') {
				x[0] -= UNIT_SIZE;
			} else if (direction == 'R') {
				x[0] += UNIT_SIZE;
			}
			
			if(x[0] < 0) {
				x[0] = SCREEN_WIDTH - UNIT_SIZE;
			}
			if(x[0] >= SCREEN_WIDTH) {
				x[0] = 0;
			}
			if(y[0] < 0) {
				y[0] = SCREEN_HEIGHT - UNIT_SIZE;
			}
			if(y[0] >= SCREEN_HEIGHT) {
				y[0] = 0;
			}
		}
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
		for(int i=0; i<bodyParts; i++) {
			if(appleX == x[i] && appleY == y[i]) {
				newApple();
			}
		}
	}
	
	public void checkApple() {
		Rectangle snakeHead = new Rectangle(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
		Rectangle apple = new Rectangle(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		if(snakeHead.intersects(apple)) {
			sound.play(0);;
			bodyParts++;
			point++;
			newApple();
		}
	}
	
	public boolean checkCollisions() {
		
		//check if it eat itself
		Rectangle snakeHead = new Rectangle(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
		Rectangle body[] = new Rectangle[bodyParts];
		for(int i = 1; i<bodyParts; i++) {
			body[i] = new Rectangle(x[i],y[i],UNIT_SIZE, UNIT_SIZE);
			if(snakeHead.intersects(body[i])) {
				gameState = overState;
				score.saveScore();
				sound.play(1);
				return true;
			}
		}
		return false;	
	}
//	public void gameOver(Graphics g) {
//		int textX = SCREEN_WIDTH/2;
//		int textY = SCREEN_HEIGHT/2;
//		g.setFont(new Font("TimesRoman", Font.BOLD, 100));
//		g.drawString("Game Over!", textX, textY);
//		g.setFont(new Font("TimesRoman", Font.BOLD, 80));
//		g.drawString("Try Again?", textX, textY +10);
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(gameState == playState) {
			checkCollisions();
			move();
			checkApple();
			repaint();
		}
		else {
			
		}
	}
}
