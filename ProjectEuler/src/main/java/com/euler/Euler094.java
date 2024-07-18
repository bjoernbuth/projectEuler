package com.euler;

import java.util.OptionalLong;

import com.euler.common.EulerUtils;
import com.euler.common.Timing;
import com.google.common.math.LongMath;

public class Euler94 {
	private final static long LIMIT=LongMath.pow(10l,9);
	
	private final static long HALF_LIMIT=LIMIT/2;
	
	private static OptionalLong checkTriple(long m,long n)	{
		long mm=m*m;
		long nn=n*n;
		long a=mm-nn;
		long b=2*m*n;
		long c=mm+nn;
		if ((a+c<=HALF_LIMIT)&&(((a+a)==(c+1))||((a+a)==(c-1)))) return OptionalLong.of(2*(a+c));
		else if ((b+c<=HALF_LIMIT)&&(((b+b)==(c+1))||((b+b)==(c-1)))) return OptionalLong.of(2*(b+c));
		else	{
			return OptionalLong.empty();
		}
	}
	
	private static long solve()	{
		long result=16;	// First result, coming from m=2, n=1.
		long base=4;
		long starting=1;
		boolean finished=false;
		while (!finished)	{
			for (long n=starting;n<base;n+=2) if (EulerUtils.areCoprime(base,n))	{
				OptionalLong triple=checkTriple(base,n);
				if (triple.isPresent())	{
					long perimeter=triple.getAsLong();
					if (perimeter>LIMIT)	{
						finished=true;
						break;
					}	else result+=perimeter;
				}	else if (2*base*(base+n)>LIMIT)	{
					finished=true;
					break;
				}
			}
			if (finished) break;
			long last=0;
			for (long m=base+1;;m+=2) if (EulerUtils.areCoprime(m,base))	{
				OptionalLong triple=checkTriple(m,base);
				if (triple.isPresent())	{
					long perimeter=triple.getAsLong();
					if (perimeter>LIMIT)	{
						finished=true;
						break;
					}	else	{
						result+=perimeter;
						last=m;
						break;
					}
				}	else if (2*m*(m+base)>LIMIT)	{
					finished=true;
					break;
				}
			}
			if (finished) break;
			for (long m=((base%2)==1)?(2*last):(2*last+1);;m+=2) if (EulerUtils.areCoprime(m,base))	{
				OptionalLong triple=checkTriple(m,base);
				if (triple.isPresent())	{
					long perimeter=triple.getAsLong();
					if (perimeter>LIMIT)	{
						finished=true;
						break;
					}	else	{
						result+=perimeter;
						starting=base+2;
						base=m;
						break;
					}
				}	else if (2*m*(m+base)>LIMIT)	{
					finished=true;
					break;
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler94::solve);
	}
}
