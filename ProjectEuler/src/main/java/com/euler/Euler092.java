package com.euler;

import com.euler.common.Timing;
import com.google.common.math.IntMath;
import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;

public class Euler92 {
	private final static int LIMIT=IntMath.pow(10,7);
	private final static IntSet SET_89=HashIntSets.newImmutableSetOf(4,16,20,37,42,58,89,145);
	
	private static int nextInChain(int in)	{
		int result=0;
		while (in>0)	{
			int d=(in%10);
			result+=d*d;
			in/=10;
		}
		return result;
	}
	
	private static int getValue(int[] values,int n)	{
		if (values[n]>0) return values[n];
		int next=nextInChain(n);
		int value=getValue(values,next);
		values[n]=value;
		return value;
	}
	
	private static long solve()	{
		int[] values=new int[LIMIT];
		SET_89.forEach((int x)->values[x]=89);
		values[1]=1;
		int count=0;
		for (int i=2;i<LIMIT;++i) if (getValue(values,i)==89) ++count;
		return count;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler92::solve);
	}
}
