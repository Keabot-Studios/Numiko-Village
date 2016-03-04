package net.keabotstudios.projectpickman.inventory.item;

import java.awt.Graphics2D;

import net.keabotstudios.projectpickman.graphics.Animation;

public abstract class Item {
	
	protected String name, description;
	protected Animation texture;
	
	public Item(String name, String description, Animation texture) {
		this.name = name;
		this.description = description;
		this.texture = texture;
	}

	public abstract void update();
	public abstract void render(Graphics2D g, int x, int y, int size);

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
