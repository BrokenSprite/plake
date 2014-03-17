package com.plake.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import com.plake.main.GamePanel;
import com.plake.utils.Keys;

public class PauseState {

	private Font font;

	private GameStateManager gsm;
	private BufferedImage w;

	public PauseState(GameStateManager gsm) {
		super();
		this.gsm = gsm;

		try {
			// title =
			// ImageIO.read(getClass().getResourceAsStream("/Menu/title.png"));

			w = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/w.gif"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		font = new Font("Century Gothic", Font.PLAIN, 14);
	}

	public void init() {
	}

	public void update() {
		handleInput();
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		
		g.drawImage(w, 86, 100, null);
		
		g.setFont(font);
		g.drawString("Game Paused", 112, 90);
		g.drawString("Press     to go to the Main Menu", 45, 110);

	}

	private void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE)) {
			System.out.println("Paused : Press Escape to un-pause");
			gsm.setPaused(false);
		}
		if (Keys.isPressed(Keys.BUTTON1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}

	}

}
