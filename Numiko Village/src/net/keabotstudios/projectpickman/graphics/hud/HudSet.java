package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Graphics2D;
import java.util.HashMap;

public class HudSet {

	private int x, y, tx, ty, dx, dy;
	private long time;
	private boolean moving;
	private HashMap<String, HudObject> components;

	public HudSet(int x, int y) {
		this.components = new HashMap<String, HudObject>();
		this.x = x;
		this.y = y;
	}

	public void moveTo(int tx, int ty, float time) {
		this.tx = tx;
		this.ty = ty;
	}

	public void add(String name, HudObject component) {
		components.put(name, component);
	}

	public void update() {
		for (HudObject o : components.values()) {
			o.update();
		}
	}

	public void render(Graphics2D g) {
		for (HudObject o : components.values()) {
			o.render(g, x, y);
		}
	}

	public HudObject get(String name) {
		return components.get(name);
	}

}
