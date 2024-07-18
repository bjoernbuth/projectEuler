package com.euler;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.euler.common.Timing;

public class Euler013 {
	private static String solve() {
		try {
			URL resource = Euler011.class.getResource("in13.txt");
			BigInteger sum = Files.lines(Paths.get(resource.toURI())).map(BigInteger::new).reduce(BigInteger::add)
					.get();
			return sum.toString().substring(0, 10);
		} catch (IOException | URISyntaxException exc) {
			throw new RuntimeException(exc);
		}
	}

	public static void main(String[] args) {
		Timing.time(Euler013::solve);
	}
}
