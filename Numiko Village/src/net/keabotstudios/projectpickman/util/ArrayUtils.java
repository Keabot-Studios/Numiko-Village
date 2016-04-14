package net.keabotstudios.projectpickman.util;

import java.util.List;

public class ArrayUtils {

	public static int[] toIntArray(List<Integer> list) {
		int[] ret = new int[list.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = list.get(i);
		return ret;
	}

}
