package net.keabotstudios.projectpickman.graphics.text;

public class NewLineCommand extends TextCommand {

	public NewLineCommand(int index, String commandText) {
		super(CommandType.NEW_LINE, index, commandText);
	}

	public void parse(String text) {}

	public String getShowText() {
		return "";
	}

}
