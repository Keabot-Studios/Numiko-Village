package net.keabotstudios.projectpickman.io.console;

import net.keabotstudios.projectpickman.GameInfo;

public class Logger {
	public enum LogLevel {
		INFO(0), DEBUG(1), ERROR(2), FATAL(3), NONE(Integer.MAX_VALUE);

		int level;

		private LogLevel(int l) {
			level = l;
		}

		public int getLevel() {
			return level;
		}
	}

	private static void log(LogLevel l, String s) {
		if (l.level >= GameInfo.log_level.level)
			System.out.println("[" + l.name() + "]" + s);
	}

	public static void info(String s) {
		log(LogLevel.INFO, s);
	}

	public static void debug(String s) {
		log(LogLevel.DEBUG, s);
	}

	public static void error(String s) {
		log(LogLevel.ERROR, s);
	}

	public static void fatal(String s) {
		log(LogLevel.FATAL, s);
	}

}
