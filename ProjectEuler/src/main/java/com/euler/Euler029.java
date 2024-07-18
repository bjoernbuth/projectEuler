package com.euler;

import java.util.ArrayList;
import java.util.List;

import com.euler.common.EulerUtils;
import com.euler.common.Timing;
import com.koloboke.collect.map.IntIntCursor;
import com.koloboke.collect.map.IntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler29 {
	private final static int MAX_BASE=100;
	private final static int MAX_EXP=100;
	
	private static class PowerCounter	{
		private final int limit;
		private final List<IntSet> generatedNumbers;
		public PowerCounter(int limit)	{
			this.limit=limit;
			generatedNumbers=new ArrayList<>();
			IntSet single=HashIntSets.newMutableSet();
			for (int i=2;i<=limit;++i) single.add(i);
			generatedNumbers.add(single);
		}
		public int getIncrease(int power)	{
			if (power==1) return generatedNumbers.get(0).size();
			ensureSize(power);
			return generatedNumbers.get(power-1).size()-generatedNumbers.get(power-2).size();
		}
		private void ensureSize(int n)	{
			for (int k=generatedNumbers.size()+1;k<=n;++k)	{
				IntSet set=HashIntSets.newMutableSet(generatedNumbers.get(k-2));
				for (int i=2;i<=limit;++i) set.add(i*k);
				generatedNumbers.add(set);
			}
		}
	}
	
	private static IntIntMap countCases(int maxBase)	{
		IntIntMap result=HashIntIntMaps.newMutableMap();
		IntIntMap knownPowers=HashIntIntMaps.newMutableMap();
		for (int i=2;i<=maxBase;++i)	{
			int pow=knownPowers.get(i);
			if (pow!=0) EulerUtils.increaseCounter(result,pow);
			else	{
				EulerUtils.increaseCounter(result,1);
				int product=i*i;
				int k=2;
				while (product<=maxBase)	{
					knownPowers.put(product,k);
					product*=i;
					++k;
				}
			}
		}
		return result;
	}
	
	private static long solve()	{
		IntIntMap cases=countCases(MAX_BASE);
		long result=0;
		IntIntCursor cursor=cases.cursor();
		PowerCounter counter=new PowerCounter(MAX_EXP);
		while (cursor.moveNext())	{
			int pow=cursor.key();
			int count=cursor.value();
			result+=counter.getIncrease(pow)*count;
		}
		return result;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler29::solve);
	}
}
