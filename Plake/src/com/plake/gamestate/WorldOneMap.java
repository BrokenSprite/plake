package com.plake.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.plake.audio.AudioPlayer;
import com.plake.entity.FireBall;
import com.plake.entity.Player;
import com.plake.tilemap.Background;
import com.plake.utils.Keys;

public class WorldOneMap extends GameState {
	private Background bg;
	private int currentChoice = 0;
	private String[] options = { "Green", "Kat", "Blue", "Purple" };
	private Font font;
	private Font fontInfo;
	private BufferedImage title, l1, l2, l3, l4, l5;
	private HashMap<String, AudioPlayer> sfx;

	public WorldOneMap(GameStateManager gsm) {
		super(gsm);
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("menuselect", new AudioPlayer("/SFX/menuselect.mp3"));
		sfx.put("menuoption", new AudioPlayer("/SFX/menuoption.mp3"));
		sfx.put("bump", new AudioPlayer("/SFX/bump.mp3"));
		this.gsm = gsm;
		try {
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			font = new Font("Arial", Font.PLAIN, 12);
			fontInfo = new Font("Arial", Font.PLAIN, 10);
			title = ImageIO.read(getClass().getResourceAsStream(
					"/Menu/charsel.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
	}

	public void update() {
		handleInput();
		bg.update();
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		g.drawImage(title, 105, 25, null);
		g.setFont(font);
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice && i == 0) {
				
			} else if (i == currentChoice && i == 1) {
				
			} else if (i == currentChoice && i == 2) {

			} else if (i == currentChoice && i == 3) {
				
			} else {
				
			}
			//g.drawString(options[i], 70 + i * 80, 180);
		}
	}

	private void select() {
	
	}

	public void handleInput() { //Simple input handling obviously referencing Keys.class
		/*
		 * if (Keys.isPressed(Keys.ENTER) && currentChoice == 1) {
		 * sfx.get("bump").play(); return; }
		 */
		if (Keys.isPressed(Keys.ENTER) /* && currentChoice != 1 */) {
			sfx.get("menuselect").play();
			System.out.println("Handled Enter");
			select();
		} else if (Keys.isPressed(Keys.LEFT)) {
			System.out.println("Handled Left");
			if (currentChoice > 0) {
				sfx.get("menuoption").play();
				currentChoice--;
			}
		} else if (Keys.isPressed(Keys.RIGHT)) {
			System.out.println("Handled Down");
			if (currentChoice < options.length - 1) {
				sfx.get("menuoption").play();
				currentChoice++;
			}
		}
	}
	
}
