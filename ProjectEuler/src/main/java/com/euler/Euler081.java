package com.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.euler.common.Timing;

public class Euler81 {
	public static int[][] parseLines(List<String> in)	{
		int[][] result=new int[in.size()][];
		int size=0;
		for (int i=0;i<in.size();++i)	{
			String[] unparsed=in.get(i).split(",");
			if (size==0) size=unparsed.length;
			else if (unparsed.length!=size) throw new RuntimeException("Inconsistent lengths.");
			result[i]=new int[size];
			for (int j=0;j<size;++j) result[i][j]=Integer.parseInt(unparsed[j]);
		}
		return result;
	}
	
	public static int[][] arraySameSize(int[][] in)	{
		int[][] result=new int[in.length][];
		for (int i=0;i<in.length;++i) result[i]=new int[in[i].length];
		return result;
	}
	
	private static long solve()	{
		try	{
			URL resource=Euler81.class.getResource("in8.txt");
			List<String> lines=Files.lines(Paths.get(resource.toURI())).collect(Collectors.toList());
			int[][] originalMatrix=parseLines(lines);
			int[][] result=arraySameSize(originalMatrix);
			result[0][0]=originalMatrix[0][0];
			int N=result.length;
			int M=result[0].length;
			for (int j=1;j<M;++j) result[0][j]=result[0][j-1]+originalMatrix[0][j];
			for (int i=1;i<N;++i) result[i][0]=result[i-1][0]+originalMatrix[i][0];
			for (int i=1;i<N;++i) for (int j=1;j<M;++j) result[i][j]=originalMatrix[i][j]+Math.min(result[i-1][j],result[i][j-1]);
			return result[N-1][M-1];
		}	catch (IOException|URISyntaxException exc)	{
			throw new RuntimeException(exc);
		}
	}
	
	public static void main(String[] args)	{
		Timing.time(Euler81::solve);
	}
}
