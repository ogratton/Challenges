package day01;

import java.util.ArrayList;

import resources.CSVReader;

public class Steps
{
	ArrayList<String[]> data;
	int dist = 0;
	int heading = 0;
	
	int north = 0;
	int east = 0;
	
	public Steps(String filepath)
	{
		data = CSVReader.readContents(filepath);
	}
	
	void turnRight()
	{
		heading++;
		heading = Math.floorMod(heading, 4);
	}
	
	void turnLeft()
	{
		heading--;
		heading = Math.floorMod(heading, 4);
	}
	
	void move()
	{
		switch(heading)
		{
			case 0:
				north += dist;
				break;
			case 1:
				east += dist;
				break;
			case 2:
				north -= dist;
				break;
			case 3:
				east -= dist;
				break;
			default:
				System.err.println("Bad heading: " + heading);
				System.exit(1);
		}
		
		System.out.println("N: " + north + ", E: " + east);
	}
	
	public int solve()
	{
		for (String[] strings : data)
		{
			for (int i = 0; i < strings.length; i++)
			{
				String instruction = strings[i].trim();
				if(instruction.startsWith("R"))
				{
					turnRight();
				}
				else if(instruction.startsWith("L"))
				{
					turnLeft();
				}
				else
				{
					System.err.println("Bad input");
					System.exit(1);
				}
				
//				System.out.println("--");
//				System.out.println(strings[i].trim());
//				System.out.println("heading: " + heading);
				dist = Integer.parseInt(instruction.substring(1));
				move();
			}
		}
		
		return Math.abs(north)+Math.abs(east);
	}
	
	public static void main(String[] args)
	{
		Steps s = new Steps("src/day01/input.txt");
		System.out.println(s.solve());
	}
}
