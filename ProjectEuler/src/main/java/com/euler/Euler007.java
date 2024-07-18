package com.euler;

import com.euler.common.Primes;
import com.euler.common.Timing;

public class Euler007 {
	private final static int N = 10001;

	private static long solve() {
		int limit = (int) (2 * N * Math.log(N));
		boolean[] composites = Primes.sieve(limit);
		int count = 2;
		boolean add4 = false;
		for (int i = 5;; i += (add4 ? 4 : 2), add4 = !add4)
			if (!composites[i]) {
				++count;
				if (count == N)
					return i;
			}
	}

	public static void main(String[] args) {
		Timing.time(Euler007::solve);
	}
}
