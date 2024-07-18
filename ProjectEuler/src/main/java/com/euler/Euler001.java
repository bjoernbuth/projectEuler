package com.euler;

import com.euler.common.Timing;

public class Euler001 {
	private final static long LIMIT = 999;
	private final static long PRIME1 = 3;
	private final static long PRIME2 = 5;

	private static long triangular(long in) {
		return ((in + 1) * in) / 2;
	}

	private static long multiplesOfAUpToB(long a, long b) {
		long howMany = b / a;
		return a * triangular(howMany);
	}

	private static long solve() {
		return multiplesOfAUpToB(PRIME1, LIMIT) + multiplesOfAUpToB(PRIME2, LIMIT)
				- multiplesOfAUpToB(PRIME1 * PRIME2, LIMIT);
	}

	public static void main(String[] args) {
		Timing.time(Euler001::solve);
	}
}
