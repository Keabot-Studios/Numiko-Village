package net.keabotstudios.projectpickman.loading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import net.keabotstudios.projectpickman.io.Logger;
import net.keabotstudios.projectpickman.io.Path;

public class Sound {

	private static HashMap<String, Clip> clips;
	private static int gap;
	private static boolean mute = false;

	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
		loadSound();
	}

	public static void load(String path, String name) {
		if (clips.get(name) != null)
			return;
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class
					.getResourceAsStream(path));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			clips.put(name, clip);
			soundLoaded++;
			Logger.info("Loading sound: " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void play(String s) {
		play(s, gap);
	}

	public static void play(String s, int i) {
		if (mute)
			return;
		Clip c = clips.get(s);
		if (c == null)
			return;
		if (c.isRunning())
			c.stop();
		c.setFramePosition(i);
		while (!c.isRunning())
			c.start();
	}

	public static void play(String s, int start, int end) {
		if (mute)
			return;
		Clip c = clips.get(s);
		if (c == null)
			return;
		if (c.isRunning())
			c.stop();
		c.setFramePosition(start);
		c.setLoopPoints(start, end);
		while (!c.isRunning())
			c.start();
	}

	public static void stop(String s) {
		if (clips.get(s) == null)
			return;
		if (clips.get(s).isRunning())
			clips.get(s).stop();
	}

	public static void stopAll() {
		for (String s : clips.keySet()) {
			if (clips.get(s) == null)
				return;
			if (clips.get(s).isRunning())
				clips.get(s).stop();
		}
	}

	public static void resume(String s) {
		if (mute)
			return;
		if (clips.get(s).isRunning())
			return;
		clips.get(s).start();
	}

	public static void loop(String s) {
		loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int frame) {
		loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}

	public static void loop(String s, int frame, int start, int end) {
		stop(s);
		if (mute)
			return;
		clips.get(s).setLoopPoints(start, end);
		clips.get(s).setFramePosition(frame);
		clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void setPosition(String s, int frame) {
		clips.get(s).setFramePosition(frame);
	}

	public static int getFrames(String s) {
		return clips.get(s).getFrameLength();
	}

	public static int getPosition(String s) {
		return clips.get(s).getFramePosition();
	}

	public static void close(String s) {
		stop(s);
		clips.get(s).close();
	}

	private static int amountOfSound = 0;
	private static int soundLoaded = 0;

	public static void loadSound() {
		loadSoundsFromFile("/loadSound.txt");
	}

	private static void loadSoundsFromFile(String f) {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Boolean> isMusic = new ArrayList<Boolean>();
		try {
			Scanner scanner = new Scanner(Sound.class.getResourceAsStream(f));
			boolean readingMusic = false;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("-")) {
					if (line.equalsIgnoreCase("-music-")) {
						readingMusic = true;
						continue;
					} else if (line.equalsIgnoreCase("-sound-")) {
						readingMusic = false;
						continue;
					} else
						continue;
				}
				names.add(line);
				isMusic.add(readingMusic);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		amountOfSound = names.size();
		for (int i = 0; i < amountOfSound; i++) {
			if (isMusic.get(i)) {
				load(Path.MUSIC + "/" + names.get(i) + ".mp3", names.get(i));
			} else {
				load(Path.SOUND + "/" + names.get(i) + ".mp3", names.get(i));
			}
		}
	}

	public static double getPercentageLoaded() {
		if (amountOfSound == 0)
			return 1.0;
		if (soundLoaded == 0)
			return 0.0;
		return (double) soundLoaded / (double) amountOfSound;
	}
}