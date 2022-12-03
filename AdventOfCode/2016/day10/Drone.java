package day10;

/**
 * A worker bee whose only instincts are to fill its
 * knee pouches and then distrubute the food according
 * to its rules, defined by its Queen at birth
 * 
 * @author Oliver Gratton
 *
 */
public class Drone extends Bee
{
	private Food[] knees; // pockets for food

	private ID lowTo; // ID of to whom to send the lower value food
	private ID highTo; // ID of to whom to send the higher value food
	private boolean defined; // true when both lowTo and highTo are defined for this bee

	/**
	 * Birth a new Drone
	 */
	public Drone(ID id, Queen queen)
	{
		super(queen, id);
		knees = new Food[] { EMPTY, EMPTY };
	}

	public void setRules(ID l, ID h)
	{
		this.lowTo = l;
		this.highTo = h;
		defined = true;
		invoke();
	}

	/**
	 * what to do when invoked to act
	 * (by being given food or having rules set)
	 */
	@Override
	protected void invoke()
	{
		if (full() && defined)
		{
			// check if this bee is the one we are looking for (see javadoc)
			aocCheck(61,17);

			// anyway, now we calculate the high and the low values and send them accordingly:
			// (note that we send the indexes of the knees, not the actual food)
			
			int low = 0, high = 1;

			if (knees[1].getValue() < knees[0].getValue())
			{
				low = 1;
				high = 0;
			}
			sendFood(lowTo, low);
			sendFood(highTo, high);
		}
	}

	/**
	 * Send a food to a bee
	 * 
	 * @param bee
	 * @param kneeIndex
	 */
	private void sendFood(ID bee, int kneeIndex)
	{
		Food food = knees[kneeIndex];
		// remove our own bit of food
		knees[kneeIndex] = EMPTY;
		try
		{
			Bee target = queen.lookup(bee);
			target.receiveFood(food);
		}
		catch (Exception e)
		{
			System.err.println("Bee " + bee + " not found");
			e.printStackTrace();
			// I suppose it should keep the food then
			knees[kneeIndex] = food;
		}

		// This seems to be all out of sync. I guess cos it's too fast, maybe?
		// Either way, it gets the right answers
		//System.out.println(this.id + " is sending food " + food.getValue() + " to " + bee);

	}

	@Override
	protected void receiveFood(Food food)
	{
		if (knees[0].isEmpty())
		{
			knees[0] = food;
		}
		else if (knees[1].isEmpty())
		{
			knees[1] = food;
		}
		else
		{
			// shouldn't happen (but probably will, cos it's me)
			System.err.println("food overload on bee " + id);
		}

		this.invoke(); // update
	}

	/**
	 * @return true if bee is carrying two bits of food
	 */
	private boolean full()
	{
		return (!knees[0].isEmpty() && !knees[1].isEmpty());
	}

	public String toString()
	{
		return id.toString() + " : " + knees[0] + ", " + knees[1] + " : " + lowTo + ", " + highTo;
	}
	
	/**
	 * Check if the bee is holding these two food values. <br>
	 * I should probably do this with an auto-sorting tuple (TODO?)
	 */
	private void aocCheck(int a, int b)
	{
		if (((knees[0].getValue() == a) && (knees[1].getValue() == b))
				|| ((knees[0].getValue() == b) && (knees[1].getValue() == a)))
		{
			System.out.println(id + " has " + a +" and " + b);
		}
	}
}
