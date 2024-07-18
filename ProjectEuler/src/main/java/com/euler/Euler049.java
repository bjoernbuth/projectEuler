package com.euler;

import com.euler.common.EulerUtils;
import com.euler.common.Primes;
import com.euler.common.Timing;
import com.koloboke.collect.map.IntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;

public class Euler49 {
	private static IntIntMap getDigits(int in)	{
		IntIntMap result=HashIntIntMaps.newMutableMap();
		while (in>0)	{
			EulerUtils.increaseCounter(result,in%10);
			in/=10;
		}
		return result;
	}
	
	private static boolean sameDigits(int in,IntIntMap toCompare)	{
		return getDigits(in).equals(toCompare);
	}
	
	private static String solve()	{
		boolean[] composites=Primes.sieve(10000);
		boolean add4=false;
		for (int i=1007;i<=3339;i+=add4?4:2,add4=!add4) if (i!=1487)	{
			if (composites[i]) continue;
			IntIntMap digits=getDigits(i/10);
			int i2=i+3330;
			if (composites[i2]||!sameDigits(i2/10,digits)) continue;
			int i3=i2+3330;
			if (composites[i3]||!sameDigits(i3/10,digits)) continue;
			return String.format("%d%d%d",i,i2,i3);
		}
		throw new RuntimeException("Not found!");
	}

	public static void main(String[] args)	{
		Timing.time(Euler49::solve);
	}
}
