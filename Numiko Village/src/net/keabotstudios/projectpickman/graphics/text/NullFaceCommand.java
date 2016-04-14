package net.keabotstudios.projectpickman.graphics.text;

public class NullFaceCommand extends TextCommand {

	public NullFaceCommand(int index, String commandText) {
		super(CommandType.NULL_FACE, index, commandText);
	}

	public void parse(String text) {}

	public String getShowText() {
		return "";
	}

}
