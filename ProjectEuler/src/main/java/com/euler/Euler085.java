package com.euler;

import java.math.RoundingMode;

import com.euler.common.Timing;
import com.google.common.math.LongMath;
import com.koloboke.collect.map.LongLongMap;
import com.koloboke.collect.map.hash.HashLongLongMaps;

public class Euler85 {
	private final static long GOAL=2*LongMath.pow(10l,6);
	
	private static long getTriangular(long in)	{
		return ((in+1)*in)/2;
	}
	
	private static long getNearLowTriangular(long in)	{
		return (LongMath.sqrt(8*in+1,RoundingMode.DOWN)-1)/2;
	}
	
	private static long solve()	{
		long currentBestSolution=0;
		long currentArea=0;
		LongLongMap triangularNumberCache=HashLongLongMaps.newMutableMap();
		for (int i=1;;++i)	{
			long triangular=triangularNumberCache.computeIfAbsent(i,Euler85::getTriangular);
			long search=getNearLowTriangular(GOAL/triangular);
			if (search<i)	{
				System.out.println("He llegado al "+i+". Termino.");
				return currentArea;
			}
			for (;;++search)	{
				long otherTriangular=triangularNumberCache.computeIfAbsent(search,Euler85::getTriangular);
				long product=triangular*otherTriangular;
				if (Math.abs(product-GOAL)<Math.abs(currentBestSolution-GOAL))	{
					System.out.println("Solución guay: "+i+"*"+search+".");
					currentBestSolution=product;
					currentArea=i*search;
				}
				if (product>GOAL) break;
			}
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler85::solve);
	}
}
