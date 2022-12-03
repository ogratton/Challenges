package day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BathroomCode
{
		char nil = '-';
	
//		char[][] codepad = new char[][] { { '1', '2', '3' }, { '4', '5', '6' }, { '7', '8', '9' } };
//		int[] start = new int[] { 1, 1 }; // start at 5

		char[][] codepad = new char[][] { { nil, nil, '1', nil, nil }, { nil, '2', '3', '4', nil }, { '5', '6', '7', '8', '9' }, { nil, 'A', 'B', 'C', nil },
			{ nil, nil, 'D', nil, nil } };
		int[] start = new int[] { 2, 0 }; // start at 5


	public void solve(String filepath) throws IOException
	{
		ArrayList<String> commands = read(filepath);
		char[] solution = new char[commands.size()];

		int[] pos = start.clone();		

		for (int i = 0; i < commands.size(); i++)
		{
			pos = doOneRow(pos, commands.get(i));
			solution[i] = lookup(pos);
		}

		for (int i = 0; i < solution.length; i++)
		{
			System.out.print(solution[i]);
//			if (i < solution.length - 1)
//				System.out.print(",");
		}
	}

	/**
	 * Look up coords on the codepad
	 * @param coord int pair with 0,0 at top left
	 * @return character at that position
	 */
	protected char lookup(int[] coord)
	{
		return codepad[coord[0]][coord[1]];
	}

	protected int[] doOneRow(int[] st, String command)
	{
		int[] loc = st;

		for (int i = 0; i < command.length(); i++)
		{
			char c = command.charAt(i);
			int[] temp = loc.clone();
			switch (c)
			{
				case 'U':
					temp[0] = temp[0] > 0 ? temp[0] - 1 : 0;
					if(lookup(temp)!=nil) loc = temp;
					break;
				case 'D':
					temp[0] = temp[0] < codepad.length - 1 ? temp[0] + 1 : codepad.length - 1;
					if(lookup(temp)!=nil) loc = temp;
					break;
				case 'L':
					temp[1] = temp[1] > 0 ? temp[1] - 1 : 0;
					if(lookup(temp)!=nil) loc = temp;
					break;
				case 'R':
					temp[1] = temp[1] < codepad[1].length - 1 ? temp[1] + 1 : codepad[1].length - 1;
					if(lookup(temp)!=nil) loc = temp;
					break;
			}
		}

		return loc;
	}

	private ArrayList<String> read(String filename) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
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
		BathroomCode bc = new BathroomCode();

//		int[] co = bc.doOneRow(new int[]{2,1}, "UUUUD");
//		System.out.println(bc.lookup(co));

		bc.solve("src/day02/input.txt");

	}

}
