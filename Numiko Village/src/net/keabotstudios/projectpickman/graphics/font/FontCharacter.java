package net.keabotstudios.projectpickman.graphics.font;

import java.awt.Color;
import java.awt.Graphics2D;

public class FontCharacter {

	final char character;
	final int graphicWidth, graphicHeight;
	final boolean[] graphic;
	
	public FontCharacter(char character, int graphicWidth, int graphicHeight, boolean[] graphic) {
		this.character = character;
		this.graphicWidth = graphicWidth;
		this.graphicHeight = graphicHeight;
		this.graphic = graphic;
	}
	
	public char getChar() {
		return character;
	}
	
	public void render(Graphics2D g, int x, int y, int size, Color c) {
		g.setColor(c);
		for(int gx = 0; gx < graphicWidth; gx++) {
			for(int gy = 0; gy < graphicHeight; gy++) {
				if(graphic[gx + gy * graphicWidth])
					g.drawLine(x + gx, y + gy, x + gx, y + gy);
			}
		}
	}

	public char getCharacter() {
		return character;
	}

	public int getWidth() {
		return graphicWidth;
	}

	public int getHeight() {
		return graphicHeight;
	}

}
