package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.graphics.text.font.Font;
import net.keabotstudios.projectpickman.graphics.text.font.FontCharacter;

public class HudText extends HudObject {

	private String text;
	private Font font;
	private Color color;
	private int size;

	public HudText(int x, int y, String text, Font font, Color color, int size) {
		super(x, y, 0, 0);
		this.text = text;
		this.font = font;
		this.color = color;
		this.size = size;
		reloadText();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		reloadText();
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		reloadText();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		reloadText();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
		reloadText();
	}

	private void reloadText() {
		int textWidth = 0;
		int textHeight = 0;
		String[] lines = text.split("\n");
		for (int l = 0; l < lines.length; l++) {
			int lineWidth = -1;
			char[] line = lines[l].toCharArray();
			for (int ci = 0; ci < line.length; ci++) {
				char c = line[ci];
				FontCharacter fc = font.getCharacter(c);
				if(c != ' ') {
					lineWidth += (fc.getWidth() + 1) * size;
				} else {
					lineWidth += font.getSpaceWidth() * size;
				}
			}
			if (lineWidth > textWidth) {
				textWidth = lineWidth;
			}
			textHeight += font.getHeight() * size;
		}

		this.width = textWidth;
		this.height = textHeight;
	}

	public void update() {
	}

	public void render(Graphics2D g, int xOffset, int yOffset) {
		int charY = 0;
		String[] lines = text.split("\n");
		for (int l = 0; l < lines.length; l++) {
			int charX = 0;
			char[] line = lines[l].toCharArray();
			for (int c = 0; c < line.length; c++) {
				FontCharacter fc = font.getCharacter(line[c]);
				if (fc.getChar() != ' ') {
					fc.render(g, x + charX + xOffset, y + charY + yOffset, size, color);
					charX += (fc.getWidth() + 1) * size;
				} else {
					charX += font.getSpaceWidth() * size;
				}

			}
			charY += font.getHeight() * size;
		}
	}

}
