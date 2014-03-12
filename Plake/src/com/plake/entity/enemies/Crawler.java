package com.plake.entity.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.plake.entity.Animation;
import com.plake.entity.Enemy;
import com.plake.tilemap.TileMap;

public class Crawler extends Enemy {

	private BufferedImage[] sprites;

	public Crawler(TileMap tm) {
		super(tm);

		moveSpeed = 0.7;
		maxSpeed = 1;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;

		width = 25;
		height = 25;
		cwidth = 20;
		cheight = 10;

		health = maxHealth = 20;
		damage = 3;

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/crawler.gif"));
			sprites = new BufferedImage[5];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(350);

		right = true;
		facingRight = true;
	}

	private void getNextPosition() {
		// movement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
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
		if (right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		// THIS ALWAYS UPDATES ANIMATIONS!!!
		animation.update();
	}

	public void draw(Graphics2D g) {
		// if (notOnScreen()) return;
		setMapPosition();
		super.draw(g);
	}

}
