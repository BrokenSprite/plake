package com.plake.entity;

import java.util.HashMap;

import com.plake.audio.AudioPlayer;
import com.plake.gamestate.LevelCompletedState;
import com.plake.tilemap.TileMap;

public class Enemy extends MapObject {

	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;

	protected boolean flinching;
	protected long flinchTimer;

	private HashMap<String, AudioPlayer> sfx;

	public Enemy(TileMap tm) {
		super(tm);
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("enemyhit", new AudioPlayer("/SFX/enemyhit.mp3"));

	}

	public boolean isDead() {
		return dead;
	}

	public int getDamage() {
		return damage;
	}

	public void hit(int damage) {
		if (dead || flinching) return;
		health -= damage;
		sfx.get("enemyhit").play();
		if (health < 0) health = 0;
		if (health == 0) {
			LevelCompletedState.eDead++;
			LevelCompletedState.score++;
			dead = true;
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	public void update() {

	}

}
