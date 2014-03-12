package com.plake.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.plake.entity.Animation;
import com.plake.tilemap.TileMap;

public class Teleport extends EntitySpecial {

	private BufferedImage[] sprites;

	public Teleport(TileMap tm) {
		super(tm);
		facingRight = true;
		width = height = 40;
		cwidth = 20;
		cheight = 40;
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites//Level/Teleport.gif"));
			sprites = new BufferedImage[9];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(10);

			right = true;
			facingRight = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		animation.update();
	}

	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
