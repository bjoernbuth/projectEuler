package com.euler;

import com.euler.common.Timing;
import com.google.common.math.IntMath;
import com.koloboke.collect.map.IntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler74 {
	private final static int LENGTH=60;
	private final static int MAX=IntMath.pow(10,6);
	
	private static class LengthCalculator	{
		private final static int[] FACTORIAL_CACHE=new int[] {1,1,2,6,24,120,720,5040,40320,362880};
		private IntIntMap nextNumberCache=HashIntIntMaps.newMutableMap();
		private static int getNextNumber(int in)	{
			int result=0;
			while (in>0)	{
				result+=FACTORIAL_CACHE[in%10];
				in/=10;
			}
			return result;
		}
		public int getLength(int in)	{
			IntSet chain=HashIntSets.newMutableSet();
			chain.add(in);
			for (;;)	{
				in=nextNumberCache.computeIfAbsent(in,LengthCalculator::getNextNumber);
				if (chain.contains(in)) return chain.size();
				else if (chain.size()>=LENGTH) return -1;
				chain.add(in);
			}
		}
	}

	public static long solve()	{
		int result=0;
		LengthCalculator calculator=new LengthCalculator();
		for (int i=3;i<MAX;++i) if (calculator.getLength(i)==LENGTH) ++result;
		return result;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler74::solve);
	}
}
