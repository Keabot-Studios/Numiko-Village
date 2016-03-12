package net.keabotstudios.projectpickman.graphics.text;

import net.keabotstudios.projectpickman.GameInfo;

public class PlayerNameCommand extends TextCommand {

	public PlayerNameCommand(int index, String commandText) {
		super(index, commandText);
	}

	public void parse(String text) {}

	public String showText() {
		return GameInfo.playerName;
	}

}
