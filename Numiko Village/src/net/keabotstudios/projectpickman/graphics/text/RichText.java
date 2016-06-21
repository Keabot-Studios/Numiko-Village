package net.keabotstudios.projectpickman.graphics.text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.keabotstudios.projectpickman.graphics.text.TextCommand.CommandType;

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
				int index = commandIndex + offset;
				switch (commandType) {
				case PLAYER_NAME:
					command = new PlayerNameCommand(index, commandText);
					break;
				case NEW_LINE:
					command = new NewLineCommand(index, commandText);
					break;
				case COLOR:
					command = new ChangeColorCommand(index, commandText);
					break;
				case SHAKE:
					command = new ShakeCommand(index, commandText);
					break;
				case STOP_SHAKE:
					command = new StopShakeCommand(index, commandText);
					break;
				case FACE:
					command = new ChangeFaceCommand(index, commandText);
					break;
				case NULL_FACE:
					command = new NullFaceCommand(index, commandText);
					break;
				case WAIT:
					command = new WaitCommand(index, commandText);
					break;
				default:
					continue;
				}
				
				formattedText += text.substring(lastCommandIndex, commandIndex) + command.getShowText();
				offset += command.getShowTextLength() - command.getCommandTextSize();
				lastCommandIndex = commandIndex + command.getCommandTextSize();
				commands.add(command);
			}
		}
		
		textCommands = commands.toArray(new TextCommand[commands.size()]);
		parseTextCommands(formattedText);
		return formattedText;
	}
	
	private void parseTextCommands(String text) {
		TextCommand[] commands = this.textCommands;
		
		ArrayList<Integer> newLines = new ArrayList<Integer>();
		
		
		for (int i = 0; i < text.length(); i++) {
			for (int ci = 0; ci < commands.length; ci++) {
				TextCommand command = commands[ci];
				if (command.getIndex() == i) {
					switch (command.getType()) {
					case NEW_LINE:
						if (!newLines.contains(i))
							newLines.add(i);
					default:
						break;
					}
				}
			}
		}
		for (int i = 0; i < text.length(); i++) {
			for (int ci = 0; ci < commands.length; ci++) {
				TextCommand command = commands[ci];
				if (command.getIndex() == i) {
					switch (command.getType()) {
					case NEW_LINE:
						System.out.print("\n");
					default:
						break;
					}
				}
			}
			System.out.print(text.charAt(i));
		}
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
