package com.plake.utils;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Updater {

	private static String newVersion;
	private static String currentVersion;

	public static int update = 0;

	public static void checkForUpdate(boolean isAuto) {
		currentVersion = TextFile.readFile("./version.txt");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			URL site = new URL("https://raw.github.com/BrokenSprite/plake/master/Plake/version.txt");
			ReadableByteChannel rbc = Channels.newChannel(site.openStream());
			FileOutputStream fos = new FileOutputStream("./version.txt");
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		newVersion = TextFile.readFile("./version.txt");
		
		if (currentVersion.equals(newVersion)) {
			if (!isAuto)
				doNotUpdate();
			return;
		} else {
			Object[] options = { "Yes", "No" };
			int temp = JOptionPane.showOptionDialog(null, "An update has been found for Plake (current version: " +currentVersion+ ", new version" +newVersion+ "\nUpdate may take upto several minutes\nDo not close the game while updating!\nA window will popup when update is complete!", "Updater", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (temp == 1)
				return;
			else {
				TextFile.writeFile("./version.txt", newVersion);
				//TODO FLAG
				// Put all WIP Maps, Sprites, etc here
				try {
					URL site = new URL("https://raw.github.com/BrokenSprite/plake/master/Plake/res/Maps/w2_lvl1.map");
					ReadableByteChannel rbc = Channels.newChannel(site.openStream());
					FileOutputStream fos = new FileOutputStream("./res/Maps/w2_lvl1.map");
					fos.getChannel().transferFrom(rbc, 0, 1 << 24);
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
					updateFailed();
				}
				
				

				finishUpdate();
				return;
			}
		}

	}

	private static void finishUpdate() {
		JOptionPane.showMessageDialog(null, "Updated to " + newVersion + "\nGame will now close", "Update Completed", JOptionPane.INFORMATION_MESSAGE);

		System.exit(0);
	}

	private static void updateFailed() {
		JOptionPane.showMessageDialog(null, "Tried update to " + newVersion + "\nGame will now close", "Update Failed, Could not connect", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	private static void doNotUpdate() {
		JOptionPane.showMessageDialog(null, "No Update Found!", "Updater", JOptionPane.INFORMATION_MESSAGE);
		return;
	}
}
