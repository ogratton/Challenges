package day10;

import java.util.ArrayList;

/**
 * An unborn bee-ling
 * (And for the purposes of this,
 * it never shall be born)
 * @author Oliver Gratton
 *
 */
public class Pupa extends Bee
{
	
	// (fairly redundant) record of food delivered to it
	private ArrayList<Food> food;
	
	/**
	 * Make a little unborn bee that can only be fed
	 * @param id
	 * @param queen
	 */
	public Pupa(ID id, Queen queen)
	{
		super(queen, id);
		food = new ArrayList<Food>();
	}

	@Override
	protected void invoke()
	{
		// nothing, it ain't born
	}

	@Override
	protected void receiveFood(Food f)
	{
		food.add(f);
		this.invoke();
	}
	
	public ArrayList<Food> getFood()
	{
		return food;
	}
	
	public String toString()
	{
		return id.toString() + " : " + food;
	}

}
