package com.plake.entity;

import java.util.HashMap;

import com.plake.audio.AudioPlayer;
import com.plake.tilemap.TileMap;

public class EntitySpecial extends MapObject {

	protected boolean spawned;
	
	private HashMap<String, AudioPlayer> sfx;

	public EntitySpecial(TileMap tm) {
		super(tm);
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("enemyhit", new AudioPlayer("/SFX/enemyhit.mp3"));

	}

	public boolean isSpawned() {
		return spawned;
	}

	public void hit(int damage) {
		System.out.println("LEVEL FINISHED!");
	}

	public void update() {

	}

}
