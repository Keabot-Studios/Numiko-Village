package net.keabotstudios.projectpickman.graphics.text;

public class ChangeColorCommand extends TextCommand {
	
	private int colorId = 0;

	public ChangeColorCommand(int index, String commandText) {
		super(CommandType.COLOR, index, commandText);
	}

	public void parse(String text) {
		colorId = Integer.parseInt(text.substring(1));
	}

	public String getShowText() {
		return "";
	}
	
	public int getColorId() {
		return colorId;
	}

}
