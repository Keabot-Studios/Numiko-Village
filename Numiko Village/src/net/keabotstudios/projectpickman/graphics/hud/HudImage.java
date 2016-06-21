package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.keabotstudios.projectpickman.graphics.Animation;

public class HudImage extends HudObject {

	private Animation image;

	public HudImage(int x, int y, int width, int height, BufferedImage texture) {
		super(x, y, width, height);
		this.image = new Animation();
		image.setFrames(new BufferedImage[]{texture});
		image.setDelay(-1);
	}

	public HudImage(int x, int y, int size, BufferedImage texture) {
		this(x, y, texture.getWidth() * size, texture.getHeight() * size, texture);
	}
	
	public HudImage(int x, int y, int width, int height, Animation image) {
		super(x, y, width, height);
		this.image = image;
	}

	public HudImage(int x, int y, int size, Animation image) {
		this(x, y, image.getImage().getWidth() * size, image.getImage().getHeight() * size, image);
	}
	
	public void update() {
		image.update();
	}

	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(image.getImage(), xOffset + x, yOffset + y, width, height, null);
	}

}
