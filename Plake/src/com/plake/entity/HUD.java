package com.plake.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.plake.gamestate.LevelCompletedState;

public class HUD {

	private Player player;

	private BufferedImage image;
	private Font font;

	public HUD(Player p) {
		player = p;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			font = new Font("Arial", Font.PLAIN, 14);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 10, null);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 25);
		g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100, 30, 45);
		g.drawString(" " + LevelCompletedState.score / 1, 15, 67);
		
		g.setColor(java.awt.Color.WHITE);
		g.drawString(player.getTimeToString(), 367, 24);
	}

}
