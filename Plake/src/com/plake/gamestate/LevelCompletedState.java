package com.plake.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.plake.entity.Player;
import com.plake.main.GamePanel;
import com.plake.utils.Keys;

public class LevelCompletedState extends GameState {

	private Font font;
	private Font fontStats;
	private Font fontMenu;

	public static int eDead;

	public static int score = Player.score;
	public static int lives = Player.health;

	private BufferedImage pEnter;
	private BufferedImage pEsc;

	public LevelCompletedState(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;
		gsm.setPaused(false);
		init();
	}

	public void init() {

		try {
			pEnter = ImageIO.read(getClass().getResourceAsStream(
					"/Sprites/Buttons/enter.gif"));
			pEsc = ImageIO.read(getClass().getResourceAsStream(
					"/Sprites/Buttons/esc.gif"));

			font = new Font("Arial", Font.PLAIN, 17);
			fontStats = new Font("Arial", Font.PLAIN, 13);
			fontMenu = new Font("Arial", Font.PLAIN, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JukeBox.load("/Music/level1-1.mp3", "level1");
		// JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);
	}

	public void update() {
		handleInput();
	}

	public void draw(Graphics2D g) {
	
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.drawImage(pEnter, 123, 130, null);
		g.drawImage(pEsc, 131, 147, null);

		g.setFont(font);
		g.drawString("Congratulations! You beat the level!", 62, 20);

		g.setFont(fontStats);
		g.drawString("Score:                   " + score, 125, 60);
		g.drawString("Lives:                    " + lives + "/5", 125, 80);
		g.drawString("Enemeys Killed:     " + eDead, 125, 100);
		g.drawString("Press             to go to the next level", 85, 140);

		g.setFont(fontMenu);
		g.drawString("Or press            to return to the Main Menu", 90, 155);
	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER)) {
			System.out.println("Enter pressed.");
			gsm.setState(11);
		} else if (Keys.isPressed(Keys.ESCAPE)) {
			gsm.setState(0);
			System.out.println("Escape pressed.");
		}
	}

}
