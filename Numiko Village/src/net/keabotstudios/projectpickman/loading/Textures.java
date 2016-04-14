package net.keabotstudios.projectpickman.loading;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import net.keabotstudios.projectpickman.io.Paths;
import net.keabotstudios.projectpickman.io.console.Logger;
import net.keabotstudios.projectpickman.util.ImageUtils;

public class Textures {

	// ICONS
	public static final BufferedImage[] icons = new BufferedImage[] { ImageUtils.loadImageRaw(Paths.ICONS + "/icon16.png"), ImageUtils.loadImageRaw(Paths.ICONS + "/icon32.png"), ImageUtils.loadImageRaw(Paths.ICONS + "/icon64.png") };
	public static final BufferedImage noTex = ImageUtils.loadImageRaw(Paths.TEXTURES + "/notex.png");

	private static HashMap<String, BufferedImage> textures;
	private static HashMap<String, BufferedImage[][]> spriteSheets;

	public static void init() {
		textures = new HashMap<String, BufferedImage>();
		spriteSheets = new HashMap<String, BufferedImage[][]>();
		loadTextures();
	}

	private static void loadImage(String path, String name) {
		BufferedImage i = ImageUtils.loadImageRaw(path);
		textures.put(name, i);
		texturesLoaded++;
	}

	private static void loadSpritesheet(String path, String name, Dimension d) {
		BufferedImage[][] ss = ImageUtils.loadSpritesheetRaw(path, d);
		spriteSheets.put(name, ss);
		texturesLoaded++;
	}

	private enum TextureType {
		GUI("gui", Paths.GUI), SPRITE("sprite", Paths.SPRITES), TILESET("tileset", Paths.TILESETS), BACKGROUND("background", Paths.BACKGROUNDS), FONT("font", Paths.FONTS);

		public final String identifier;
		public final String path;

		private TextureType(String identifier, String path) {
			this.identifier = identifier;
			this.path = path;
		}
	}

	private static int amountOfTextures = 0;
	private static int texturesLoaded = 0;

	public static void loadTextures() {
		loadTexturesFromFile("/loadTextures.txt");
	}

	private static void loadTexturesFromFile(String f) {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<TextureType> types = new ArrayList<TextureType>();
		ArrayList<Boolean> isSpritesheet = new ArrayList<Boolean>();
		ArrayList<Dimension> spriteSizes = new ArrayList<Dimension>();
		try {
			Scanner scanner = new Scanner(Textures.class.getResourceAsStream(f));
			TextureType type = null;
			Boolean spritesheetFlag = false;
			Dimension spriteSize = null;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineParts = line.split(",");
				if (line.startsWith("-")) {
					for (TextureType t : TextureType.values()) {
						if (line.equalsIgnoreCase("-" + t.identifier + "-")) {
							type = t;
							break;
						}
					}
					continue;
				}
				if (lineParts.length == 3) {
					spritesheetFlag = true;
					int sWidth = Integer.parseInt(lineParts[1]);
					int sHeight = Integer.parseInt(lineParts[2]);
					spriteSize = new Dimension(sWidth, sHeight);
				} else {
					spritesheetFlag = false;
					spriteSize = new Dimension(0, 0);
				}
				if (spritesheetFlag)
					names.add(lineParts[0]);
				else
					names.add(line);
				types.add(type);
				isSpritesheet.add(spritesheetFlag);
				spriteSizes.add(spriteSize);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		amountOfTextures = names.size();
		for (int i = 0; i < amountOfTextures; i++) {
			String name = names.get(i);
			Dimension spriteSize = spriteSizes.get(i);
			if (isSpritesheet.get(i)) {
				loadSpritesheet(types.get(i).path + "/" + name + ".png", name, spriteSize);
			} else {
				loadImage(types.get(i).path + "/" + name + ".png", name);
			}
		}
	}

	public static float getPercentageLoaded() {
		if (amountOfTextures == 0)
			return 1.0f;
		if (texturesLoaded == 0)
			return 0.0f;
		return (float) texturesLoaded / (float) amountOfTextures;
	}

	public static BufferedImage getTexture(String string) {
		if (!textures.containsKey(string)) {
			Logger.error("Texture \"" + string + "\" is not loaded or does not exist!");
			return noTex;
		}
		return textures.get(string);
	}

	public static BufferedImage[][] getSpriteSheet(String string) {
		if (!spriteSheets.containsKey(string)) {
			Logger.error("Spritesheet \"" + string + "\" is not loaded or does not exist!");
			return new BufferedImage[][] { new BufferedImage[] { noTex, noTex, noTex }, new BufferedImage[] { noTex, noTex, noTex }, new BufferedImage[] { noTex, noTex, noTex } };
		}
		return spriteSheets.get(string);
	}

}
