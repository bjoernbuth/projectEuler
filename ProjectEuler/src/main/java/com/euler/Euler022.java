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

public class Euler22 {
	private static long nameValue(String name)	{
		long result=0;
		for (char c:name.toCharArray()) result+=c-'A'+1;
		return result;
	}
	
	private static String unquote(String in)	{
		return in.substring(1,in.length()-1);
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler22.class.getResource("in22.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			if (lines.size()>1) throw new RuntimeException("Unexpected format.");
			List<String> names=Arrays.asList(lines.get(0).split(","));
			for (int i=0;i<names.size();++i) names.set(i,unquote(names.get(i)));
			names.sort(null);
			long result=0;
			for (int i=0;i<names.size();++i) result+=(i+1)*nameValue(names.get(i));
			return result;
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}

	public static void main(String[] args)	{
		Timing.time(Euler22::solve);
	}
}
