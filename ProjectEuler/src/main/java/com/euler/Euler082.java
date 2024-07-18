package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler82 {
	public static int getMinimum(OptionalInt... values)	{
		OptionalInt firstNonEmpty=OptionalInt.empty();
		int i=0;
		while (firstNonEmpty.isEmpty())	{
			firstNonEmpty=values[i];
			++i;
		}
		int result=firstNonEmpty.getAsInt();
		for (;i<values.length;++i) if (values[i].isPresent()) result=Math.min(result,values[i].getAsInt());
		return result;
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler82.class.getResource("in8.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			int[][] originalMatrix=Euler81.parseLines(lines);
			int[][] result=Euler81.arraySameSize(originalMatrix);
			result[0][0]=originalMatrix[0][0];
			int N=result.length;
			int M=result[0].length;
			for (int i=1;i<N;++i) result[i][0]=originalMatrix[i][0];
			for (int j=1;j<M;++j)	{
				for (int i=0;i<N;++i) result[i][j]=result[i][j-1]+originalMatrix[i][j];
				boolean anyChange=true;
				while (anyChange)	{
					anyChange=false;
					for (int i=0;i<N;++i)	{
						int prev=result[i][j];
						OptionalInt left=OptionalInt.of(result[i][j-1]);
						OptionalInt up=(i==0)?OptionalInt.empty():OptionalInt.of(result[i-1][j]);
						OptionalInt down=(i==N-1)?OptionalInt.empty():OptionalInt.of(result[i+1][j]);
						int curr=getMinimum(left,up,down)+originalMatrix[i][j];
						if (curr<prev)	{
							anyChange=true;
							result[i][j]=curr;
						}
					}
				}
			}
			int min=result[0][M-1];
			for (int i=1;i<M;++i) min=Math.min(min,result[i][M-1]);
			return min;
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler82::solve);
	}
}
