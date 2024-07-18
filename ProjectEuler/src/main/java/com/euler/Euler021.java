package com.euler;

import com.euler.common.DivisorHolder;
import com.euler.common.Primes;
import com.euler.common.Timing;
import com.koloboke.collect.map.IntIntCursor;
import com.koloboke.collect.map.IntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;

public class Euler21 {
	private final static int LIMIT=10000;
	
	private static long solve()	{
		IntIntMap abundantSums=HashIntIntMaps.newMutableMap();
		IntIntMap defectiveSums=HashIntIntMaps.newMutableMap();
		int[] firstPrimes=Primes.firstPrimeSieve(LIMIT);
		for (int i=2;i<=LIMIT;++i)	{
			DivisorHolder divs=DivisorHolder.getFromFirstPrimes(i,firstPrimes);
			int divSum=(int)(divs.getDivisorSum()-i);
			if (divSum>i) abundantSums.put(i,divSum);
			else if (divSum<i) defectiveSums.put(i,divSum);
		}
		long result=0l;
		IntIntCursor cursor=abundantSums.cursor();
		while (cursor.moveNext()) if (defectiveSums.get(cursor.value())==cursor.key()) result+=cursor.key()+cursor.value();
		return result;
	}

	public static void main(String[] args)	{
		Timing.time(Euler21::solve);
	}
}
