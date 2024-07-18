package com.euler;

import java.util.ArrayList;
import java.util.List;

import com.euler.common.Primes;
import com.euler.common.Primes.RabinMiller;
import com.euler.common.Timing;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import com.koloboke.collect.LongCursor;
import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.koloboke.collect.set.LongSet;
import com.koloboke.collect.set.hash.HashLongSets;

public class Euler60 {
	private final static int DEEPEST_MATCH=5;
	
	private final static int[] WITNESSES=new int[] {2,7,61};
	private final static RabinMiller RABIN_MILLER=new RabinMiller();
	private final static int MAX_PRIME=IntMath.pow(10,6);
	
	private static long concat(long n1,long n2)	{
		return n1*LongMath.pow(10,(int)Math.ceil(Math.log10(n2)))+n2;
	}
	
	private static boolean checkMatch(long v1,long v2)	{
		return RABIN_MILLER.isPrime(concat(v1,v2),WITNESSES)&&RABIN_MILLER.isPrime(concat(v2,v1),WITNESSES);
	}
	
	private static class Tree	{
		public final long value;
		public final int N;
		private final List<Tree> children;
		public Tree(long value,int N)	{
			this.value=value;
			this.N=N;
			children=(N<=0)?null:new ArrayList<>();
		}
		public boolean add(long in,LongSet result)	{
			if (N<=0)	{
				result.add(value);
				return true;
			}
			for (Tree tree:children) if (checkMatch(in,tree.value)&&tree.add(in,result))	{
				result.add(value);
				return true;
			}
			Tree newChild=new Tree(in,N-1);
			children.add(newChild);
			return false;
		}
	}
	
	private static long solve()	{
		int[] primes=Primes.listIntPrimesAsArray(MAX_PRIME);
		IntObjMap<Tree> foundMatches=HashIntObjMaps.newMutableMap();
		LongSet resultPrimes=HashLongSets.newMutableSet();
		for (int i=1;;++i)	{
			for (int j=1;j<i;++j) if (checkMatch(primes[i],primes[j]))	{
				resultPrimes.clear();
				if (foundMatches.get(primes[j]).add(primes[i],resultPrimes))	{
					long result=primes[i];
					LongCursor cursor=resultPrimes.cursor();
					while (cursor.moveNext()) result+=cursor.elem();
					return result;
				}
			}
			foundMatches.put(primes[i],new Tree(primes[i],DEEPEST_MATCH-2));
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler60::solve);
	}
}
