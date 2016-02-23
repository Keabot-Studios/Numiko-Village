package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Graphics2D;

public abstract class HudObject {
	
	protected int x, y;
	protected int width, height;
	
	protected HudObject(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}

	public abstract void update();
	public abstract void render(Graphics2D g);

}
