package net.keabotstudios.projectpickman.graphics.text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.keabotstudios.projectpickman.graphics.text.TextCommand.CommandType;
import net.keabotstudios.projectpickman.io.console.Logger;

public class RichText {

	private String text;
	private TextCommand[] textCommands;

	public RichText(String text) {
		this.text = parse(text);
	}

	private String parse(String text) {
		Matcher m = Pattern.compile(TextCommand.BASE_COMMAND).matcher(text);
		ArrayList<TextCommand> commands = new ArrayList<TextCommand>();
		int lastCommandIndex = 0;
		String formattedText = "";
		int offset = 0;
		while (m.find()) {
			String group = m.group();
			String commandText = group.substring(1, group.length() - 1);
			CommandType commandType = CommandType.getFromText(commandText);
			TextCommand command = null;
			int commandIndex = m.start();
			if (commandType != null) {
				switch (commandType) {
				default:
					continue;
				case PLAYER_NAME:
					command = new PlayerNameCommand(commandIndex - offset, commandText);
					break;
				case NEW_LINE:
					command = new NewLineCommand(commandIndex - offset, commandText);
					break;
				case COLOR:
					command = new ChangeColorCommand(commandIndex - offset, commandText);
					break;
				case SHAKE:
					command = new ShakeCommand(commandIndex - offset, commandText);
					break;
				case STOP_SHAKE:
					command = new StopShakeCommand(commandIndex - offset, commandText);
					break;
				case FACE:
					command = new ChangeFaceCommand(commandIndex - offset, commandText);
					break;
				case NULL_FACE:
					command = new NullFaceCommand(commandIndex - offset, commandText);
					break;
				case WAIT:
					command = new WaitCommand(commandIndex - offset, commandText);
					break;
				}
				
				formattedText += text.substring(lastCommandIndex, commandIndex) + command.getShowText();
				offset += command.getShowTextLength() + command.getCommandTextSize();
				lastCommandIndex = commandIndex + command.getCommandTextSize();
				commands.add(command);
			}
		}
		textCommands = commands.toArray(new TextCommand[commands.size()]);

		return formattedText;
	}

	public String getText() {
		return text;
	}

	public boolean isEmpty() {
		return text.isEmpty();
	}

	public int getLength() {
		return text.length();
	}

	public TextCommand[] getCommands() {
		return textCommands;
	}
}
