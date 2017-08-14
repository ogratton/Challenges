package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is an attempt at solving Advent of Code 2016 Day 10
 * in a decentralised way, so as to simulate a bee hive.
 * The Queen gives birth to all the Drones, but from there
 * they can do the task by themselves.
 * 
 * Unfortunately the prints can't keep up with the food exchange,
 * so maybe I'll have to slow it down and try and add a visual
 * representation...
 * 
 * @author Oliver Gratton
 *
 */
public class BeeHive
{	
	protected static ArrayList<String> read(String filepath) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String line = "";
		ArrayList<String> data = new ArrayList<String>();
		while ((line = br.readLine()) != null)
		{
			if(!line.startsWith("#"))
			{
				data.add(line);	
			}
		}
		br.close();
		return data;
	}
	
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> lines = read("src/day10/input.txt");
		
		Queen vic = new Queen(lines);
		
		vic.run();
	}
}
