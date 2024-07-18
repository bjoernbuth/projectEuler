package com.euler;

import java.util.ArrayList;
import java.util.List;

import com.euler.common.DivisorHolder;
import com.euler.common.Primes;
import com.euler.common.Timing;

public class Euler005 {
	private final static int LIMIT = 20;

	private static long solve() {
		int[] firstPrimes = Primes.firstPrimeSieve(LIMIT);
		List<DivisorHolder> divisors = new ArrayList<>(LIMIT - 1);
		for (int i = 2; i <= LIMIT; ++i)
			divisors.add(DivisorHolder.getFromFirstPrimes(i, firstPrimes));
		DivisorHolder gcd = DivisorHolder.getLcm(divisors);
		return gcd.getAsLong();
	}

	public static void main(String[] args) {
		Timing.time(Euler005::solve);
	}
}
