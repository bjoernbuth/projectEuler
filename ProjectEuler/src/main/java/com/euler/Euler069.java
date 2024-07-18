package com.euler;

import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler69 {
	private final static long LIMIT=LongMath.pow(10l,6);
	
	private final static int PRIME_LIMIT=100;
	
	private static long solve()	{
		boolean[] composites=Primes.sieve(PRIME_LIMIT);
		long result=1l;
		for (int i=2;;++i) if (!composites[i])	{
			long newProduct=result*i;
			if (newProduct>LIMIT) return result;
			result=newProduct;
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler69::solve);
	}
}
