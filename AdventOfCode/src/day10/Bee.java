package day10;

/**
 * Generic object for both Drones and Pupae
 * Does not include Queen
 * @author Oliver Gratton
 *
 */
public abstract class Bee
{
	// id of bee (drone or pupa followed by number)
	protected ID id;
	
	protected Queen queen;
	
	// definition of an empty knee pocket
	final Food EMPTY = new Food(-1);
	
	/**
	 * An active action
	 */
	protected abstract void invoke();
	
	/**
	 * What a bee should do when it receives a piece of food
	 * @param food
	 */
	protected abstract void receiveFood(Food food);
	
	public Bee(Queen queen, ID id)
	{
		this.queen = queen;
		this.id = id;
	}
}
