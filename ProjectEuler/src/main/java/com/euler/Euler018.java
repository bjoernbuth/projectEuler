package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler018 {
	private static String fileName;

	private static int[][] parseLines(List<String> lines) {
		int[][] result = new int[lines.size()][];
		result[0] = new int[] { Integer.parseInt(lines.get(0)) };
		for (int i = 1; i < lines.size(); ++i) {
			String[] split = lines.get(i).split(" ");
			if (split.length != i + 1)
				throw new IllegalArgumentException();
			result[i] = new int[i + 1];
			for (int j = 0; j <= i; ++j)
				result[i][j] = Integer.parseInt(split[j]);
		}
		return result;
	}

	private static long[][] getBestSums(int[][] in) {
		long[][] result = new long[in.length][];
		result[0] = new long[] { in[0][0] };
		for (int i = 1; i < in.length; ++i) {
			result[i] = new long[i + 1];
			result[i][0] = result[i - 1][0] + in[i][0];
			for (int j = 1; j < i; ++j)
				result[i][j] = Math.max(result[i - 1][j - 1], result[i - 1][j]) + in[i][j];
			result[i][i] = result[i - 1][i - 1] + in[i][i];
		}
		return result;
	}

	private static long solve() {
		try {
			URL resource = Euler018.class.getResource(fileName);
			List<String> lines = Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			int[][] triangle = parseLines(lines);
			long[][] bestSums = getBestSums(triangle);
			return Arrays.stream(bestSums[bestSums.length - 1]).max().getAsLong();
		} catch (IOException | URISyntaxException exc) {
			throw new RuntimeException(exc);
		}
	}

	public static void main(String[] args) {
		if (args.length == 0)
			fileName = "in18.txt";
		else
			fileName = "in67.txt";
		Timing.time(Euler018::solve);
	}
}
