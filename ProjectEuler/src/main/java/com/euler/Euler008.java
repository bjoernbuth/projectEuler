package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;
import com.google.common.base.Joiner;

public class Euler008 {
	private final static int LENGTH = 13;

	private static long getMaxProduct(int[] in) {
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

	private static int[] extractDigits(String str) {
		int[] result = new int[str.length()];
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if ((c <= '0') || (c > '9'))
				throw new IllegalArgumentException();
			else
				result[i] = c - '0';
		}
		return result;
	}

	private static List<int[]> separateIntoDigitSets(String digitStream) {
		String[] split = digitStream.split("0");
		List<int[]> result = new ArrayList<>(split.length);
		for (String str : split)
			if (str.length() >= LENGTH)
				result.add(extractDigits(str));
		return result;
	}

	private static long solve() {
		try {
			URL resource = Euler008.class.getResource("in8.txt");
			String joinedLines = Joiner.on("")
					.join(Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList()));
			List<int[]> digitSets = separateIntoDigitSets(joinedLines);
			return digitSets.stream().mapToLong(Euler008::getMaxProduct).max().getAsLong();
		} catch (IOException | URISyntaxException exc) {
			throw new RuntimeException(exc);
		}
	}

	public static void main(String[] args) {
		Timing.time(Euler008::solve);
	}
}
