package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Score {

	GamePanel gp;
	public Score(GamePanel gp) {
		this.gp = gp;
	}
	public void saveScore() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("score.txt", true));
			
			bw.write(""+gp.point);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadSocre() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("score.txt"));
			
			String s = br.readLine();
			
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
