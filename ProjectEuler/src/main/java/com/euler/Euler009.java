package com.euler;

import com.euler.common.PythagoreanTriples;
import com.euler.common.Timing;

public class Euler009 {
	private final static long N = 1000;

	private static long solve() {
		PythagoreanTriples.PrimitiveTriplesIterator iterator = new PythagoreanTriples.PrimitiveTriplesIterator();
		for (;;) {
			iterator.next();
			long a = iterator.a();
			long b = iterator.b();
			long c = iterator.c();
			long sum = a + b + c;
			if ((N % sum) == 0) {
				long q = N / sum;
				return q * q * q * a * b * c;
			}
		}
	}

	public static void main(String[] args) {
		Timing.time(Euler009::solve);
	}
}
