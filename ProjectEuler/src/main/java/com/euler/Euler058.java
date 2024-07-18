package com.euler;

import com.euler.common.Primes;
import com.euler.common.Primes.RabinMiller;
import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler58 {
	private final static int SIEVE_LIMIT=IntMath.pow(10,7);
	
	private static class PrimeChecker	{
		private final static int[] WITNESSES=new int[] {2,3,5,7};
		private interface InternalPrimeChecker	{
			public boolean isPrime(long in);
		}
		private class ErathostenesPrimeChecker implements InternalPrimeChecker	{
			private final boolean[] composites;
			public ErathostenesPrimeChecker(int limit)	{
				composites=Primes.sieve(limit);
			}
			@Override
			public boolean isPrime(long in) {
				int i=(int)in;
				if (i>=composites.length)	{
					checker=new RabinMillerPrimeChecker();
					return checker.isPrime(in);
				}
				return !composites[i];
			}
		}
		private class RabinMillerPrimeChecker implements InternalPrimeChecker	{
			private final RabinMiller rabinMiller=new RabinMiller();
			@Override
			public boolean isPrime(long in)	{
				return rabinMiller.isPrime(in,WITNESSES);
			}
		}
		private InternalPrimeChecker checker;
		public PrimeChecker(int sieveLimit)	{
			checker=new ErathostenesPrimeChecker(sieveLimit);
		}
		public boolean isPrime(long in)	{
			return checker.isPrime(in);
		}
	}
	
	private static long solve()	{
		PrimeChecker checker=new PrimeChecker(SIEVE_LIMIT);
		long f1=3;
		long f2=5;
		long f3=7;
		long f4=9;
		long primes=3;
		long total=4;
		for (long i=4;;i+=2)	{
			f1=f4+i;
			if (checker.isPrime(f1)) ++primes;
			f2=f1+i;
			if (checker.isPrime(f2)) ++primes;
			f3=f2+i;
			if (checker.isPrime(f3)) ++primes;
			f4=f3+i;
			if (checker.isPrime(f4)) ++primes;
			total+=4;
			if (10*primes<total) return i-1;
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler58::solve);
	}
}
