package com.plake.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.plake.audio.AudioPlayer;
import com.plake.audio.JukeBox;
import com.plake.entity.FireBall;
import com.plake.entity.Player;
import com.plake.tilemap.Background;
import com.plake.utils.Keys;

public class CharacterSelect extends GameState {

	private Background bg;

	private int currentChoice = 0;
	private String[] options = { "Green", "Kat", "Blue" };

	private Color versionColor;

	private Font font;
	private Font fontInfo;

	private BufferedImage left, right, enter, greendrag, kat, bluedrag, /*
																		 * comingsoon
																		 * ,
																		 * soontitle
																		 * ,
																		 */title, highlighted;

	private HashMap<String, AudioPlayer> sfx;

	public CharacterSelect(GameStateManager gsm) {
		super(gsm);

		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("menuselect", new AudioPlayer("/SFX/menuselect.mp3"));
		sfx.put("menuoption", new AudioPlayer("/SFX/menuoption.mp3"));
		sfx.put("bump", new AudioPlayer("/SFX/bump.mp3"));

		this.gsm = gsm;
		try {
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			versionColor = new Color(84, 84, 84);
			font = new Font("Arial", Font.PLAIN, 12);
			fontInfo = new Font("Arial", Font.PLAIN, 10);

			title = ImageIO.read(getClass().getResourceAsStream("/Menu/charsel.png"));
			highlighted = ImageIO.read(getClass().getResourceAsStream("/Menu/highlighted.gif"));

			left = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/left.gif"));
			right = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/right.gif"));
			enter = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/enter.gif"));

			greendrag = ImageIO.read(getClass().getResourceAsStream("/Sprites/Menu/greendrag.gif"));
			kat = ImageIO.read(getClass().getResourceAsStream("/Sprites/Menu/kat.gif"));
			bluedrag = ImageIO.read(getClass().getResourceAsStream("/Sprites/Menu/bluedrag.gif"));

			// comingsoon =
			// ImageIO.read(getClass().getResourceAsStream("/Sprites/Menu/comingsoon.gif"));
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
		g.drawImage(title, 60, 30, null);
		g.drawImage(left, 180, 188, null);
		g.drawImage(right, 202, 188, null);
		g.drawImage(enter, 80, 188, null);

		g.drawImage(greendrag, 75, 130, null);
		g.drawImage(kat, 154, 133, null);
		g.drawImage(bluedrag, 230, 130, null);

		g.setColor(versionColor);
		g.setFont(fontInfo);
		// g.drawString("Open Beta " + Game.version, 118, 80);

		g.drawString("Choose", 83, 210);
		g.drawString("Select", 181, 210);

		g.setFont(font);

		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice && i == 0) {
				g.setColor(Color.BLACK);
				g.drawImage(highlighted, 75, 130, null);
			} else if (i == currentChoice && i == 1) {
				g.setColor(Color.BLACK);
				g.drawImage(highlighted, 150, 130, null);
			} else if (i == currentChoice && i == 2) {
				g.setColor(Color.BLACK);
				g.drawImage(highlighted, 230, 130, null);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 70 + i * 80, 180);
		}
	}

	private void select() {
		if (currentChoice == 0) {
			Player.path = "/Sprites/Player/Greendrag/playersprites.gif";
			FireBall.path = "/Sprites/Player/fireball.gif";
			gsm.setState(1);
			JukeBox.stop("menuMusic");
		}
		if (currentChoice == 1) {
			Player.path = "/Sprites/Player/Kat/playersprites.gif";
			FireBall.path = "/Sprites/Player/Kat/hairball.gif";
			gsm.setState(1);
			JukeBox.stop("menuMusic");
		}
		if (currentChoice == 2) {
			Player.path = "/Sprites/Player/Bluedrag/playersprites.gif";
			FireBall.path = "/Sprites/Player/fireball.gif";
			gsm.setState(1);
			JukeBox.stop("menuMusic");
		}
	}

	public void handleInput() {

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