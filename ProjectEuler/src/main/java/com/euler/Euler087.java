package com.euler;

import java.math.RoundingMode;

import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.IntMath;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler87 {
	private final static int LIMIT=5*IntMath.pow(10,7);

	private static long solve()	{
		int primeLimit=IntMath.sqrt(LIMIT,RoundingMode.DOWN);
		int[] primes=Primes.listIntPrimesAsArray(primeLimit);
		int[] primesSquared=new int[primes.length];
		int[] primesCubed=new int[primes.length];
		int[] primesTesseracted=new int[primes.length];	// Sure, why not.
		for (int i=0;i<primes.length;++i) primesSquared[i]=primes[i]*primes[i];
		for (int i=0;i<primes.length;++i)	{
			primesCubed[i]=primesSquared[i]*primes[i];
			if (primesCubed[i]>LIMIT) break;
		}
		for (int i=0;i<primes.length;++i)	{
			primesTesseracted[i]=primesSquared[i]*primesSquared[i];
			if (primesTesseracted[i]>LIMIT) break;
		}
		IntSet found=HashIntSets.newMutableSet();
		for (int p:primesSquared)	{
			int cubesAdded=0;
			for (int q:primesCubed)	{
				int fourthsAdded=0;
				int sum2=p+q;
				for (int r:primesTesseracted)	{
					int sum3=sum2+r;
					if (sum3>=LIMIT) break;
					found.add(sum3);
					++fourthsAdded;
					++cubesAdded;
				}
				if (fourthsAdded==0) break;
			}
			if (cubesAdded==0) break;
		}
		return found.sizeAsLong();
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler87::solve);
	}
}
