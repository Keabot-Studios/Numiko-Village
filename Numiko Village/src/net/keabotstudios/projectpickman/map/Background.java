package net.keabotstudios.projectpickman.map;

import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.loading.Textures;

public class Background {

	private String texture;
	private int xOffset, yOffset;

	public Background(String texture) {
		this.texture = texture;
	}

	public void render(Graphics2D g) {
		g.drawImage(Textures.getTexture(texture), xOffset - References.WIDTH, yOffset, References.WIDTH, References.HEIGHT, null);
		g.drawImage(Textures.getTexture(texture), xOffset, yOffset, References.WIDTH, References.HEIGHT, null);
		g.drawImage(Textures.getTexture(texture), xOffset + References.WIDTH, yOffset, References.WIDTH, References.HEIGHT, null);
		g.drawImage(Textures.getTexture(texture), xOffset, yOffset - References.HEIGHT, References.WIDTH, References.HEIGHT, null);
		g.drawImage(Textures.getTexture(texture), xOffset, yOffset + References.HEIGHT, References.WIDTH, References.HEIGHT, null);
	}

	public void setOffset(int x, int y) {
		this.xOffset = x % References.WIDTH;
		this.yOffset = y % References.HEIGHT;
	}

}
