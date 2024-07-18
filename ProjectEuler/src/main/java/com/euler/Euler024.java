package com.euler;

import java.util.LinkedList;
import java.util.List;

import com.euler.common.EulerUtils;
import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler24 {
	private final static int POSITION=IntMath.pow(10,6)-1;	// The first one is the "0-th", so the millionth is actually the 999999-th.
	
	private static String solve()	{
		EulerUtils.FactorialCache cache=new EulerUtils.FactorialCache(10);
		List<Integer> digits=new LinkedList<>();
		for (int i=0;i<10;++i) digits.add(i);
		int currentPosition=POSITION;
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<10;++i)	{
			int factPosition=9-i;
			int factorial=(int)cache.get(factPosition);
			int index=currentPosition/factorial;
			currentPosition%=factorial;
			sb.append(digits.get(index));
			digits.remove(index);	// We are using index as an int, not an Integer!!
		}
		return sb.toString();
	}

	public static void main(String[] args)	{
		Timing.time(Euler24::solve);
	}
}
