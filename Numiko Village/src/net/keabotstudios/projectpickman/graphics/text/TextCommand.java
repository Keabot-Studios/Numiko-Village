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
	
	protected final int index;
	protected final String commmandText;
	
	public TextCommand(int index, String commandText) {
		this.index = index;
		this.commmandText = commandText;
	}
	
	public abstract void parse(String text);
	
	public abstract String showText();
	
	public int showTextLength() {
		return showText().length();
	}

	public int getIndex() {
		return index;
	}

	public int getCommandTextSize() {
		return commmandText.length() + 2;
	}

}
