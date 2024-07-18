package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler89 {
	// All of these are replaceable by a string of exactly two characters.
	private final static List<String> REPLACEABLE=Arrays.asList("VIIII","LXXXX","DCCCC","IIII","XXXX","CCCC");
	
	private static long solve()	{
		try	{
			URL resource=Euler89.class.getResource("in89.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			long result=0;
			for (String line:lines)	{
				String value=line;
				for (String str:REPLACEABLE) for (;;)	{
					int pos=value.indexOf(str);
					if (pos==-1) break;
					result+=str.length()-2;
					value=value.replaceFirst(str,"_");
				}
			}
			return result;
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler89::solve);
	}
}
