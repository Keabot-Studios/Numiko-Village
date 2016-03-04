package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class HudImage extends HudObject {

	private BufferedImage texture;

	public HudImage(int x, int y, int width, int height, BufferedImage texture) {
		super(x, y, width, height);
		this.texture = texture;
	}

	public HudImage(int x, int y, int size, BufferedImage texture) {
		this(x, y, texture.getWidth() * size, texture.getHeight() * size, texture);
	}

	public void update() {}

	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(texture, xOffset + x, yOffset + y, width, height, null);
	}

}
