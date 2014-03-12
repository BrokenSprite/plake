package com.plake.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.plake.audio.AudioPlayer;

public class Explosion {

	private int x;
	private int y;
	private int xmap;
	private int ymap;

	private int width;
	private int height;

	private Animation animation;
	private BufferedImage[] sprites;

	private boolean remove;

	private HashMap<String, AudioPlayer> sfx;

	public Explosion(int x, int y) {
		this.y = y;
		this.x = x;

		width = 30;
		height = 30;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/explosion.gif"));
			sprites = new BufferedImage[6];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(70);
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("explode", new AudioPlayer("/SFX/explode.mp3"));
		sfx.get("explode").play();
	}

	public void update() {
		animation.update();
		if (animation.hasPlayedOnce()) {
			remove = true;
		}
	}

	public boolean shouldRemove() {
		return remove;
	}

	public void setMapPosition(int x, int y) {
		xmap = x;
		ymap = y;
	}

	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);

	}
}
