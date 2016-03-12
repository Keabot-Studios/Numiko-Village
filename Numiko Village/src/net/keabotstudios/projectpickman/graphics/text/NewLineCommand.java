package net.keabotstudios.projectpickman.graphics.text;

public class NewLineCommand extends TextCommand {

	public NewLineCommand(int index, String commandText) {
		super(index, commandText);
	}

	public void parse(String text) {}

	public String showText() {
		return "";
	}

}
