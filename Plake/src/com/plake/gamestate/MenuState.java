package com.plake.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.plake.audio.AudioPlayer;
import com.plake.main.Game;
import com.plake.tilemap.Background;
import com.plake.utils.Keys;

public class MenuState extends GameState {

	private Background bg;

	private int currentChoice = 0;
	private String[] options = { "Start", "Quit" };

	private Color versionColor;

	private Font font, fontInfo;

	private BufferedImage title, up, down, enter, head;

	private HashMap<String, AudioPlayer> sfx;
	
	private AudioPlayer bgMusic;

	public MenuState(GameStateManager gsm) {
		super(gsm);

		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("menuselect", new AudioPlayer("/SFX/menuselect.mp3"));
		sfx.put("menuoption", new AudioPlayer("/SFX/menuoption.mp3"));
		sfx.put("menumusic", new AudioPlayer("/Music/menumusic.mp3"));
		
		bgMusic = new AudioPlayer("/Music/menumusic.mp3");
		bgMusic.play();
		
		this.gsm = gsm;
		try {

			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			versionColor = new Color(84, 84, 84);
			font = new Font("Arial", Font.PLAIN, 12);
			fontInfo = new Font("Arial", Font.PLAIN, 10);

			title = ImageIO.read(getClass().getResourceAsStream("/Menu/title.png"));

			up = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/arrowup.gif"));
			down = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/arrowdown.gif"));
			enter = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/enter.gif"));
			
			head = ImageIO.read(getClass().getResourceAsStream("/Sprites/Map/icon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//sfx.get("menumusic").play();
		//JukeBox.load("/Music/menumusic.mp3", "menumusic");
		//JukeBox.loop("menumusic", 600, JukeBox.getFrames("menumusic") - 2200);
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
		g.drawImage(up, 290, 188, null);
		g.drawImage(down, 310, 188, null);
		g.drawImage(enter, 80, 188, null);
		
		if (currentChoice == 0) {
			g.drawImage(head, 163, 126, null);
		}
		if (currentChoice == 1) {
			g.drawImage(head, 163, 142, null);
		}

		g.setColor(versionColor);
		g.setFont(fontInfo);
		g.drawString("Open Beta " + Game.version, 162, 75);

		g.drawString("Choose", 83, 210);
		g.drawString("Select", 291, 210);

		g.setFont(font);

		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 190, 140 + i * 15);
		}
	}

	private void select() {
		if (currentChoice == 0) {
			gsm.setState(7);
			bgMusic.stop();
		}
		if (currentChoice == 1) {
			System.exit(0);
		}
	}

	public void handleInput() {

		if (Keys.isPressed(Keys.ENTER)) {
			sfx.get("menuselect").play();
			select();
		} else if (Keys.isPressed(Keys.UP)) {
			if (currentChoice > 0) {
				sfx.get("menuoption").play();
				currentChoice--;
			}
		} else if (Keys.isPressed(Keys.DOWN)) {
			if (currentChoice < options.length - 1) {
				sfx.get("menuoption").play();
				currentChoice++;
			}
		}
	}
}
