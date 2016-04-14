package net.keabotstudios.projectpickman.graphics.text;

public class StopShakeCommand extends TextCommand {

	public StopShakeCommand(int index, String commandText) {
		super(CommandType.STOP_SHAKE, index, commandText);
	}

	public void parse(String text) {}

	public String getShowText() {
		return "";
	}

}
