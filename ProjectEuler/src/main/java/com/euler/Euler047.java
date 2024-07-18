package com.euler;

import com.euler.common.DivisorHolder;
import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler47 {
	private final static int LIMIT=IntMath.pow(10,6);
	
	private static long solve()	{
		int[] firstPrimes=Primes.firstPrimeSieve(LIMIT);
		int currentStreak=0;
		for (int i=2*3*5*7;i<=LIMIT;++i)	{
			DivisorHolder divisors=DivisorHolder.getFromFirstPrimes(i,firstPrimes);
			if (divisors.getFactorMap().size()==4)	{
				++currentStreak;
				if (currentStreak==4) return i-3;
			}	else currentStreak=0;
		}
		throw new RuntimeException("Please increase the limit.");
	}

	public static void main(String[] args)	{
		Timing.time(Euler47::solve);
	}
}
