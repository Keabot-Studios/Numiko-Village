package net.keabotstudios.projectpickman.graphics.text;

import java.awt.Color;

public class ChangeColorCommand extends TextCommand {
	
	private int colorId = 0;

	public ChangeColorCommand(int index, String commandText) {
		super(index, commandText);
	}

	public void parse(String text) {
		colorId = Integer.parseInt(text.substring(1));
	}

	public String showText() {
		return "";
	}

}
