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
import com.plake.entity.enemies.Crawler;
import com.plake.entity.enemies.Slugger;
import com.plake.gamestate.GameState;
import com.plake.gamestate.GameStateManager;
import com.plake.main.GamePanel;
import com.plake.tilemap.Background;
import com.plake.tilemap.TileMap;
import com.plake.utils.Keys;

public class Level1State extends GameState {

	private TileMap tileMap;
	private Background bg;

	private Player player;

	private ArrayList<Enemy> enemys;
	private ArrayList<EntitySpecial> entities;
	private ArrayList<Explosion> explosions;

	private HUD hud;

	private boolean titleStart;

	private Teleport teleport;
	private Title title;

	private BufferedImage hageonText;

	public Level1State(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;
		gsm.setPaused(false);
		init();
	}

	public void init() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tutorialtileset.gif");
		tileMap.loadMap("/Maps/w1_lvl1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);

		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

		player = new Player(tileMap);
		player.setPosition(100, 100);

		teleport = new Teleport(tileMap);
		teleport.setPosition(3065, 190);

		populateEntity();

		explosions = new ArrayList<Explosion>();

		hud = new HUD(player);

		// TITLE

		try {
			titleStart = true;
			hageonText = ImageIO.read(getClass().getResourceAsStream("/Title/level1.png"));
			title = new Title(hageonText.getSubimage(0, 0, 92, 40));
			title.sety(60);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JukeBox.load("/Music/level1-1.mp3", "level1");
		//JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);

		System.out.println("Level: Level1State (Tutorial Level) Initialized!");
	}

	private void populateEntity() {
		enemys = new ArrayList<Enemy>();
		entities = new ArrayList<EntitySpecial>();
		Slugger s;
		Crawler s2;
		Point[] points = new Point[] { new Point(860, 200), new Point(1525, 200), new Point(1680, 200), new Point(1800, 200) };
		Point[] points2 = new Point[] { new Point(960, 200) };
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

	}

	/*
	 * private void nextLevel() {
	 * 
	 * }
	 */

	public void update() {
		handleInput();
		teleport.update();
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
		bg.setPosition(tileMap.getx(), tileMap.gety());

		if (player.getHealth() == 0 || player.getx() > tileMap.getWidth() || player.gety() > tileMap.getHeight()) {
			player.setDead(true);
		}

		// TITLE

		if (titleStart == true) {
			title.begin();
			titleStart = false;
		}

		if (title != null) {
			title.update();
			if (title.shouldRemove())
				title = null;
		}

		// player attacking
		player.checkAttack(enemys);

		if (teleport.intersects(player)) {
			//JukeBox.stop("level1");
			gsm.l1beat = true;
			gsm.setState(8);
			System.out.println("Level: Level1State (Tutorial Level) Completed!");
		}

		// update all enemies
		for (int i = 0; i < enemys.size(); i++) {
			Enemy e = enemys.get(i);
			e.update();
			if (e.isDead()) {
				enemys.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			EntitySpecial e = entities.get(i);
			e.update();
			if (e.isSpawned()) {
				entities.remove(i);
				i--;
			}
		}
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
	}

	public void draw(Graphics2D g) {
		bg.draw(g);

		tileMap.draw(g);

		player.draw(g);

		// //teleport.draw(g);

		// draw ememies
		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).draw(g);
		}

		teleport.draw(g);

		// draw Explostion
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int) tileMap.getx(), (int) tileMap.gety());
			explosions.get(i).draw(g);
		}

		// HUD DRAW
		hud.draw(g);

		// DRAW TITLE

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
	}

}
