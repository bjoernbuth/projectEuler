package com.euler;

import com.euler.common.EulerUtils;
import com.euler.common.Primes;
import com.euler.common.Timing;
import com.google.common.math.IntMath;

public class Euler70 {
	private final static int LIMIT=IntMath.pow(10,7);
	
	private static boolean isAnagram(int a,int b)	{
		return EulerUtils.getDigitDistribution(a).equals(EulerUtils.getDigitDistribution(b));
	}
	
	private static long solve()	{
		int[] primes=Primes.listIntPrimesAsArray(LIMIT);
		double minQ=2;
		int minNumber=0;
		for (int i=0;i<primes.length;++i)	{
			if (primes[i]%3!=2) continue;
			if (primes[i]*primes[i]>LIMIT) break;
			for (int j=i+1;j<primes.length;++j)	{
				int prod=primes[i]*primes[j];
				if (prod%9!=7) continue;
				if (prod>LIMIT) break;	// Next i.
				int totient=(primes[i]-1)*(primes[j]-1);
				double q=((double)prod)/((double)totient);
				if ((q<minQ)&&isAnagram(prod,totient))	{
					minQ=q;
					minNumber=prod;
				}
			}
		}
		return minNumber;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler70::solve);
	}
}
