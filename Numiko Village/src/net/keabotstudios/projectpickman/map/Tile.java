package net.keabotstudios.projectpickman.map;

import java.awt.image.BufferedImage;

public class Tile {
	
	public enum TileType {
		NORMAL, SOLID, PLATFORM
	}

	private TileType type;
	private BufferedImage texture;

	public Tile(TileType type, BufferedImage texture) {
		this.type = type;
		this.texture = texture;
	}

	public TileType getType() {
		return type;
	}
	
	public BufferedImage getTexture() {
		return texture;
	}

}
