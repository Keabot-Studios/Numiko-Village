package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.keabotstudios.projectpickman.loading.Textures;
import net.keabotstudios.projectpickman.util.ImageUtils;

public class HudBar extends HudObject {

	private int value, maxValue;
	private Color color;

	private BufferedImage[] barFrame;
	private BufferedImage barPiece;
	private BufferedImage bar;

	public HudBar(int x, int y, int width, int maxValue, int value, Color color) {
		super(x, y, width, 6);
		this.maxValue = maxValue;
		if (value > maxValue)
			value = maxValue;
		if (value < 0)
			value = 0;
		this.value = value;
		this.color = color;
		this.barPiece = ImageUtils.substitute(Textures.getTexture("bar"), color);
		this.bar = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.barFrame = Textures.getSpriteSheet("bar_border")[0];
		calculateBar();
	}

	public void update() {
		calculateBar();
	}

	public int getValue() {
		return value;
	}

	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(barFrame[0], xOffset + x - 1, yOffset + y - 1, null);
		for (int i = 0; i < width; i++) {
			g.drawImage(barFrame[1], xOffset + x + i, yOffset + y - 1, null);
		}
		g.drawImage(barFrame[2], xOffset + x + width, yOffset + y - 1, null);

		int barWidth = (int) (bar.getWidth() * ((float) value / (float) maxValue));
		if (barWidth != 0)
			g.drawImage(bar.getSubimage(0, 0, barWidth, bar.getHeight()), xOffset + x, yOffset + y, null);
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public void setValue(int value) {
		if (value > maxValue)
			value = maxValue;
		if (value < 0)
			value = 0;
		this.value = value;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	int lastOffset = 0;

	private void calculateBar() {
		int offset = (int) (System.currentTimeMillis() / 100) % barPiece.getWidth() - barPiece.getWidth();
		if (offset == lastOffset)
			return;
		bar = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bar.getGraphics();
		for (int i = 0; i < width + barPiece.getWidth(); i++) {
			BufferedImage img = barPiece.getSubimage(i % barPiece.getWidth(), 0, 1, barPiece.getHeight());
			g.drawImage(img, i + offset, 0, null);
		}
		g.dispose();
		lastOffset = offset;
	}

}
