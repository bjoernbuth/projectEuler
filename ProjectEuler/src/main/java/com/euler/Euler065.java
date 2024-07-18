package com.euler;

import java.math.BigInteger;
import java.util.Iterator;

import com.euler.common.Convergents.ContinuedFraction;
import com.euler.common.Convergents.Convergent;
import com.euler.common.Timing;

public class Euler65 {
	private final static int N=100;
	
	private static class EContinuedFraction implements ContinuedFraction	{
		@Override
		public int getRoot() {
			return 2;
		}
		@Override
		public int getTerm(int n) {
			if (n%3==2) return 2*((n+1)/3);
			else return 1;
		}
	}
	
	private static long solve()	{
		Iterator<Convergent> convergents=new EContinuedFraction().iterator();
		for (int i=1;i<N;++i) convergents.next();	// This runs N-1 times.
		BigInteger toSum=convergents.next().p;
		int result=0;
		for (char c:toSum.toString().toCharArray()) result+=c-'0';
		return result;
	}

	public static void main(String[] args)	{
		Timing.time(Euler65::solve);
	}
}
