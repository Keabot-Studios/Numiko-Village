package net.keabotstudios.projectpickman.graphics.text;

public class ShakeCommand extends TextCommand {
	
	private int shakeDist = 0;
	private float shakeProb = 0.0f;

	public ShakeCommand(int index, String commandText) {
		super(index, commandText);
	}

	public void parse(String text) {
		String[] parts = text.substring(1).split(",");
		shakeDist = Integer.parseInt(parts[0]);
		shakeProb = Float.parseFloat(parts[1]);
	}

	public String showText() {
		return "";
	}

}
