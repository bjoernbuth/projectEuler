package com.euler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.euler.common.EulerUtils;
import com.euler.common.Timing;
import com.koloboke.collect.map.IntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;

public class Euler62 {
	private final static int MIN_COUNT=5;
	
	private static IntIntMap getDigitSummary(long in)	{
		IntIntMap result=HashIntIntMaps.newMutableMap();
		while (in>0)	{
			EulerUtils.increaseCounter(result,(int)(in%10));
			in/=10;
		}
		return result;
	}
	
	private static long solve()	{
		Map<IntIntMap,List<Long>> cache=new HashMap<>();
		for (long i=1;;++i)	{
			long cube=i*i*i;
			IntIntMap digitSummary=getDigitSummary(cube);
			List<Long> currentList=cache.computeIfAbsent(digitSummary,(IntIntMap unusedKey)->new ArrayList<>());
			currentList.add(cube);
			if (currentList.size()>=MIN_COUNT) return currentList.get(0);
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler62::solve);
	}
}
