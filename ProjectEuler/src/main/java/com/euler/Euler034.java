package com.euler;

import com.euler.common.CombinationIterator;
import com.euler.common.EulerUtils;
import com.euler.common.Timing;
import com.koloboke.collect.map.IntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;

public class Euler34 {
	private static class FactorialVerifier	{
		private final static int[] FACTORIALS=new int[10];
		static	{
			FACTORIALS[0]=1;
			for (int i=1;i<10;++i) FACTORIALS[i]=i*FACTORIALS[i-1];
		}
		private static int adjustDigits(int sum,int maxDigits)	{
			int n=sum;
			int digits=0;
			while (n>0)	{
				++digits;
				n/=10;
			}
			// The number has n digits, so we have counted (7-n) additional zeros!
			return sum+digits-7;
		}
		public static int getVerifiedSum(int[] combination)	{
			int nonZeroValues=0;
			int sum=0;
			for (int i:combination)	{
				if (i>0) ++nonZeroValues;
				sum+=FACTORIALS[i];
			}
			if (nonZeroValues<2) return 0;
			sum=adjustDigits(sum,7);
			IntIntMap summed=getDistributionFromNumber(sum);
			IntIntMap original=getDistributionFromDigits(combination);
			return summed.equals(original)?sum:0;
		}
		private static IntIntMap getDistributionFromNumber(int n)	{
			IntIntMap result=HashIntIntMaps.newMutableMap();
			while (n>0)	{
				int d=n%10;
				n/=10;
				if (d>0) EulerUtils.increaseCounter(result,d);
			}
			return result;
		}
		private static IntIntMap getDistributionFromDigits(int[] combination)	{
			IntIntMap result=HashIntIntMaps.newMutableMap();
			for (int d:combination) if (d!=0) EulerUtils.increaseCounter(result,d);
			return result;
		}
	}
	
	private static long solve()	{
		// I know I wrote it myself, but... I love this class.
		CombinationIterator iterator=new CombinationIterator(7,10,true,false);
		long sum=0;
		for (int[] combination:iterator) sum+=FactorialVerifier.getVerifiedSum(combination);
		return sum;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler34::solve);
	}
}
