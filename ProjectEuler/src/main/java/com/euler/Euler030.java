package com.euler;

import com.euler.common.CombinationIterator;
import com.euler.common.EulerUtils;
import com.euler.common.Timing;
import com.google.common.math.IntMath;
import com.koloboke.collect.map.IntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;

public class Euler30 {
	private static class FifthPowerVerifier	{
		private final static int[] POWERS=new int[10];
		static	{
			for (int i=0;i<10;++i) POWERS[i]=IntMath.pow(i,5);
		}
		public static int getVerifiedSum(int[] combination)	{
			int nonZeroValues=0;
			int sum=0;
			for (int i:combination)	{
				if (i>0) ++nonZeroValues;
				sum+=POWERS[i];
			}
			if (nonZeroValues<2) return 0;
			IntIntMap summed=EulerUtils.getDigitDistribution(sum);
			IntIntMap original=getDistributionFromDigits(combination);
			return summed.equals(original)?sum:0;
		}
		private static IntIntMap getDistributionFromDigits(int[] combination)	{
			IntIntMap result=HashIntIntMaps.newMutableMap();
			for (int d:combination) if (d!=0) EulerUtils.increaseCounter(result,d);
			return result;
		}
	}
	
	private static long solve()	{
		// I know I wrote it myself, but... I love this class.
		CombinationIterator iterator=new CombinationIterator(6,10,true,false);
		long sum=0;
		for (int[] combination:iterator) sum+=FifthPowerVerifier.getVerifiedSum(combination);
		return sum;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler30::solve);
	}
}
