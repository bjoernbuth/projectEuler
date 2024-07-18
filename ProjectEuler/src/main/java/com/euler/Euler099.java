package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler99 {
	private static double getNumericValue(String line)	{
		String[] split=line.split(",");
		long n1=Long.parseLong(split[0]);
		long n2=Long.parseLong(split[1]);
		return n2*Math.log(n1);
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler99.class.getResource("in99.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			double maxValue=0;
			int maxLine=0;
			for (int i=0;i<lines.size();++i)	{
				double value=getNumericValue(lines.get(i));
				if (value>maxValue)	{
					maxValue=value;
					maxLine=i+1;	// Index starts as 1? PREPOSTEROUS.
				}
			}
			return maxLine;
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler99::solve);
	}
}
