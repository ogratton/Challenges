package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Triangles
{
	protected static boolean triangle(String a, String b, String c)
	{
		int ai = Integer.parseInt(a);
		int bi = Integer.parseInt(b);
		int ci = Integer.parseInt(c);

		return ai + bi > ci;
	}

	protected static ArrayList<String> read(String filepath) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String line = "";
		ArrayList<String> data = new ArrayList<String>();
		while ((line = br.readLine()) != null)
		{
			data.add(line);
		}
		br.close();
		return data;
	}

	public static void main(String[] args) throws IOException
	{
		ArrayList<String> data = read("src/day3/input.txt");

		int total = 0;
		for (String string : data)
		{
			System.out.print(string);
			String[] xs = string.trim().split("\\s+");
			if (triangle(xs[0], xs[1], xs[2]))
			{
				total++;
				System.out.println("  True");
			}
			else
			{
				System.out.println("  False");
			}
		}

		System.out.println(total);
	}
}
