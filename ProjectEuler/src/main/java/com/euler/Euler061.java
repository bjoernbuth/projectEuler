package com.euler;

import java.math.RoundingMode;

import com.euler.common.Timing;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.math.LongMath;
import com.koloboke.collect.set.LongSet;
import com.koloboke.collect.set.hash.HashLongSets;

public class Euler61 {
	private static long reversePolygonal(int k,long value)	{
		// (k-2)n^2+(4-k)n=2*value
		// (k-2)n^2+(4-k)n-2*value=0
		// n=[(k-4)+sqrt((4-k)^2+8*value*(k-2))]/(2k-4)
		long k4=k-4;
		long sqOperand=k4*k4+8*value*(k-2);
		return (k-4+LongMath.sqrt(sqOperand,RoundingMode.DOWN))/(2*k-4);
	}
	
	private static long getPolygonal(int k,long value)	{
		return (((k-2)*value+4-k)*value)/2;
	}
	
	private static class PolygonalNumber	{
		public final int polygon;
		public final long number;
		public final long next;
		public PolygonalNumber(int polygon,long number)	{
			this.polygon=polygon;
			this.number=number;
			next=number%100;
		}
	}
	
	private static boolean findChain(Multimap<Long,PolygonalNumber> polygonals,long chainStart,long chainEnd,boolean[] found,LongSet resultSet) {
		for (PolygonalNumber poly:polygonals.get(chainStart)) if (!found[poly.polygon])	{
			found[poly.polygon]=true;
			resultSet.add(poly.number);
			if (resultSet.size()==found.length+1)	{
				if (poly.next==chainEnd) return true;
			}	else if (findChain(polygonals,poly.next,chainEnd,found,resultSet)) return true;
			resultSet.removeLong(poly.number);
			found[poly.polygon]=false;
		}
		return false;
	}

	private static long solve()	{
		Multimap<Long,PolygonalNumber> polygonals=MultimapBuilder.hashKeys().arrayListValues().build();
		for (int k=3;k<=7;++k)	{
			long min=1+reversePolygonal(k,1000);
			long max=reversePolygonal(k,10000);
			for (long i=min;i<=max;++i)	{
				long polygonal=getPolygonal(k,i);
				PolygonalNumber obj=new PolygonalNumber(k-3,polygonal);
				polygonals.put(polygonal/100,obj);
			}
		}
		boolean[] found=new boolean[5];
		long min=1+reversePolygonal(8,1000);
		long max=reversePolygonal(8,10000);
		LongSet resultSet=HashLongSets.newMutableSet();
		for (long i=min;i<=max;++i)	{
			long polygonal=getPolygonal(8,i);
			long firstHalf=polygonal/100;
			long secondHalf=polygonal%100;
			resultSet.add(polygonal);
			if (findChain(polygonals,secondHalf,firstHalf,found,resultSet)) break;
			resultSet.removeLong(polygonal);
		}
		if (resultSet.size()!=6) throw new RuntimeException("Not found.");
		long result=0l;
		for (long pol:resultSet) result+=pol;
		return result;
	}

	public static void main(String[] args)	{
		Timing.time(Euler61::solve);
	}
}
