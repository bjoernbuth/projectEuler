package com.euler;

import java.math.RoundingMode;
import java.util.List;

import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler003 {
	private final static long NUMBER = 600851475143l;

	private static long solve() {
		List<Long> primes = Primes.listLongPrimes(LongMath.sqrt(NUMBER, RoundingMode.UP));
		long n = NUMBER;
		for (long p : primes) {
			while ((n % p) == 0)
				n /= p;
			if (n == 1l)
				return p;
		}
		return n;
	}

	public static void main(String[] args) {
		Timing.time(Euler003::solve);
	}
}
