package com.euler;

import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler010 {
	private final static long LIMIT = 2 * LongMath.pow(10l, 6);

	private static long solve() {
		return Primes.sumPrimes(LIMIT);
	}

	public static void main(String[] args) {
		Timing.time(Euler010::solve);
	}
}
