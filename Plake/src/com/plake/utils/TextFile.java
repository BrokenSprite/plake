package com.plake.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFile {

	private static String line;

	public static String readFile(String path) {
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader(path));
			line = file.readLine();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return line;

	}

	public static void writeFile(String path, String text) {		
		try {
			FileWriter file = new FileWriter(path);
			file.write(text);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
