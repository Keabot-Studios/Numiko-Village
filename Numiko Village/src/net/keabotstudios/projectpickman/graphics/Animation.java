package net.keabotstudios.projectpickman.graphics;

import java.awt.image.BufferedImage;

public class Animation {

	private Texture[] frames;
	private int currentFrame;
	private int numFrames;

	private int count;
	private int delay;

	private int timesPlayed;

	private boolean playOnce;

	public Animation() {
		timesPlayed = 0;
	}

	public void setFrames(Texture[] frames) {
		this.frames = frames;
		currentFrame = 0;
		count = 0;
		timesPlayed = 0;
		numFrames = frames.length;
	}

	public void setFrames(BufferedImage[] frames) {
		this.frames = new Texture[frames.length];
		for (int i = 0; i < frames.length; i++) {
			this.frames[i] = new Texture(frames[i]);
		}

		currentFrame = 0;
		count = 0;
		timesPlayed = 0;
		numFrames = frames.length;
	}

	public void setDelay(int i) {
		delay = i;
	}

	public void setFrame(int i) {
		currentFrame = i;
	}

	public void setNumFrames(int i) {
		numFrames = i;
	}

	public void setPlaysOnce(boolean playsOnce) {
		this.playOnce = playsOnce;
	}

	public void update() {
		if (delay == -1)
			return;
		count++;
		if (count == delay) {
			currentFrame++;
			count = 0;
		}
		if (currentFrame == frames.length) {
			if (!playOnce) {
				currentFrame = 0;
				timesPlayed++;
			} else {
				currentFrame = frames.length - 1;
				timesPlayed = 0;
				count = 0;
			}
		}
	}

	public int getFrame() {
		return currentFrame;
	}

	public int getCount() {
		return count;
	}

	public int getNumFrames() {
		return numFrames;
	}

	public void bind() {
		frames[currentFrame].bind();
		;
	}

	public void unbind() {
		frames[currentFrame].unbind();
		;
	}

	public boolean hasPlayedOnce() {
		return timesPlayed > 0;
	}

	public boolean hasPlayed(int i) {
		return timesPlayed == i;
	}

	public int getDelay() {
		return delay;

	}

}
