package com.euler;

import com.euler.common.DivisorHolder;
import com.euler.common.Primes;
import com.euler.common.Timing;
import com.koloboke.collect.IntCursor;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler95 {
	private final static int LIMIT=1000000;
	
	private static long solve()	{
		int[] firstPrimes=Primes.firstPrimeSieve(LIMIT);
		int[] divisorSums=new int[1+LIMIT];
		divisorSums[1]=1;
		for (int i=2;i<=LIMIT;++i)	{
			DivisorHolder divs=DivisorHolder.getFromFirstPrimes(i,firstPrimes);
			divisorSums[i]=(int)(divs.getDivisorSum()-i);
		}
		boolean[] visited=new boolean[1+LIMIT];
		int maxLength=0;
		int result=0;
		for (int i=2;i<=LIMIT;++i) if (!visited[i]&&(firstPrimes[i]!=0))	{
			IntSet chain=HashIntSets.newMutableSet();
			int n=i;
			while (!chain.contains(n))	{
				if (n>LIMIT) break;
				chain.add(n);
				n=divisorSums[n];
			}
			if (n==i)	{
				// Chain found.
				IntCursor cursor=chain.cursor();
				while (cursor.moveNext()) visited[cursor.elem()]=true;
				if (chain.size()>maxLength)	{
					maxLength=chain.size();
					result=i;
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler95::solve);
	}
}
