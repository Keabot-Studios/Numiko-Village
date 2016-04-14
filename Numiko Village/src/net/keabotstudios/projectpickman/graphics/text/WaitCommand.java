package net.keabotstudios.projectpickman.graphics.text;

import net.keabotstudios.projectpickman.References;

public class WaitCommand extends TextCommand {
	
	private int delay = 0;

	public WaitCommand(int index, String commandText) {
		super(CommandType.WAIT, index, commandText);
	}

	public void parse(String text) {
		delay = Integer.parseInt(text.substring(1));
		if(delay < 1) delay = 1;
		if(delay > References.MAX_UPS * 5) delay = References.MAX_UPS * 5;
	}

	public String getShowText() {
		return "";
	}
	
	public int getDelay() {
		return delay;
	}

}
