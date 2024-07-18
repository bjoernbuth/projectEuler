package com.euler;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.euler.common.Primes;

public class Euler221 {
	private final static int POSITION = 150000;
	private final static int TRIAL = 200000;
	private final static double LIMIT = 63 * Math.log(2);

	private static List<Long> primes;

	private static void addDivisor(SortedSet<Long> previous, long prime) {
		SortedSet<Long> copy = new TreeSet<>(previous);
		for (long d : copy)
			previous.add(d * prime);
	}

	private static SortedSet<Long> getAllDivisorsList(long n) {
		SortedSet<Long> allDivisors = new TreeSet<>();
		allDivisors.add(1l);
		for (long p : primes)
			while ((n % p) == 0) {
				addDivisor(allDivisors, p);
				n /= p;
				if (n == 1l)
					return allDivisors;
			}
		addDivisor(allDivisors, n);
		return allDivisors;
	}

	private static SortedSet<Long> getHalfDivList(long n, long h) {
		SortedSet<Long> allDivs = getAllDivisorsList(n);
		return allDivs.headSet(h);
	}

	public static void main(String[] args) {
		primes = new ArrayList<>();
		boolean[] composites = Primes.sieve(TRIAL);
		for (int i = 2; i < composites.length; ++i)
			if (!composites[i])
				primes.add((long) i);
		NavigableMap<Long, List<Long>> alexandrians = new TreeMap<>();
		for (long i = 1; i <= TRIAL; ++i) {
			long sq = 1 + (i * i);
			Collection<Long> divs = getHalfDivList(sq, i + 1);
			for (long j : divs) {
				long f1 = i;
				long f2 = i + j;
				long f3 = i + (sq / j);
				if (Math.log(f1) + Math.log(f2) + Math.log(f3) >= LIMIT)
					continue;
				long key = f1 * f2 * f3;
				List<Long> value = alexandrians.get(key);
				if (value == null)
					value = new ArrayList<>();
				value.add(i);
				alexandrians.put(key, value);
			}
		}
		try (PrintStream ps = new PrintStream(new File("D:\\out.txt"))) {
			long maxKey = 0;
			Iterator<Map.Entry<Long, List<Long>>> entries = alexandrians.entrySet().iterator();
			for (int i = 0; i < Math.min(POSITION, alexandrians.size()); ++i) {
				Map.Entry<Long, List<Long>> entry = entries.next();
				if (i == (POSITION - 1))
					System.out.println("Result: " + entry.getKey());
				ps.println("" + i + " => " + entry.getKey() + " " + entry.getValue());
				for (long l : entry.getValue())
					maxKey = Math.max(maxKey, l);
			}
			System.out.println("Max key used: " + maxKey + ".");
		} catch (IOException exc) {
			System.out.println("Mierda :(.");
		}
	}

	/*
	 * public static void main(String[] args) {
	 * primes=new ArrayList<>();
	 * boolean[] composites=Primes.sieve(TRIAL);
	 * for (int i=2;i<composites.length;++i) if (!composites[i])
	 * primes.add((long)i);
	 * SortedSet<Long> alexandrians=new TreeSet<>();
	 * for (long i=1;i<=TRIAL;++i) {
	 * long sq=1+(i*i);
	 * Collection<Long> divs=getHalfDivList(sq,i+1);
	 * for (long j:divs) {
	 * long f1=i;
	 * long f2=i+j;
	 * long f3=i+(sq/j);
	 * if (Math.log(f1)+Math.log(f2)+Math.log(f3)>=LIMIT) continue;
	 * alexandrians.add(f1*f2*f3);
	 * }
	 * }
	 * Long[] ordered=alexandrians.toArray(new Long[0]);
	 * System.out.println(ordered[POSITION-1]);
	 * try (PrintStream ps=new PrintStream(new File("D:\\out.txt"))) {
	 * for (int i=0;i<Math.min(2*POSITION,ordered.length);++i)
	 * ps.println(""+i+" => "+ordered[i]);
	 * } catch (IOException exc) {
	 * System.out.println("Mierda :(.");
	 * }
	 * }
	 */
}
