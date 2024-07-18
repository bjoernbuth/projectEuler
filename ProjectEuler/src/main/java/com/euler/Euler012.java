package com.euler;

import com.euler.common.DivisorHolder;
import com.euler.common.Primes;
import com.euler.common.Timing;

public class Euler12 {
	private final static int N=500;
	
	private final static int LIMIT=N*N;	// Mostly a random guess. Well more than enough for this problem.
	
	private static long solve()	{
		int[] firstPrimes=Primes.firstPrimeSieve(LIMIT);
		DivisorHolder previous=new DivisorHolder();
		for (int i=2;i<=LIMIT;++i)	{
			DivisorHolder current=DivisorHolder.getFromFirstPrimes(i,firstPrimes);
			DivisorHolder combined=DivisorHolder.combine(previous,current);
			combined.removeFactor(2);
			if (combined.getAmountOfDivisors()>=N) return combined.getAsLong();
			previous=current;
		}
		throw new RuntimeException("Please increase the sieve number.");
	}

	public static void main(String[] args)	{
		Timing.time(Euler12::solve);
	}
}
