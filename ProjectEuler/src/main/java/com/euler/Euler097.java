package com.euler;

import java.math.BigInteger;

import com.euler.common.BigIntegerUtils;
import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler97 {
	private final static int EXP=7830457;
	private final static int FACTOR=28433;
	private final static long MOD=LongMath.pow(10l,10);
	
	private static long solve()	{
		long result=BigIntegerUtils.powMod(BigInteger.TWO,EXP,BigInteger.valueOf(MOD)).longValue()*FACTOR;
		return (result+1)%MOD;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler97::solve);
	}
}
