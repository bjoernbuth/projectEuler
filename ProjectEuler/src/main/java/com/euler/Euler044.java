package com.euler;

import java.math.RoundingMode;

import com.euler.common.Timing;
import com.google.common.math.LongMath;
import com.koloboke.collect.set.LongSet;
import com.koloboke.collect.set.hash.HashLongSets;

public class Euler44 {
	private static boolean isPentagonal(long in)	{
		long det=1+24*in;
		long sq=LongMath.sqrt(det,RoundingMode.DOWN);
		if (sq*sq!=det) return false;
		return (sq+1)%6==0;
	}
	
	private static long solve()	{
		LongSet prevs=HashLongSets.newMutableSet();
		for (long i=1;;++i)	{
			long pentagonal=((3*i-1)*i)/2;
			for (long prev:prevs) if (isPentagonal(pentagonal+prev)&&prevs.contains(pentagonal-prev)) return pentagonal-prev;
			prevs.add(pentagonal);
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler44::solve);
	}
}
