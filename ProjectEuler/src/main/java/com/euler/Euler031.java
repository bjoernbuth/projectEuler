package com.euler;

import java.util.Arrays;

import com.euler.common.Timing;

public class Euler31 {
	private final static int LIMIT=200;
	private final static int[] COINS=new int[] {2,5,10,20,50,100,200};
	
	private static long solve()	{
		long[] current=new long[1+LIMIT];
		long[] previous=new long[1+LIMIT];
		// For 1p:
		Arrays.fill(current,1l);
		for (int c:COINS)	{
			long[] swap=current;
			current=previous;
			previous=swap;
			for (int i=0;i<=LIMIT;++i)	{
				int value=0;
				for (int k=i;k>=0;k-=c) value+=previous[k];
				current[i]=value;
			}
		}
		return current[LIMIT];
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler31::solve);
	}
}
