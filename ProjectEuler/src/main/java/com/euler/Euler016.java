package com.euler;

import java.math.BigInteger;

import com.euler.common.EulerUtils;
import com.euler.common.Timing;

public class Euler016 {
	private final static int POWER = 1000;

	private static long solve() {
		BigInteger bigPow = EulerUtils.exp(BigInteger.TWO, POWER);
		long result = 0;
		for (char c : bigPow.toString().toCharArray())
			result += c - '0';
		return result;
	}

	public static void main(String[] args) {
		Timing.time(Euler016::solve);
	}
}
