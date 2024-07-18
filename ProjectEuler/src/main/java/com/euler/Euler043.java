package com.euler;

import com.euler.common.Timing;

public class Euler43 {
	private static long solve()	{
		// See the analysis in the PDF!!!
		long n1=1430952867l;
		long n2=4130952867l;
		long n3=1406357289l;
		long n4=1460357289l;
		long n5=4106357289l;
		long n6=4160357289l;
		return n1+n2+n3+n4+n5+n6;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler43::solve);
	}
}
