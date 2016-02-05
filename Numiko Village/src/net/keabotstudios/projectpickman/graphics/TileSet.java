package net.keabotstudios.projectpickman.graphics;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.map.Tile;
import net.keabotstudios.projectpickman.map.Tile.TileType;

public class TileSet {

	private Tile[] tiles;
	private BufferedImage spritesheet;

	public TileSet(String tilesetImagePath, String tilesetCollisionsPath) {
		String delims = "\\s+";
		try {
			InputStream in = getClass().getResourceAsStream(tilesetCollisionsPath + ".tsc");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			int width = Integer.parseInt(br.readLine());
			int height = Integer.parseInt(br.readLine());
			TileType[] types = new TileType[width * height];

			for (int y = 0; y < height; y++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int x = 0; x < width; x++) {
					types[x + y * width] = TileType.values()[Integer.parseInt(tokens[x])];
				}
			}
			br.close();

			spritesheet = ImageIO.read(new FileInputStream(tilesetImagePath + ".png"));

			tiles = new Tile[width * height];

			for (int i = 0; i < tiles.length; i++) {
				int tx = (i % width) * References.TILE_SIZE;
				int ty = (i / height) * References.TILE_SIZE;
				tiles[i] = new Tile(types[i], spritesheet.getSubimage(tx, ty, References.TILE_SIZE, References.TILE_SIZE));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Tile getTile(int id) {
		return tiles[id];
	}
}
