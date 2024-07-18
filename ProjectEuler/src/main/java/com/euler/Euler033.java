package com.euler;

import com.euler.common.EulerUtils;
import com.euler.common.Timing;

public class Euler33 {
	private static long solve()	{
		long num=1;
		long den=1;
		for (int a=1;a<9;++a) for (int b=a+1;b<=9;++b)	{
			int i=10*a+b;
			for (int c=1;c<b;++c) {
				int j=10*b+c;
				if ((i*c)==(a*j))	{
					num*=a;
					den*=c;
				}
			}
		}
		return den/EulerUtils.gcd(num,den);
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler33::solve);
	}
}
