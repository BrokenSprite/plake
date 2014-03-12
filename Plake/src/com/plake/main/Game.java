package com.plake.main;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class Game {

	public static String version = "1.3.2";
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Plake : Beta");
		window.setIconImage(Toolkit.getDefaultToolkit().getImage(Game.class.getResource("/Sprites/Menu/icon.png")));
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}
}
