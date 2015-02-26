package com.plake.gamestate;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.plake.audio.AudioPlayer;
import com.plake.utils.Keys;

public class WorldOneMap extends GameState 
{
	private int currentChoice = 0;
	
	public int currentLevel = 1;
	
	private String[] options = { "Level 1", "Level 2", "Level 3", "Level 4", "Level 5" };
	private Font font;
	private Font fontInfo;
	private BufferedImage bg, title, mapBg, map, mapHead, l1, l2, l3, l4, l5;

	private boolean l1beat, l2beat, l3beat, l4beat, l5beat;

	private HashMap<String, AudioPlayer> sfx;
	private GameStateManager gsm;

	public static String playerPath;

	public WorldOneMap(GameStateManager gsm) {
		super(gsm);
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("menuselect", new AudioPlayer("/SFX/menuselect.mp3"));
		sfx.put("menuoption", new AudioPlayer("/SFX/menuoption.mp3"));
		sfx.put("bump", new AudioPlayer("/SFX/bump.mp3"));
		this.gsm = gsm;
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/menubg.gif"));
			font = new Font("Arial", Font.PLAIN, 12);
			fontInfo = new Font("Arial", Font.PLAIN, 10);
			mapBg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/mapbg.png"));
			title = ImageIO.read(getClass().getResourceAsStream("/Menu/w1map.png"));
			map = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/w1map.png"));
			mapHead = ImageIO.read(getClass().getResourceAsStream("/Sprites/Map/icon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
	}

	public void update() {
		handleInput();
		checkLevels();
	}

	private void checkLevels() {
		if (l1beat) {

		}
	}

	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, null);
		g.drawImage(mapBg, 0, 0, null);
		g.drawImage(title, 105, -10, null);
		g.drawImage(map, 0, 0, null);
		g.setFont(font);
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice && i == 0) {
				g.drawImage(mapHead, 109, 168, null);
			} else if (i == currentChoice && i == 1) {
				g.drawImage(mapHead, 137, 132, null);
			} else if (i == currentChoice && i == 2) {
				g.drawImage(mapHead, 189, 132, null);
			} else if (i == currentChoice && i == 3) {
				g.drawImage(mapHead, 189, 84, null);
			} else if (i == currentChoice && i == 4) {
				g.drawImage(mapHead, 281, 84, null);
			}
		}
	}

	public static void moveToNextLevel() {
		
	}
	
	private void select() {
		if (currentChoice == 0) {
			gsm.setState(1);
			currentLevel = 1;
		} else if (currentChoice == 1) {
			gsm.setState(4);
		} else if (currentChoice == 2) {
			gsm.setState(5);
		} else if (currentChoice == 3) {
			gsm.setState(6);
		} else if (currentChoice == 4) {
			gsm.setState(9);
		}
	}

	public void handleInput() { // Simple input handling obviously referencing
								// Keys.class
		if (Keys.isPressed(Keys.ENTER)) {
			sfx.get("menuselect").play();
			select();
		} else if (Keys.isPressed(Keys.LEFT) || (Keys.isPressed(Keys.DOWN))) {
			if (currentChoice > 0) {
				sfx.get("menuoption").play();
				currentChoice--;
			}
		} else if (Keys.isPressed(Keys.RIGHT) || (Keys.isPressed(Keys.UP))) {
			if (currentChoice < options.length - 1) {
				sfx.get("menuoption").play();
				currentChoice++;
			}
		}
	}

}
