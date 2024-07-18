package com.euler;

import com.euler.common.PythagoreanTriples;
import com.euler.common.Timing;
import com.euler.common.Triangle;

public class Euler39 {
	private final static int LIMIT=1000;
	
	public static long solve()	{
		int[] solutions=new int[1+LIMIT];
		for (Triangle triple:PythagoreanTriples.getSimpleTriplesWithPerimeterLimit(LIMIT))	{
			int perimeter=(int)(triple.a+triple.b+triple.c);
			int q=LIMIT/perimeter;
			for (int i=1;i<=q;++i) ++solutions[i*perimeter];
		}
		int max=-1;
		int maxPos=-1;
		for (int i=12;i<=LIMIT;++i) if (solutions[i]>max)	{
			max=solutions[i];
			maxPos=i;
		}
		return maxPos;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler39::solve);
	}
}
