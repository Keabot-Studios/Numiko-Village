package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.GameInfo;
import net.keabotstudios.projectpickman.graphics.text.font.Font;
import net.keabotstudios.projectpickman.loading.Textures;

public class HudDialougeBox extends HudObject {
	
	private final double size;
	private BufferedImage box;
	private BufferedImage faceTexture;
	
	private String text;
	private Font font;
	private HashMap<Character, BufferedImage> fontChars;
	private Color color;
	
	private	char[] showingChars;
	private int charDelay = 1, charTimer = 0, nextCharIndex = 0;
	private boolean done = false, hasText = false, shaking = false;
	private static final int IDLE = 0, ENTERING = 1, EXITING = 2;
	private int movingStatus = IDLE;
	
	private double dy = 0;
	private int ty = 0;

	public HudDialougeBox() {
		super(0, References.HEIGHT, References.WIDTH, 0);
		box = Textures.getTexture("dialougeBox");
		size = References.WIDTH / box.getWidth();
		this.height = (int) (box.getHeight() * size);
		this.y -= height;
		if(GameInfo.dialougeBoxOnTop) {
			this.y = 0;
		}
	}
	
	public void changeText(String text, int charDelay) {
		if(text == null || text.isEmpty()) {
			this.done = true;
			this.hasText = false;
		}
		this.showingChars = new char[text.length()];
		this.hasText = true;
		this.done = false;
		this.text = text;
		this.charTimer = 0;
		if(charDelay < 1) charDelay = 1;
		this.charDelay = charDelay;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setFace(String text) {
		this.text = text;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public void update() {
		if(text == null) return;
		if(showingChars != text.toCharArray() && !done) {
			charTimer++;
			if(charTimer == charDelay) {
				showingChars[nextCharIndex] = text.charAt(nextCharIndex);
				nextCharIndex++;
				charTimer = 0;
			}
		} else {
			done = true;
			charTimer = 0;
			nextCharIndex = 0;
		}
	}

	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(box, x, y, width, height, null);
		//DRAW FACE
		if(hasText) {
			
		}
	}
	
	public void show() {
		
	}
	
	public void hide() {
		
	}
	
	public boolean showing() {
		return movingStatus == IDLE;
	}

}
