package net.keabotstudios.projectpickman.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.keabotstudios.projectpickman.GameInfo;
import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.graphics.text.ChangeColorCommand;
import net.keabotstudios.projectpickman.graphics.text.WaitCommand;
import net.keabotstudios.projectpickman.graphics.text.RichText;
import net.keabotstudios.projectpickman.graphics.text.ShakeCommand;
import net.keabotstudios.projectpickman.graphics.text.TextCommand;
import net.keabotstudios.projectpickman.graphics.text.font.Font;
import net.keabotstudios.projectpickman.graphics.text.font.FontCharacter;
import net.keabotstudios.projectpickman.io.console.Logger;
import net.keabotstudios.projectpickman.loading.Textures;
import net.keabotstudios.projectpickman.util.ArrayUtils;

public class HudDialougeBox extends HudObject {

	private final double size;
	private BufferedImage box;
	private BufferedImage faceTexture;

	private RichText text;
	private Font font;

	private boolean[] showing;
	private boolean[] shaking;
	private int[] shakeDists;
	private float[] shakeProbs;
	private int[] colors;
	private int[] newLines;
	private int[] delays;
	private int charTimer = 0, nextCharIndex = 0;
	private boolean done = false, hasText = false;
	private static final int IDLE = 0, ENTERING = 1, EXITING = 2;
	private int movingStatus = IDLE;

	private double dy = 0;
	private int ty = 0;

	public HudDialougeBox(Font font) {
		super(0, References.HEIGHT, References.WIDTH, 0);
		box = Textures.getTexture("dialougeBox");
		size = References.WIDTH / box.getWidth();
		this.height = (int) (box.getHeight() * size);
		this.y -= height;
		if (GameInfo.dialougeBoxOnTop) {
			this.y = 0;
		}
		this.faceTexture = Textures.noTex;
		this.font = font;
	}

	public void changeText(RichText text) {
		if (text == null || text.isEmpty()) {
			this.done = true;
			this.hasText = false;
			this.showing = null;
			this.charTimer = 0;
			this.shaking = null;
			this.shakeDists = null;
			this.shakeProbs = null;
			this.colors = null;
			this.newLines = null;
			return;
		}
		this.text = text;
		this.showing = new boolean[text.getLength()];
		this.hasText = true;
		this.done = false;

		this.charTimer = 0;

		this.shaking = new boolean[text.getLength()];
		this.shakeDists = new int[text.getLength()];
		this.shakeProbs = new float[text.getLength()];
		this.colors = new int[text.getLength()];
		this.delays = new int[text.getLength()];
		parseTextCommands();
	}

	private void parseTextCommands() {
		TextCommand[] commands = this.text.getCommands();

		int currColorId = 0;
		boolean currShaking = false;
		int currShakeDist = 0;
		float currShakeProb = 0.0f;
		ArrayList<Integer> newLines = new ArrayList<Integer>();

		for (int i = 0; i < this.text.getLength(); i++) {
			for (int ci = 0; ci < commands.length; ci++) {
				TextCommand command = commands[ci];
				if (command.getIndex() == i) {
					switch (command.getType()) {
					case COLOR:
						currColorId = ((ChangeColorCommand) command).getColorId();
						break;
					case FACE:
						// TODO FACES
						break;
					case NEW_LINE:
						if (!newLines.contains(i))
							newLines.add(i);
						break;
					case NULL_FACE:
						this.faceTexture = Textures.noTex;
						break;
					case PLAYER_NAME:
						break;
					case SHAKE:
						currShaking = true;
						currShakeDist = ((ShakeCommand) command).getShakeDistance();
						currShakeProb = ((ShakeCommand) command).getShakeProbability();
						break;
					case STOP_SHAKE:
						currShaking = false;
						currShakeDist = 0;
						currShakeProb = 0.0f;
						break;
					case WAIT:
						this.delays[i] = ((WaitCommand) command).getDelay();
						break;
					default:
						break;
					}
				}
			}
			this.shaking[i] = currShaking;
			this.shakeDists[i] = currShakeDist;
			this.shakeProbs[i] = currShakeProb;
			this.colors[i] = currColorId;
			if (this.delays[i] == 0)
				this.delays[i] = References.DEFAULT_TEXT_DELAY;
		}
		this.newLines = ArrayUtils.toIntArray(newLines);
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public boolean isDone() {
		return done;
	}

	public void update() {
		if (text == null)
			return;
		if (nextCharIndex != text.getLength() - 1 && !done) {
			charTimer++;
			if (charTimer == delays[nextCharIndex]) {
				showing[nextCharIndex] = true;
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
		if(hidden) return;
		g.drawImage(box, x, y, width, height, null);
		g.drawImage(faceTexture, x + (int) (3.0 * size), y + (int) (3.0 * size), (int) (16.0 * size), (int) (16.0 * size), null);
		int newLineIndex = 0;
		if (hasText) {
			int cx = x + (int) (20.0 * size);
			int cy = y + (int) (3.0 * size);
			for (int i = 0; i < text.getLength(); i++) {
				if (showing[i]) {
					FontCharacter character = font.getCharacter(text.getText().charAt(i));
					if(text.getText().charAt(i) == ' ') {
						cx += font.getSpaceWidth() * 2;
						continue;
					}
					if(character == null) continue;
					if (newLines.length != 0 && newLineIndex < newLines.length) {
						if (i == newLines[newLineIndex]) {
							newLineIndex++;
							cy += font.getHeight() * 2 + 2;
							cx = x + (int) (20.0 * size);
						}
					}
					character.render(g, cx, cy, 2, References.DEFAULT_DIALOUGE_COLORS[colors[i]]);
					cx += (character.getWidth() + 1) * 2;
				}
			}
		}
	}

	public boolean showing() {
		return movingStatus == IDLE;
	}

}
