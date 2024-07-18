package com.euler;

import com.euler.common.Primes;
import com.euler.common.Timing;

public class Euler26 {
	private final static int LIMIT=1000;
	
	private static int getCycleLength(int prime)	{
		int q=1;
		int count=1;
		for (;;)	{
			q=(10*q)%prime;
			if (q==1) return count;
			++count;
		}
	}
	
	private static long solve()	{
		int[] primes=Primes.listIntPrimesAsArray(LIMIT);
		int maxPrime=-1;
		int maxLength=-1;
		for (int i=primes.length-1;i>=0;--i)	{
			int prime=primes[i];
			if (prime<maxLength) break;
			int length=getCycleLength(prime);
			if (length>maxLength)	{
				maxPrime=prime;
				maxLength=length;
			}
		}
		return maxPrime;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler26::solve);
	}
}
