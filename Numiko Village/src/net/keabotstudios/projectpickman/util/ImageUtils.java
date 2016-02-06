package net.keabotstudios.projectpickman.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.keabotstudios.projectpickman.Game;

public class ImageUtils {

	public static BufferedImage loadImage(String file) {
		try {
			return ImageIO.read(Game.class.getResourceAsStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage[][] loadSpritesheet(String file, int width, int height) {
		BufferedImage sheet = loadImage(file);
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

}
