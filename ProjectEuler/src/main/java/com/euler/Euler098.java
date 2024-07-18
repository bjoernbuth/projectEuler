package com.euler;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.euler.common.EulerUtils.Pair;
import com.euler.common.Timing;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.common.math.LongMath;
import com.koloboke.collect.map.CharIntMap;
import com.koloboke.collect.map.LongObjMap;
import com.koloboke.collect.map.hash.HashCharIntMaps;
import com.koloboke.collect.map.hash.HashLongObjMaps;

public class Euler98 {
	private static class LongWithDigits	{
		public final long number;
		public final List<Integer> digits;
		public LongWithDigits(long in)	{
			number=in;
			digits=new ArrayList<>();
			while (in>0)	{
				// Note the reverse addition order.
				digits.add(0,(int)(in%10));
				in/=10;
			}
		}
		public Multiset<Integer> getDigits()	{
			return HashMultiset.create(digits);
		}
		@Override
		public String toString()	{
			return String.valueOf(number);
		}
	}
	
	private static Multimap<Integer,String> sortWords(String wordChain)	{
		Multimap<Integer,String> result=MultimapBuilder.treeKeys(Comparator.<Integer>reverseOrder()).arrayListValues().build();
		for (String split:wordChain.split(","))	{
			String unquoted=split.substring(1,split.length()-1);
			result.put(unquoted.length(),unquoted);
		}
		return result;
	}
	
	private static Multiset<Character> getSummary(String word)	{
		Multiset<Character> result=HashMultiset.create(word.length());
		for (char c:word.toCharArray()) result.add(c);
		return result;
	}
	
	private static <T,U> Table<Multiset<Integer>,Multiset<T>,List<U>> summarizeAndFilter(Collection<U> strings,Function<U,Multiset<T>> summarizer)	{
		Table<Multiset<Integer>,Multiset<T>,List<U>> result=HashBasedTable.create();
		for (U word:strings)	{
			Multiset<T> summary=summarizer.apply(word);
			Multiset<Integer> keyCount=HashMultiset.create();
			for (T c:summary.elementSet()) keyCount.add(summary.count(c));
			List<U> list=result.get(keyCount,summary);
			if (list==null)	{
				list=new ArrayList<>();
				result.put(keyCount,summary,list);
			}
			list.add(word);
		}
		List<Pair<Multiset<Integer>,Multiset<T>>> toDelete=new ArrayList<>();
		for (Map.Entry<Multiset<Integer>,Map<Multiset<T>,List<U>>> entry:result.rowMap().entrySet())	{
			Map<Multiset<T>,List<U>> valueMap=entry.getValue();
			List<Multiset<T>> toDelete2=new ArrayList<>();
			for (Map.Entry<Multiset<T>,List<U>> entry2:valueMap.entrySet()) if (entry2.getValue().size()<2) toDelete.add(new Pair<>(entry.getKey(),entry2.getKey()));
			for (Multiset<T> multiset:toDelete2) valueMap.remove(multiset);
		}
		for (Pair<Multiset<Integer>,Multiset<T>> pair:toDelete) result.remove(pair.first,pair.second);
		return result;
	}
	
	private static List<LongWithDigits> generateSquaresOfLength(int n)	{
		long pow=LongMath.pow(10l,n-1);
		long first=LongMath.sqrt(pow,RoundingMode.CEILING);
		long firstNot=LongMath.sqrt(10*pow,RoundingMode.CEILING);
		List<LongWithDigits> result=new ArrayList<>((int)(firstNot-first));
		for (long i=first;i<firstNot;++i) result.add(new LongWithDigits(i*i));
		return result;
	}
	
	private static OptionalLong max(OptionalLong l1,OptionalLong l2)	{
		return (l2.isPresent()&&((!l1.isPresent())||(l1.getAsLong()<l2.getAsLong())))?l2:l1;
	}
	
	private static LongObjMap<LongWithDigits> getMap(List<LongWithDigits> nums)	{
		LongObjMap<LongWithDigits> result=HashLongObjMaps.newMutableMap();
		for (LongWithDigits l:nums) result.put(l.number,l);
		return result;
	}
	
	private static OptionalLong tryAndMatch(String s1,String s2,LongWithDigits num,LongObjMap<LongWithDigits> nums)	{
		CharIntMap digitAssignment=HashCharIntMaps.newMutableMap();
		for (int i=0;i<s1.length();++i)	{
			char c=s1.charAt(i);
			int n=num.digits.get(i).intValue();
			if (digitAssignment.containsKey(c))	{
				int assigned=digitAssignment.get(c);
				if (assigned!=n) return OptionalLong.empty();
			}	else digitAssignment.put(c,n);
		}
		long candidate=0;
		for (int i=0;i<s1.length();++i)	{
			candidate=(10*candidate)+digitAssignment.get(s2.charAt(i));
		}
		LongWithDigits anagram=nums.get(candidate);
		return (anagram==null)?OptionalLong.empty():OptionalLong.of(Math.max(num.number,anagram.number));
	}
	
	private static OptionalLong tryAndMatch(List<String> strs,LongObjMap<LongWithDigits> nums)	{
		OptionalLong maxValue=OptionalLong.empty();
		int N=strs.size();
		for (int i=0;i<N-1;++i) for (int j=i+1;j<N;++j)	for (LongWithDigits l:nums.values()) maxValue=max(maxValue,tryAndMatch(strs.get(i),strs.get(j),l,nums));
		return maxValue;
	}
	
	private static OptionalLong findMatch(Table<Multiset<Integer>,Multiset<Character>,List<String>> wordSummary,Table<Multiset<Integer>,Multiset<Integer>,List<LongWithDigits>> squareSummary)	{
		OptionalLong maxValue=OptionalLong.empty();
		for (Map.Entry<Multiset<Integer>,Map<Multiset<Character>,List<String>>> entry:wordSummary.rowMap().entrySet())	{
			Multiset<Integer> key=entry.getKey();
			Map<Multiset<Character>,List<String>> wordAnagrams=entry.getValue();
			Map<Multiset<Integer>,List<LongWithDigits>> numAnagrams=squareSummary.row(key);
			if (numAnagrams.isEmpty()) continue;
			for (List<String> l1:wordAnagrams.values()) for (List<LongWithDigits> l2:numAnagrams.values()) maxValue=max(maxValue,tryAndMatch(l1,getMap(l2)));
		}
		return maxValue;
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler98.class.getResource("in98.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			if (lines.size()>1) throw new RuntimeException("Unexpected format.");
			Multimap<Integer,String> words=sortWords(lines.get(0));
			for (Map.Entry<Integer,Collection<String>> entry:words.asMap().entrySet())	{
				Table<Multiset<Integer>,Multiset<Character>,List<String>> summary=summarizeAndFilter(entry.getValue(),Euler98::getSummary);
				if (summary.isEmpty()) continue;
				Table<Multiset<Integer>,Multiset<Integer>,List<LongWithDigits>> squaresSummary=summarizeAndFilter(generateSquaresOfLength(entry.getKey()),LongWithDigits::getDigits);
				if (squaresSummary.isEmpty()) continue;
				OptionalLong result=findMatch(summary,squaresSummary);
				if (result.isPresent()) return result.getAsLong();
			}
			return -1l;
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler98::solve);
	}
}
