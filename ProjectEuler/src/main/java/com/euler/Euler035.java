package com.euler;

import com.euler.common.CustomOrderedCombinationIterator;
import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.IntMath;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler35 {
	private final static int MAX_DIGITS=6;
	
	private static int[] generateValues(int[] comb)	{
		int N=comb.length;
		int[] result=new int[N];
		for (int i=0;i<N;++i)	{
			int val=0;
			for (int j=0;j<N;++j)	{
				val*=10;
				val+=comb[(i+j)%N];
			}
			result[i]=val;
		}
		return result;
	}
	
	private static boolean isOrderedAndCircular(int[] values,boolean[] composites)	{
		if (composites[values[0]]) return false;
		for (int i=1;i<values.length;++i) if ((values[i]<values[0])||composites[values[i]]) return false;
		return true;
	}
	
	private static long solve()	{
		IntSet primes=HashIntSets.newMutableSet();
		boolean[] composites=Primes.sieve(IntMath.pow(10,MAX_DIGITS));
		int[] odds=new int[] {1,3,5,7,9};
		for (int i=2;i<=MAX_DIGITS;++i)	{
			CustomOrderedCombinationIterator iterator=new CustomOrderedCombinationIterator(i, odds, false);
			for (int[] comb:iterator)	{
				int[] values=generateValues(comb);
				if (isOrderedAndCircular(values,composites)) for (int p:values) primes.add(p);
			}
		}
		return primes.size()+4;
	}

	public static void main(String[] args)	{
		Timing.time(Euler35::solve);
	}
}
