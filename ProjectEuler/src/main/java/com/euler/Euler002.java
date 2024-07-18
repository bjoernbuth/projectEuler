package com.euler;

import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler002 {
	private final static long LIMIT = 4 * LongMath.pow(10l, 6);

	private static long solve() {
		double sq5 = Math.sqrt(5);
		double phi = (1 + sq5) / 2.0;
		int n = (int) Math.floor(Math.log(sq5 * LIMIT) / (3.0 * Math.log(phi)));
		double phi3 = Math.pow(phi, 3d);
		double numerator = Math.pow(phi, 3 * n + 3) - phi3;
		double denominator = (phi3 - 1) * sq5;
		return (long) Math.round(numerator / denominator);
	}

	public static void main(String[] args) {
		Timing.time(Euler002::solve);
	}
}
