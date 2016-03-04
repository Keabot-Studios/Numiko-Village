package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.keabotstudios.projectpickman.io.Input.InputAxis;
import net.keabotstudios.projectpickman.loading.Textures;

public class HudButtonPrompt extends HudObject {
	
	private BufferedImage[] textures;

	public HudButtonPrompt(int x, int y, int size, InputAxis inputAxis, boolean hasContoller) {
		super(x, y, 8 * size, 8 * size);
		int texX = 0;
		switch(inputAxis) {
		case UP:
			texX = 0;
			break;
		case DOWN:
			texX = 1;
			break;
		case LEFT:
			texX = 2;
			break;
		case RIGHT:
			texX = 3;
			break;
		case ACTION1:
			texX = 4;
			break;
		case ACTION2:
			texX = 5;
			break;
		case ACTION3:
			texX = 6;
			break;
		default:
			texX = 6;
			break;
		}
		textures = new BufferedImage[1];
		if(hasContoller) {
			textures = new BufferedImage[2];
			textures[1] = Textures.getSpriteSheet("buttonPrompts")[1][texX];
		}
		textures[0] = Textures.getSpriteSheet("buttonPrompts")[0][texX];
	}

	public void update() {}

	public void render(Graphics2D g, int xOffset, int yOffset) {
		if(textures.length > 1) {
			if(System.currentTimeMillis() % 2000 < 1000) {
				g.drawImage(textures[0], xOffset + x, yOffset + y, width, height, null);
			} else {
				g.drawImage(textures[1], xOffset + x, yOffset + y, width, height, null);
			}
		} else {
			g.drawImage(textures[0], xOffset + x, yOffset + y, width, height, null);
		}
		
	}

}
