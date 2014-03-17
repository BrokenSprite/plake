package com.plake.gamestate;

import java.awt.image.BufferedImage;

import com.plake.gamestate.world_one.Level1State;
import com.plake.gamestate.world_one.Level2State;
import com.plake.gamestate.world_one.Level3State;
import com.plake.gamestate.world_one.Level4State;
import com.plake.gamestate.world_one.Level5State;
import com.plake.gamestate.world_two.W2Level1State;

public class GameStateManager {

	@SuppressWarnings("unused")
	private BufferedImage image;

	private GameState[] gameStates;
	private int currentState;

	public static final int NUMGAMESTATES = 11;

	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int PAUSESTATE = 2;
	public static final int LEVELWIN = 3;
	public static final int LEVEL2STATE = 4;
	public static final int LEVEL3STATE = 5;
	public static final int LEVEL4STATE = 6;
	public static final int CHARSEL = 7;
	public static final int LEVELCOMP = 8;
	public static final int LEVEL5STATE = 9;
	public static final int W2_LEVEL1STATE = 10;

	public boolean l1beat, l2beat, l3beat, l4beat, l5beat;
	public boolean w2_l1beat;

	private PauseState pauseState;
	private CharacterSelect charSel;
	public boolean paused;
	public boolean charsel;

	public GameStateManager() {

		gameStates = new GameState[NUMGAMESTATES];
		pauseState = new PauseState(this);
		paused = false;

		currentState = MENUSTATE;
		loadState(currentState);

	}

	public void nextLevel() {
		LevelCompletedState.eDead = 0;
		LevelCompletedState.score = 0;

		if (l1beat) {
			l1beat = false;
			setState(4);
		} else if (l2beat) {
			l2beat = false;
			setState(5);
		} else if (l3beat) {
			l3beat = false;
			setState(6);
		} else if (l4beat) {
			l4beat = false;
			setState(9);
		} else if (l5beat) {
			l5beat = false;
			setState(10);
		} else if (w2_l1beat) {
			w2_l1beat = false;
			setState(0);
		}

	}

	public int getCurrentState() {
		return currentState;
	}

	private void loadState(int state) {
		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if (state == CHARSEL)
			gameStates[state] = new CharacterSelect(this);
		else if (state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		else if (state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		else if (state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
		else if (state == LEVEL4STATE)
			gameStates[state] = new Level4State(this);
		
		else if (state == LEVEL5STATE)
			gameStates[state] = new Level5State(this);

		else if (state == W2_LEVEL1STATE)
			gameStates[state] = new W2Level1State(this);

		else if (state == LEVELCOMP)
			gameStates[state] = new LevelCompletedState(this);

	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void setPaused(boolean b) {
		paused = b;
	}

	public void update() {

		if (paused) {
			pauseState.update();
			return;
		}
		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	public void draw(java.awt.Graphics2D g) {
		if (paused) {
			pauseState.draw(g);
			return;
		}

		if (charsel) {
			charSel.draw(g);
			return;
		}

		if (gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		} else {

		}

	}

}