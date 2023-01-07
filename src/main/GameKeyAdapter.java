package main;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameKeyAdapter extends KeyAdapter{
	
	GamePanel gp;
	
	GameKeyAdapter(GamePanel gp) {
		this.gp = gp;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(gp.gameState == gp.startState) {
			gp.gameState = gp.playState;
		}
		if(gp.gameState == gp.overState) {
			gp.gameState = gp.playState;
			gp.startGame();
		}
		if((gp.direction == 'U' || gp.direction == 'D') && gp.y[0] != gp.y[1]) {
			if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {			
				gp.direction = 'L';
			} else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				gp.direction = 'R';
			}
		} else if((gp.direction == 'R' || gp.direction == 'L') && gp.x[0] != gp.x[1]) {
			if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.direction = 'U';
			} else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.direction = 'D';
			}
		}
	}
}
