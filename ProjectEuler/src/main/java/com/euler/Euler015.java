package com.euler;

import com.euler.common.BigIntegerUtils;
import com.euler.common.Timing;

public class Euler015 {
	private final static int R = 20;
	private final static int C = 20;

	private static String solve() {
		return new BigIntegerUtils.BigCombinatorialNumberCache(R + C).get(R + C, R).toString();
	}

	public static void main(String[] args) {
		Timing.time(Euler015::solve);
	}
}
