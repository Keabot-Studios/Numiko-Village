package net.keabotstudios.projectpickman.graphics.text;

public class ChangeFaceCommand extends TextCommand {
	
	private int faceId = 0, emotionId = 0;

	public ChangeFaceCommand(int index, String commandText) {
		super(index, commandText);
	}

	public void parse(String text) {
		String[] parts = text.substring(1).split(",");
		faceId = Integer.parseInt(parts[0]);
		emotionId = Integer.parseInt(parts[1]);
	}

	public int getFaceId() {
		return faceId;
	}

	public int getEmotionId() {
		return emotionId;
	}

	public String showText() {
		return "";
	}

}
