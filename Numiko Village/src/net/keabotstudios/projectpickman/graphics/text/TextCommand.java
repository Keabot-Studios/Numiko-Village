package net.keabotstudios.projectpickman.graphics.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TextCommand {
	
	public static final String BASE_COMMAND = "<{1}[^<>]*>{1}";
	
	public enum CommandType {
		PLAYER_NAME("playername"), NEW_LINE("n"), COLOR("c[0-9]+"), WAIT("w[0-9]+"), SHAKE("s[0-9]+,[0-9]*\\.[0-9]+"), STOP_SHAKE("s-"), FACE("f[0-9]+,[0-9]+"), NULL_FACE("f-");
		
		private Pattern pattern;
		
		private CommandType(String pattern) {
			this.pattern = Pattern.compile(pattern);
		}
		
		public Pattern getPattern() {
			return pattern;
		}
		
		public static CommandType getFromText(String text) {
			Matcher m = null;
			for(CommandType ct : CommandType.values()) {
				m = ct.pattern.matcher(text);
				if(m.matches()) {
					return ct;
				}
			}
			return null;
		}
	}
	
	protected final CommandType type;
	protected final int index;
	protected final String commmandText;
	
	public TextCommand(CommandType type, int index, String commandText) {
		this.type = type;
		this.index = index;
		this.commmandText = commandText;
		parse(commandText);
	}
	
	public abstract void parse(String text);
	
	public abstract String getShowText();
	
	public int getShowTextLength() {
		return getShowText().length();
	}

	public int getIndex() {
		return index;
	}
	
	public String getCommandText() {
		return commmandText;
	}

	public int getCommandTextSize() {
		return commmandText.length() + 2;
	}
	
	public CommandType getType() {
		return type;
	}

}
