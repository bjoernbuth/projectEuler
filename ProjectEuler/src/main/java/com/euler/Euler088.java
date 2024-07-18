package com.euler;

import java.util.Set;

import com.euler.common.DecompositionFinder;
import com.euler.common.Timing;
import com.google.common.collect.Multiset;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler88 {
	private final static int LIMIT=12000;
	
	private static int getCandidate(Multiset<Integer> decomp,int total)	{
		int result=decomp.size()+total;
		for (int n:decomp) result-=n;
		/*
		 * Theoretically we should consider the case that total<sum. This actually never happens since we have at least two factors and
		 * they are greater than 1.
		 */
		return result;
	}
	
	private static long solve()	{
		boolean[] found=new boolean[LIMIT+1];
		found[0]=true;
		found[1]=true;
		int pending=LIMIT-1;
		DecompositionFinder finder=new DecompositionFinder(2*LIMIT);
		IntSet result=HashIntSets.newMutableSet();
		for (int i=4;pending>0;++i)	{
			Set<Multiset<Integer>> decompositions=finder.getDecomposition(i);
			if (decompositions.size()>1) for (Multiset<Integer> decomp:decompositions) if (decomp.size()>1)	{
				int candidate=getCandidate(decomp,i);
				if ((candidate<2)||(candidate>LIMIT)||found[candidate]) continue;
				found[candidate]=true;
				--pending;
				result.add(i);
			}
		}
		long sum=0;
		for (int val:result) sum+=val;
		return sum;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler88::solve);
	}
}
