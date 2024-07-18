package com.euler;

import java.util.NavigableSet;
import java.util.TreeSet;

import com.euler.common.DivisorHolder;
import com.euler.common.Primes;
import com.euler.common.Timing;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler23 {
	private final static int LIMIT=28123;
	
	private static NavigableSet<Integer> getAbundantNumbersUpTo(int limit)	{
		int[] firstPrimes=Primes.firstPrimeSieve(limit);
		NavigableSet<Integer> result=new TreeSet<>();
		for (int i=2;i<=limit;++i)	{
			DivisorHolder divs=DivisorHolder.getFromFirstPrimes(i,firstPrimes);
			if (divs.getDivisorSum()>2*i) result.add(i);
		}
		return result;
	}
	
	private static IntSet getSumsUpTo(int limit,NavigableSet<Integer> set)	{
		IntSet result=HashIntSets.newMutableSet();
		for (int a:set)	{
			int upperLimit=limit-a;
			if (a>upperLimit) break;
			NavigableSet<Integer> subset=set.subSet(a,true,upperLimit,true);
			for (int b:subset) result.add(a+b);
		}
		return result;
	}
	
	private static long solve()	{
		NavigableSet<Integer> abundantNumbers=getAbundantNumbersUpTo(LIMIT);
		IntSet sums=getSumsUpTo(LIMIT,abundantNumbers);
		long result=0;
		for (int i=1;i<=LIMIT;++i) if (!sums.contains(i)) result+=i;
		return result;
	}

	public static void main(String[] args)	{
		Timing.time(Euler23::solve);
	}
}
