package day10;

/**
 * Just an int value, but made into a class for legibility
 * @author Oliver Gratton
 *
 */
public class Food
{
	private int value;
	private boolean empty;
	
	/**
	 * 
	 * @param value -1 for empty
	 */
	public Food(int value)
	{
		this.value = value;
		empty = value==-1;
	}
	
	/**
	 * @return value field
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * @return true if value is -1
	 */
	public boolean isEmpty()
	{
		return empty;
	}
	
	public String toString()
	{
		return "food"+value;
	}
	
}
