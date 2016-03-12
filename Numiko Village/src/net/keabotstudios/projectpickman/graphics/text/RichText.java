package net.keabotstudios.projectpickman.graphics.text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.keabotstudios.projectpickman.graphics.text.TextCommand.CommandType;
import net.keabotstudios.projectpickman.io.Logger;

public class RichText {

	private String text, formattedText;
	private TextCommand[] textCommands;

	public RichText(String text) {
		this.text = text;
		this.formattedText = parse(text);

	}

	private String parse(String text) {
		Matcher m = Pattern.compile(TextCommand.BASE_COMMAND).matcher(text);
		ArrayList<TextCommand> commands = new ArrayList<TextCommand>();
		int offset = 0;
		int lastIndex = 0;
		String formattedText = "";
		while (m.find()) {
			String group = m.group();
			String commandText = group.substring(1, group.length() - 1);
			CommandType commandType = CommandType.getFromText(commandText);
			TextCommand command = null;
			if (commandType != null) {
				switch (commandType) {
				default:
					continue;
				case PLAYER_NAME:
					command = new PlayerNameCommand(m.start() - offset, commandText);
					break;
				case NEW_LINE:
					command = new NewLineCommand(m.start() - offset, commandText);
					break;
				case COLOR:
					command = new ChangeColorCommand(m.start() - offset, commandText);
					break;
				case SHAKE:
					command = new ShakeCommand(m.start() - offset, commandText);
					break;
				case STOP_SHAKE:
					command = new StopShakeCommand(m.start() - offset, commandText);
					break;
				case FACE:
					command = new ChangeFaceCommand(m.start() - offset, commandText);
					break;
				case NULL_FACE:
					command = new NullFaceCommand(m.start() - offset, commandText);
					break;
				}
				Logger.debug("" + lastIndex);
				//formattedText += text.substring(lastIndex - offset, command.getIndex() + offset) + command.showText();
				lastIndex = command.getIndex();
				offset -= command.getCommandTextSize(); 
				commands.add(command);
			}
		}
		textCommands = commands.toArray(new TextCommand[commands.size()]);

		Logger.debug(formattedText);
		return formattedText;
	}

	public String getFormattedText() {
		return formattedText;
	}

}
