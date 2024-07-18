package com.euler;

import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler53 {
	private final static int END=100;
	private final static long LIMIT=LongMath.pow(10l,6);
	
	private static int[] generateCombinatorials(int n,int maxK)	{
		int[] result=new int[1+maxK];
		result[0]=1;
		for (int k=1;k<=maxK;++k) result[k]=(result[k-1]*(n+1-k))/k;
		return result;
	}
	
	private static int[] generateNextCombinatorial(int[] previous)	{
		int N=previous.length;
		while (previous[N-2]+previous[N-1]>=LIMIT) --N;
		int[] result=new int[N];
		result[0]=1;
		for (int i=1;i<N;++i) result[i]=previous[i-1]+previous[i];
		return result;
	}
	
	private static long solve()	{
		int[] array=generateCombinatorials(23,10);
		int result=4;
		for (int i=24;i<=END;++i)	{
			array=generateNextCombinatorial(array);
			result+=i+1-2*array.length;
		}
		return result;
	}

	public static void main(String[] args)	{
		Timing.time(Euler53::solve);
	}
}
