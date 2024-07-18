package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler011 {
	private final static int LENGTH = 4;

	public static long getMaxProduct(int[] in) {
		long curProduct = 1;
		for (int i = 0; i < LENGTH; ++i)
			curProduct *= in[i];
		long maxProduct = curProduct;
		for (int i = LENGTH; i < in.length; ++i) {
			curProduct /= in[i - LENGTH];
			curProduct *= in[i];
			maxProduct = Math.max(maxProduct, curProduct);
		}
		return maxProduct;
	}

	private static int[][] parseInts(List<String> lines) {
		int size1 = lines.size();
		String[] split = lines.get(0).split(" ");
		int size2 = split.length;
		int[][] result = new int[size1][size2];
		for (int j = 0; j < size2; ++j)
			result[0][j] = Integer.parseInt(split[j]);
		for (int i = 1; i < size1; ++i) {
			split = lines.get(i).split(" ");
			for (int j = 0; j < size2; ++j)
				result[i][j] = Integer.parseInt(split[j]);
		}
		return result;
	}

	private static int findZero(int[] array, int startingPos) {
		for (int i = startingPos; i < array.length; ++i)
			if (array[i] == 0)
				return i;
		return -1;
	}

	private static void curateLine(int[] array, List<int[]> result) {
		int currentStart = 0;
		while (array.length - currentStart >= LENGTH) {
			int pos = findZero(array, currentStart);
			if (pos == -1) {
				result.add(Arrays.copyOfRange(array, currentStart, array.length));
				return;
			} else if (pos == currentStart)
				++currentStart;
			else {
				int len = pos - currentStart;
				if (len >= LENGTH)
					result.add(Arrays.copyOfRange(array, currentStart, pos));
				currentStart = pos + 1;
			}
		}
	}

	private static int[] getLine(int startingRow, int startingCol, int rowInc, int colInc, int[][] array) {
		int maxLenCol = array.length;
		int maxLenRow = array[0].length;
		if (rowInc > 0)
			maxLenRow = (array.length - startingRow) / rowInc;
		else if (rowInc < 0)
			maxLenRow = (startingRow / (-rowInc)) + 1;
		if (colInc > 0)
			maxLenCol = (array.length - startingCol) / colInc;
		else if (rowInc < 0)
			maxLenCol = (startingCol / (-colInc)) + 1;
		int maxLen = Math.min(maxLenCol, maxLenRow);
		int[] result = new int[maxLen];
		int row = startingRow;
		int col = startingCol;
		for (int i = 0; i < maxLen; ++i) {
			result[i] = array[row][col];
			row += rowInc;
			col += colInc;
		}
		return result;
	}

	private static List<int[]> getCuratedLines(int[][] array) {
		List<int[]> lines = new ArrayList<>();
		for (int i = 0; i < array.length; ++i)
			lines.add(array[i]);
		for (int i = 0; i < array[0].length; ++i)
			lines.add(getLine(0, i, 1, 0, array));
		for (int i = 0; i < array.length; ++i) {
			lines.add(getLine(i, 0, 1, 1, array));
			lines.add(getLine(i, 0, -1, 1, array));
		}
		for (int i = 1; i < array[0].length; ++i) {
			lines.add(getLine(0, i, 1, 1, array));
			lines.add(getLine(array.length - 1, i, -1, 1, array));
		}
		List<int[]> result = new ArrayList<>();
		for (int[] line : lines)
			curateLine(line, result);
		return result;
	}

	private static long solve() {
		try {
			URL resource = Euler011.class.getResource("in11.txt");
			List<String> lines = Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			int[][] array = parseInts(lines);
			List<int[]> curatedLines = getCuratedLines(array);
			return curatedLines.stream().mapToLong(Euler011::getMaxProduct).max().getAsLong();
		} catch (IOException | URISyntaxException exc) {
			throw new RuntimeException(exc);
		}
	}

	public static void main(String[] args) {
		Timing.time(Euler011::solve);
	}
}
