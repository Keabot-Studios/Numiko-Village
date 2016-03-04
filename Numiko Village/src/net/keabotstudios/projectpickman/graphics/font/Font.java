package net.keabotstudios.projectpickman.graphics.font;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import net.keabotstudios.projectpickman.io.Logger;
import net.keabotstudios.projectpickman.loading.Textures;
import net.keabotstudios.projectpickman.util.ImageUtils;

public enum Font {
	MAIN("main", new String[] { "ABCDGHJK", "MNOPQRST", "UVWXYZab", "cdghkmno", "pquvwxyz", "02356789", "*-<>~   ", "EFLefjrst", "4^?      ", "_-\"        ", "I1\\/          ", "il!()             ", ":;'.                        " }, new int[] { 7, 7, 7, 7, 7, 7, 7, 6, 6, 5, 4, 3, 2 }, 11, 7);

	private String texture;
	private String[] lines;
	private int[] widths;
	private int height, spaceWidth;
	private HashMap<Character, FontCharacter> characters;

	private Font(String texture, String[] lines, int[] widths, int height, int spaceWidth) {
		this.texture = texture;
		this.lines = lines;
		this.widths = widths;
		this.height = height;
		this.spaceWidth = spaceWidth;
		this.characters = new HashMap<Character, FontCharacter>();
	}

	public void loadFont() {
		BufferedImage fontSheet = Textures.getTexture(texture);

		int numRows = fontSheet.getHeight() / height;
		if (lines.length != numRows || widths.length != numRows) {
			Logger.error("Font sheet is wrong size, the list of characters is wrong, or character list is wrong. Double check them!");
			System.exit(-1);
		}

		for (int row = 0; row < numRows; row++) {
			int numCharsInRow = fontSheet.getWidth() / widths[row];
			for (int col = 0; col < numCharsInRow; col++) {
				char c = lines[row].charAt(col);
				if (c == ' ')
					continue;
				boolean[] graphic = new boolean[widths[row] * height];
				BufferedImage characterGraphic = fontSheet.getSubimage(row * widths[row], col * height, widths[row], height);
				for(int cgx = 0; cgx < widths[row]; cgx++) {
					for(int cgy = 0; cgy < widths[row]; cgy++) {
						int alpha = new Color(characterGraphic.getRGB(cgx, cgy), true).getAlpha();
						graphic[cgx + cgy * widths[row]] = alpha != 0;
					}
				}
				characters.put(c, new FontCharacter(c, widths[row], height, graphic));
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getSpaceWidth() {
		return spaceWidth;
	}
	
	public FontCharacter getCharacter(char c) {
		return characters.get(c);
	}
	
	public static void loadFonts() {
		for(Font f : Font.values()) {
			f.loadFont();
		}
	}

}
