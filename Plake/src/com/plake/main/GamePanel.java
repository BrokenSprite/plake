package com.plake.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.plake.gamestate.GameStateManager;
import com.plake.utils.Keys;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = Game.width;// 320
	public static final int HEIGHT = Game.height;// 240
	public static final int SCALE = Game.scale;

	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	private BufferedImage image;
	private Graphics2D g;

	private GameStateManager gsm; // Game state manager

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();

	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	public void init() {

		System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		
		
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;

		gsm = new GameStateManager();

	}

	public void run() {
		init();
		long start;
		long elapsed;
		long wait;
		while (running) {
			start = System.nanoTime();
			update();
			draw();
			drawToScreen();
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;
			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		gsm.update();
		Keys.update();
	}

	public void draw() {
		gsm.draw(g);
	}

	public void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key) {
	}

	public void keyPressed(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);

	}

}
