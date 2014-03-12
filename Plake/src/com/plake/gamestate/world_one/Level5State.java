package com.plake.gamestate.world_one;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.plake.audio.JukeBox;
import com.plake.entity.Enemy;
import com.plake.entity.EntitySpecial;
import com.plake.entity.Explosion;
import com.plake.entity.HUD;
import com.plake.entity.Player;
import com.plake.entity.Teleport;
import com.plake.entity.Title;
import com.plake.entity.enemies.Blocker;
import com.plake.entity.enemies.Crawler;
import com.plake.entity.enemies.Slugger;
import com.plake.gamestate.GameState;
import com.plake.gamestate.GameStateManager;
import com.plake.tilemap.Background;
import com.plake.tilemap.TileMap;
import com.plake.utils.Keys;

public class Level5State extends GameState {

	private TileMap tileMap;
	private Background bg;
	private Player player;
	private ArrayList<Enemy> enemys;
	private ArrayList<EntitySpecial> entities;
	private ArrayList<Explosion> explosions;
	private HUD hud;
	private Teleport teleport;

	private boolean titleStart;

	private Title title;

	private BufferedImage hageonText;

	public Level5State(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;
		gsm.setPaused(false);
		init();
	}

	public void init() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/w1_lvl5.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		player = new Player(tileMap);
		player.setPosition(100, 100);
		teleport = new Teleport(tileMap);
		teleport.setPosition(3990, 150);
		populateEntity();
		explosions = new ArrayList<Explosion>();
		hud = new HUD(player);

		try {
			titleStart = true;
			hageonText = ImageIO.read(getClass().getResourceAsStream("/Title/level5.png"));// TODO
																							// FLAG
			title = new Title(hageonText.getSubimage(0, 0, 98, 40));
			title.sety(60);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JukeBox.load("/Music/level1-1.mp3", "level1");
		JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);

		System.out.println("Level: Level 5 (Nomral Level) Initialized!");// TODO
																			// FLAG
	}

	private void populateEntity() {
		enemys = new ArrayList<Enemy>();
		entities = new ArrayList<EntitySpecial>();
		Slugger s;
		Crawler s2;
		Blocker s3;
		Point[] points = new Point[] { new Point(50, 200), new Point(1260, 140), new Point(2175, 200), new Point(2300, 200) };
		Point[] points2 = new Point[] { new Point(630, 200), new Point(1635, 200), new Point(2820, 80), new Point(3455, 200) };
		Point[] points3 = new Point[] { new Point(1936, 150) };
		for (int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemys.add(s);
		}
		for (int i = 0; i < points2.length; i++) {
			s2 = new Crawler(tileMap);
			s2.setPosition(points2[i].x, points2[i].y);
			enemys.add(s2);
		}
		for (int i = 0; i < points3.length; i++) {
			s3 = new Blocker(tileMap);
			s3.setPosition(points3[i].x, points3[i].y);
			enemys.add(s3);
		}
	}

	public void update() {
		handleInput();
		teleport.update();
		player.update();
		tileMap.setPosition(160 - player.getx(), 120 - player.gety());
		bg.setPosition(tileMap.getx(), tileMap.gety());
		if (player.getHealth() == 0 || player.getx() > tileMap.getWidth() || player.gety() > tileMap.getHeight())
			player.setDead(true);
		player.checkAttack(enemys);
		if (teleport.intersects(player)) {
			JukeBox.stop("level1");
			gsm.l5beat = true; // TODO FLAG
			gsm.setState(8);
		}

		if (titleStart == true) {
			title.begin();
			titleStart = false;
		}

		if (title != null) {
			title.update();
			if (title.shouldRemove())
				title = null;
		}

		for (int i = 0; i < enemys.size(); i++) {
			Enemy e = (Enemy) enemys.get(i);
			e.update();
			if (e.isDead()) {
				enemys.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			EntitySpecial e = (EntitySpecial) entities.get(i);
			e.update();
			if (e.isSpawned()) {
				entities.remove(i);
				i--;
			}
		}
		for (int i = 0; i < explosions.size(); i++) {
			((Explosion) explosions.get(i)).update();
			if (((Explosion) explosions.get(i)).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		for (int i = 0; i < enemys.size(); i++)
			((Enemy) enemys.get(i)).draw(g);
		teleport.draw(g);
		for (int i = 0; i < explosions.size(); i++) {
			((Explosion) explosions.get(i)).setMapPosition((int) tileMap.getx(), (int) tileMap.gety());
			((Explosion) explosions.get(i)).draw(g);
		}
		hud.draw(g);

		if (title != null)
			title.draw(g);
	}

	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(true);
		if (player.getHealth() == 0)
			return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setLeft(Keys.keyState[Keys.LEFT] || Keys.keyState[Keys.A]);
		player.setRight(Keys.keyState[Keys.RIGHT] || Keys.keyState[Keys.D]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		player.setGliding(Keys.keyState[Keys.BUTTON2]);
		if (Keys.isPressed(Keys.BUTTON4))
			player.setFiring();
		if (Keys.isPressed(Keys.BUTTON3))
			player.setScratching();
		if (Keys.isPressed(Keys.HOME))
			populateEntity();
	}

}
