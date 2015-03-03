package com.plake.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.reflect.Method;

import javax.swing.JFrame;

public class Game {

	public static int width = 400; // 400
	public static int height = 240; // 240
	public static int scale = 1; // In theory this scales the resolution up to
									// 800x440

	public static String version = "1.4.0";

	public static void main(String[] args) {
		getResolution();
		JFrame window = new JFrame("Plake : Beta");

		if (isMacOSX()) {
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Plake");
			enableFullScreenMode(null);
		}

		window.setIconImage(Toolkit.getDefaultToolkit().getImage(Game.class.getResource("/Sprites/Menu/icon.png")));
		window.add(new GamePanel());
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);

	}

	public static void enableFullScreenMode(Window window) {
		String className = "com.apple.eawt.FullScreenUtilities";
		String methodName = "setWindowCanFullScreen";

		try {
			Class<?> clazz = Class.forName(className);
			Method method = clazz.getMethod(methodName, new Class<?>[] { Window.class, boolean.class });
			method.invoke(null, window, true);
		} catch (Throwable t) {
			System.err.println("Full screen mode is not supported");
			t.printStackTrace();
		}
	}

	public static void getResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
		
		//TODO Add aspect ratio detection
		if (height < 1366) {
			
		}
	}

	private static boolean isMacOSX() {
		return System.getProperty("os.name").indexOf("Mac OS X") >= 0;
	}

}
