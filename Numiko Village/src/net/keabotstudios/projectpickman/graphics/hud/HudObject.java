package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.io.console.Logger;

public abstract class HudObject {

	protected int x, y;
	protected int width, height;
	protected boolean hidden = false;

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
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public abstract void update();

	public abstract void render(Graphics2D g, int xOffset, int yOffset);

	public void hide() {
		Logger.debug("HIDDEN: " + this.getClass().getSimpleName());
		hidden = true;
	}
	
	public void unhide() {
		hidden = false;
	}
	
	public boolean isHidden() {
		return hidden;
	}

}
