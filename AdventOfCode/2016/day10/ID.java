package day10;

/**
 * Essentially a string*int tuple
 * Oh how I miss you, Haskell
 * @author Oliver Gratton
 *
 */
public class ID
{
	private String beetype;
	private int number;
	
	/**
	 * For the scatty
	 * @param everything String in form "drone 168"
	 */
	public ID(String everything)
	{
		String[] bits = everything.split(" ");
		if (bits[0].equals("drone") || bits[0].equals("pupa"))
		{
			beetype = bits[0];
		}
		else
		{
			System.err.println("Invalid id candidate: " + everything);
		}
		try
		{
			number = Integer.parseInt(bits[1]);
		}
		catch (NumberFormatException e)
		{
			System.err.println("Invalid id candidate: " + everything);
		}
	}
	
	/**
	 * For the well-organised
	 * @param type
	 * @param n
	 */
	public ID(String type, int n)
	{
		beetype = type;
		number = n;
	}
	
	public String getType()
	{
		return beetype;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public String toString()
	{
		return beetype + number;
	}
}
