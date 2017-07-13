package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads a Comma Separated Value file and returns it as an ArrayList of lines
 * 
 * @author Oliver
 */
public class CSVReader
{
	private static BufferedReader br;
	private static String line = "";
	private static String csvSplitBy = ",";
	private static String comment = "#";

	/**
	 * Read the contents of a csv into an ArrayList
	 * Ignore lines that start with a #
	 * 
	 * @param filename the filepath of the file relative to the src folder
	 * @return array list of string arrays
	 */
	public static ArrayList<String[]> readContents(String filename, String... split)
	{
		if(split.length > 0) csvSplitBy = split[0]; 
		
		ArrayList<String[]> lines = new ArrayList<String[]>();
		try
		{
			br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null)
			{
				if (!line.startsWith(comment))
				{
					// use comma as separator
					String[] items = line.split(csvSplitBy);
					lines.add(items);
				}
			}
			return lines;
		}
		catch (IOException e)
		{
			System.out.println("Couldn't find file " + filename);
			return null;
		}
	}
}
