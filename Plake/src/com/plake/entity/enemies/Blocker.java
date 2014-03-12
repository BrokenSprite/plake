package com.plake.entity.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.plake.entity.Animation;
import com.plake.entity.Enemy;
import com.plake.tilemap.TileMap;

public class Blocker extends Enemy {

	private BufferedImage[] sprites;

	public Blocker(TileMap tm) {
		super(tm);

		moveSpeed = 1.5;
		maxSpeed = 1.6;
		fallSpeed = 1.0;
		maxFallSpeed = 4.0;

		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;

		health = maxHealth = 500;
		damage = 100;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/blocker.gif"));
			sprites = new BufferedImage[1];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);

		right = true;
		facingRight = true;
	}

	private void getNextPosition() {
		// movement
		if (left) {
			dy -= moveSpeed;
			if (dy < -maxSpeed) {
				dy = -maxSpeed;
			}
		} else if (right) {
			dy += moveSpeed;
			if (dy > maxSpeed) {
				dy = maxSpeed;
			}
		}
		// falling
		if (falling) {
			dy += fallSpeed;
		}
	}

	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 100000;
			if (elapsed > 400) {
				flinching = false;
			}
		}
		// if we hit a wall, go the other way!
		if (right && dy == 0) {
			right = false;
			left = true;
			facingRight = false;
		} else if (left && dy == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		// THIS ALWAYS UPDATES ANIMATIONS!!!
		animation.update();
	}

	public void draw(Graphics2D g) {
		setMapPosition();
		super.draw(g);
	}

}
