package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;
import com.koloboke.collect.map.CharObjCursor;
import com.koloboke.collect.map.CharObjMap;
import com.koloboke.collect.map.hash.HashCharObjMaps;
import com.koloboke.collect.set.CharSet;
import com.koloboke.collect.set.hash.HashCharSets;

public class Euler79 {
	private static String solve()	{
		try	{
			URL resource=Euler79.class.getResource("in79.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			CharObjMap<CharSet> precedence=HashCharObjMaps.newMutableMap();
			for (String l:lines)	{
				char[] line=l.toCharArray();
				for (int i=line.length-1;i>=0;--i)	{
					// We include the case i=0 because we want to have an empty set for the initial character.
					CharSet thisCharPrecedence=precedence.computeIfAbsent(line[i],(char unused)->HashCharSets.newMutableSet());
					for (int j=i-1;j>=0;--j) thisCharPrecedence.add(line[j]);
				}
			}
			StringBuilder result=new StringBuilder();
			CharSet currentlyAdded=HashCharSets.newMutableSet();
			while (!precedence.isEmpty())	{
				CharObjCursor<CharSet> cursor=precedence.cursor();
				while (cursor.moveNext())	{
					CharSet requisites=cursor.value();
					if (currentlyAdded.containsAll(requisites))	{
						char toAdd=cursor.key();
						result.append(toAdd);
						currentlyAdded.add(toAdd);
						cursor.remove();
					}
				}
			}
			return result.toString();
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler79::solve);
	}
}
