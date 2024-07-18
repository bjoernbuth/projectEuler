package com.euler;

import com.euler.common.Timing;
import com.google.common.math.LongMath;
import com.koloboke.collect.map.LongIntMap;
import com.koloboke.collect.map.hash.HashLongIntMaps;

public class Euler014 {
	private static long LIMIT = LongMath.pow(10l, 6);

	private static class MemoizedCollatz {
		private final LongIntMap lengths;

		public MemoizedCollatz() {
			lengths = HashLongIntMaps.newMutableMap();
			lengths.put(1l, 1);
		}

		public int getLength(long in) {
			return lengths.computeIfAbsent(in, (long key) -> {
				if ((key % 2) == 0)
					return 1 + getLength(key / 2);
				else
					return 1 + getLength(3 * key + 1);
			});
		}
	}

	private static long solve() {
		long max = 0l;
		long maxLen = 0l;
		MemoizedCollatz memory = new MemoizedCollatz();
		for (int i = 1; i <= LIMIT; ++i) {
			long len = memory.getLength(i);
			if (len > maxLen) {
				maxLen = len;
				max = i;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		Timing.time(Euler014::solve);
	}
}
