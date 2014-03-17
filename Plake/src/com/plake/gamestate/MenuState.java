package com.plake.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryJavaSound;

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

	private BufferedImage title, up, down, enter;

	private HashMap<String, AudioPlayer> sfx;

	// private Updater u;

	public MenuState(GameStateManager gsm) {
		super(gsm);

		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("menuselect", new AudioPlayer("/SFX/menuselect.mp3"));
		sfx.put("menuoption", new AudioPlayer("/SFX/menuoption.mp3"));

		Game.class.getResource("/Music/menumusic.ogg");
		try {
			SoundSystemConfig.addLibrary(LibraryJavaSound.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
		} catch (SoundSystemException e) {
			System.err.println("error linking with the plug-ins");
		}
		SoundSystem menuMusic = new SoundSystem();

		menuMusic.quickPlay(false, Game.class.getResource("/Music/menumusic.ogg"), "menumusic.ogg", true, 0, 2200, 2200, SoundSystemConfig.ATTENUATION_NONE, 2200);

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
		g.drawImage(up, 180, 188, null);
		g.drawImage(down, 202, 188, null);
		g.drawImage(enter, 80, 188, null);

		g.setColor(versionColor);
		g.setFont(fontInfo);
		g.drawString("Open Beta " + Game.version, 118, 80);

		g.drawString("Choose", 83, 210);
		g.drawString("Select", 181, 210);

		g.setFont(font);

		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 140 + i * 15);
		}
	}

	private void select() {
		if (currentChoice == 0) {
			gsm.setState(7);
			
		}
		if (currentChoice == 1) {
			System.exit(0);
		}
	}

	public void handleInput() {

		if (Keys.isPressed(Keys.ENTER)) {
			sfx.get("menuselect").play();
			System.out.println("Handled Enter");
			select();
		} else if (Keys.isPressed(Keys.UP)) {
			System.out.println("Handled Up");
			if (currentChoice > 0) {
				sfx.get("menuoption").play();
				currentChoice--;
			}
		} else if (Keys.isPressed(Keys.DOWN)) {
			System.out.println("Handled Down");
			if (currentChoice < options.length - 1) {
				sfx.get("menuoption").play();
				currentChoice++;
			}
		}
	}
}
