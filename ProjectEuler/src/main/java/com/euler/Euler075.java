package com.euler;

import com.euler.common.PythagoreanTriples;
import com.euler.common.Timing;
import com.euler.common.Triangle;

public class Euler75 {
	private final static int LIMIT=1500000;
	
	public static long solve()	{
		int[] found=new int[1+LIMIT];
		for (Triangle triple:PythagoreanTriples.getSimpleTriplesWithPerimeterLimit(LIMIT))	{
			int perimeter=(int)(triple.a+triple.b+triple.c);
			for (int i=perimeter;i<=LIMIT;i+=perimeter) ++found[i];
		}
		int result=0;
		for (int i=12;i<=LIMIT;++i) if (found[i]==1) ++result;
		return result;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler75::solve);
	}
}
