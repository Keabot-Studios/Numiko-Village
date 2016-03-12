package net.keabotstudios.projectpickman.graphics.text;

public class NullFaceCommand extends TextCommand {

	public NullFaceCommand(int index, String commandText) {
		super(index, commandText);
	}

	public void parse(String text) {}

	public String showText() {
		return "";
	}

}
