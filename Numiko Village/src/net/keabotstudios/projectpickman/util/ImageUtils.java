package net.keabotstudios.projectpickman.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import net.keabotstudios.projectpickman.Game;
import net.keabotstudios.projectpickman.io.Logger;

public class ImageUtils {

	public static BufferedImage loadImageRaw(String file) {
		try {
			return ImageIO.read(Game.class.getResourceAsStream(file));
		} catch (Exception e) {
			Logger.error("Could not load " + file + "!");
		}
		return null;
	}

	public static BufferedImage[][] loadSpritesheetRaw(String file, Dimension d) {
		BufferedImage sheet = loadImageRaw(file);
		int width = d.width;
		int height = d.height;
		int numImgsX = sheet.getWidth() / width;
		int numImgsY = sheet.getHeight() / height;
		BufferedImage[][] out = new BufferedImage[numImgsY][numImgsX];
		for (int row = 0; row < numImgsY; row++) {
			for (int col = 0; col < numImgsX; col++) {
				out[row][col] = sheet.getSubimage(col * width, row * height, width, height);
			}
		}
		return out;
	}

	public static BufferedImage substitute(BufferedImage original, Color to) {
		BufferedImage out = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				Color c = new Color(original.getRGB(x, y), true);
				float brightness = ((float) (c.getRed() + c.getGreen() + c.getBlue()) / 3) / 255f;

				int alpha = (int) (c.getAlpha());
				int red = (int) (to.getRed() + (to.getRed() * brightness));
				int green = (int) (to.getGreen() + (to.getGreen() * brightness));
				int blue = (int) (to.getBlue() + (to.getBlue() * brightness));
				int rgba = (((alpha & 0x0ff) << 24) | (red & 0x0ff) << 16) | ((green & 0x0ff) << 8) | (blue & 0x0ff);

				out.setRGB(x, y, rgba);
			}
		}
		return out;
	}

}
