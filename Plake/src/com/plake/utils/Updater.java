package com.plake.utils;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JOptionPane;

import com.plake.gamestate.MenuState;

public class Updater {

	private static String newVersion;
	private static String currentVersion;

	public static int update = 0;

	public static void checkForUpdate(boolean isAuto) {
		currentVersion = TextFile.readFile("./version.txt");
		try {
			URL site = new URL("https://raw.github.com/BrokenSprite/plake/master/Plake/version.txt");
			ReadableByteChannel rbc = Channels.newChannel(site.openStream());
			FileOutputStream fos = new FileOutputStream("./version.txt");
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		newVersion = TextFile.readFile("./version.txt");
		MenuState.version = currentVersion;
		if (currentVersion.equals(newVersion)) {
			if (!isAuto)
				doNotUpdate();
			return;
		} else {
			Object[] options = { "Yes", "No" };
			int temp = JOptionPane.showOptionDialog(null, "An update has been found for Plake, update?", "Updater", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (temp == 1)
				return;
			else {
				TextFile.writeFile("./version.txt", newVersion);

				try {
					URL site = new URL("https://raw.github.com/BrokenSprite/plake/master/Plake/res/Sprites/Player/Kat/playersprites.gif");
					ReadableByteChannel rbc = Channels.newChannel(site.openStream());
					FileOutputStream fos = new FileOutputStream("./Sprites/Player/Kat/playersprites.gif");
					fos.getChannel().transferFrom(rbc, 0, 1 << 24);
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				finishUpdate();
				return;
			}
		}

	}

	private static void finishUpdate() {
		JOptionPane.showMessageDialog(null, "Updated to " +newVersion+ "\nGame will now close", "Update Completed", JOptionPane.INFORMATION_MESSAGE);
		
		System.exit(0);
	}

	private static void doNotUpdate() {
		JOptionPane.showMessageDialog(null, "No Update Found!", "Updater", JOptionPane.INFORMATION_MESSAGE);
		return;
	}
}
