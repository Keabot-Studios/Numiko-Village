package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.keabotstudios.projectpickman.io.Logger;

public class HudSet {

	private int x, y, tx, ty, dx, dy;
	private long time;
	private boolean moving;
	private HashMap<String, Integer> componentIndexes;
	private ArrayList<HudObject> components;

	public HudSet(int x, int y) {
		this.components = new ArrayList<HudObject>();
		this.componentIndexes = new HashMap<String, Integer>();
		this.x = x;
		this.y = y;
	}

	public void moveTo(int tx, int ty, float time) {
		this.tx = tx;
		this.ty = ty;
	}

	public void add(String name, HudObject component) {
		if(componentIndexes.containsKey(name) || components.contains(component)) {
			Logger.error("HudObject and/or Name already exists in this HudSet! Name: " + name + " HudObject: " + component.toString());
			return;
		}
		components.add(component);
		componentIndexes.put(name, components.indexOf(component));
	}

	public void update() {
		for (HudObject o : components) {
			o.update();
		}
	}

	public void render(Graphics2D g) {
		for (HudObject o : components) {
			o.render(g, x, y);
		}
	}

	public HudObject get(String name) {
		return components.get(componentIndexes.get(name));
	}
	
	public void remove(String name) {
		components.remove(componentIndexes.get(name));
		componentIndexes.remove(name);
	}

}
