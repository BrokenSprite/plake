package com.plake.utils;

import java.awt.event.KeyEvent;

//this class contains a boolean array of current and previous key states
//for the 10 keys that are used for this game.
//a key k is down when keyState[k] is true.

public class Keys {

	public static final int NUM_KEYS = 99;

	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int BUTTON1 = 4;
	public static int BUTTON2 = 5;
	public static int BUTTON3 = 6;
	public static int BUTTON4 = 7;
	public static int ENTER = 8;
	public static int ESCAPE = 9;
	public static int HOME = 10;
	public static int END = 11;
	public static int PGUP = 12;
	public static int PGDN = 13;
	public static int A = 14;
	public static int D = 15;

	public static void keySet(int i, boolean b) {
		if (i == KeyEvent.VK_UP) keyState[UP] = b;
		else if (i == KeyEvent.VK_LEFT) keyState[LEFT] = b;
		else if (i == KeyEvent.VK_DOWN) keyState[DOWN] = b;
		else if (i == KeyEvent.VK_RIGHT) keyState[RIGHT] = b;
		else if (i == KeyEvent.VK_W) keyState[BUTTON1] = b;
		else if (i == KeyEvent.VK_E) keyState[BUTTON2] = b;
		else if (i == KeyEvent.VK_R) keyState[BUTTON3] = b;
		else if (i == KeyEvent.VK_F) keyState[BUTTON4] = b;
		else if (i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
		else if (i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
		else if (i == KeyEvent.VK_HOME) keyState[HOME] = b;
		else if (i == KeyEvent.VK_END) keyState[END] = b;
		else if (i == KeyEvent.VK_PAGE_UP) keyState[PGUP] = b;
		else if (i == KeyEvent.VK_PAGE_DOWN) keyState[PGDN] = b;
		else if (i == KeyEvent.VK_A) keyState[A] = b;
		else if (i == KeyEvent.VK_D) keyState[D] = b;
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}

	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}

	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i]) return true;
		}
		return false;
	}

}
