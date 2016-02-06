package net.keabotstudios.projectpickman.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

	public static String loadAsString(String file) {
		StringBuilder result = new StringBuilder();
		try {
			InputStream in = FileUtils.class.getResourceAsStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer + "\n");
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
