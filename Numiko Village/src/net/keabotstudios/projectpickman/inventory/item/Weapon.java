package net.keabotstudios.projectpickman.inventory.item;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.keabotstudios.projectpickman.entity.Player;
import net.keabotstudios.projectpickman.graphics.Animation;
import net.keabotstudios.projectpickman.loading.Textures;
import net.keabotstudios.projectpickman.map.Level;

public abstract class Weapon extends Item {

	public Weapon(String name, String description, int texIndex) {
		super(name, description, new Animation());
		int texRow = texIndex / Textures.getSpriteSheet("weapons").length;
		int texCol = texIndex % Textures.getSpriteSheet("weapons")[texRow].length;
		this.texture.setFrames(new BufferedImage[]{Textures.getSpriteSheet("weapons")[texRow][texCol]});
		this.texture.setDelay(-1);
	}

	public void update() {
		texture.update();
	}

	public void render(Graphics2D g, int x, int y, int size) {
		g.drawImage(this.texture.getImage(), x, y, this.texture.getImage().getWidth() * size, this.texture.getImage().getHeight() * size, null);
	}
	
	public abstract void activate(Player player, Level level);

}
