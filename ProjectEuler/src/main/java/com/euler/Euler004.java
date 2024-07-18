package com.euler;

import com.euler.common.Timing;

public class Euler004 {
	private static boolean isPalindrome(long in) {
		long[] digits = new long[6];
		for (int i = 0; i < 6; ++i) {
			digits[i] = in % 10;
			in /= 10;
		}
		return ((digits[0] == digits[5]) && (digits[1] == digits[4]) && (digits[2] == digits[3]));
	}

	private static long solve() {
		long bestFound = -1;
		for (long a = 990;; a -= 11l) {
			if (a * 999 < bestFound)
				break;
			for (long b = 999; b > 0; --b) {
				long product = a * b;
				if (product < bestFound)
					break;
				else if (isPalindrome(product))
					bestFound = product;
			}
		}
		return bestFound;
	}

	public static void main(String[] args) {
		Timing.time(Euler004::solve);
	}
}
