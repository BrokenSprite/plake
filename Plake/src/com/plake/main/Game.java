package com.plake.main;

import java.awt.Toolkit;

import javax.swing.JFrame;

import com.plake.utils.TextFile;
import com.plake.utils.Updater;

public class Game {

	public static void main(String[] args) {//FUCKING GAY ASS BULLSHIP
		JFrame window = new JFrame("Plake : Beta");
		Updater.checkForUpdate(false);
		window.setIconImage(Toolkit.getDefaultToolkit().getImage(Game.class.getResource("/Sprites/Menu/icon.png")));
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}
}
