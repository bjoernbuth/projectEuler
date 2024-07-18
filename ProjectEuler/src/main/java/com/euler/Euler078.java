package com.euler;

import com.euler.common.Timing;
import com.google.common.math.IntMath;
import com.koloboke.collect.map.IntIntMap;
import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;
import com.koloboke.collect.map.hash.HashIntObjMaps;

public class Euler78 {
	private final static int MOD=IntMath.pow(10,6);
	
	private static class GeneralizedPentagonals	{
		public final int minus;
		public final int plus;
		public GeneralizedPentagonals(int in)	{
			plus=(in*(3*in+1))/2;
			minus=plus-in;
		}
	}
	
	public static long solve()	{
		IntIntMap partitionCache=HashIntIntMaps.newMutableMap();
		IntObjMap<GeneralizedPentagonals> pentagonals=HashIntObjMaps.newMutableMap();
		partitionCache.put(0,1);
		for (int i=1;;++i)	{
			int sum=0;
			for (int j=1;;++j)	{
				GeneralizedPentagonals gp=pentagonals.computeIfAbsent(j,GeneralizedPentagonals::new);
				if (gp.minus>i) break;
				int toAdd=partitionCache.get(i-gp.minus);
				if ((j%2)==1) sum+=toAdd;
				else sum-=toAdd;
				if (gp.plus>i) break;
				toAdd=partitionCache.get(i-gp.plus);
				if ((j%2)==1) sum+=toAdd;
				else sum-=toAdd;
			}
			sum%=MOD;
			if (sum==0) return i;
			partitionCache.put(i,sum);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler78::solve);
	}
}
