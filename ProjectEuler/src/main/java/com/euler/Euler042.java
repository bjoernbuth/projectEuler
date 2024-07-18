package com.euler;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;
import com.google.common.math.LongMath;
import com.koloboke.collect.map.LongObjMap;
import com.koloboke.collect.map.hash.HashLongObjMaps;

public class Euler42 {
	private static long nameValue(String name)	{
		long result=0;
		for (char c:name.toCharArray()) if (c!='\"') result+=c-'A'+1;
		return result;
	}
	
	private static Boolean isTriangular(long in)	{
		long operand=8*in+1;
		long sq=LongMath.sqrt(operand,RoundingMode.DOWN);
		return sq*sq==operand;
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler42.class.getResource("in42.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			if (lines.size()>1) throw new RuntimeException("Unexpected format.");
			List<String> words=Arrays.asList(lines.get(0).split(","));
			int counter=0;
			LongObjMap<Boolean> cache=HashLongObjMaps.newMutableMap();
			for (String word:words)	{
				long value=nameValue(word);
				if (cache.computeIfAbsent(value,Euler42::isTriangular)) ++counter;
			}
			return counter;
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler42::solve);
	}
}
