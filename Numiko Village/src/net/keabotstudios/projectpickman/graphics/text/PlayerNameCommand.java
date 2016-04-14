package net.keabotstudios.projectpickman.graphics.text;

import net.keabotstudios.projectpickman.GameInfo;

public class PlayerNameCommand extends TextCommand {

	public PlayerNameCommand(int index, String commandText) {
		super(CommandType.PLAYER_NAME, index, commandText);
	}

	public void parse(String text) {}

	public String getShowText() {
		return GameInfo.playerName;
	}

}
