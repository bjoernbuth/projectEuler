package com.euler;

import com.euler.common.Timing;

public class Euler76 {
	private final static int LIMIT=100;
	
	public static long solve()	{
		long[][] result=new long[LIMIT][1+LIMIT];
		for (int j=0;j<=LIMIT;++j) result[1][j]=1;
		for (int i=2;i<LIMIT;++i) for (int j=0;j<=LIMIT;++j) for (int k=j;k>=0;k-=i) result[i][j]+=result[i-1][k];
		return result[LIMIT-1][LIMIT];
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler76::solve);
	}
}
