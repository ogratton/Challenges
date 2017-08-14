package day10;

import java.util.ArrayList;

/**
 * The Queen bee has all the instructions and a register of all the Drones and
 * Pupae in the hive.
 * She gives birth (and food) to all the bees
 * 
 * @author Oliver Gratton
 *
 */
public class Queen
{
	// the universal list of all the other bees in the system
	// so they can find each other
	private Drone[] drones = new Drone[1000];
	private Pupa[] pupae = new Pupa[1000];

	private ArrayList<String> instructions;

	public Queen(ArrayList<String> instructions)
	{
		this.instructions = instructions;
	}

	/**
	 * The Queen cycles through her instructions,
	 * birthing and giving out food to all the bees
	 */
	public void run()
	{
		for (int i = 0; i < instructions.size(); i++)
		{
			interpret(instructions.get(i));
//			printAllBees(drones);
//			printAllBees(pupae);
//			System.out.println(instructions.get(i));
//			System.out.println("-------------------------------");
		}
		
		// For working out part two:
//		System.out.println(pupae[0]);
//		System.out.println(pupae[1]);
//		System.out.println(pupae[2]);
	}

	/**
	 * Parse and exectute an instruction
	 * 
	 * @param instruction
	 */
	private void interpret(String instruction)
	{
		if (instruction.startsWith("drone"))
		{
			interpretDrone(instruction);
		}
		else if (instruction.startsWith("food"))
		{
			interpretPupa(instruction);
		}
		else
		{
			System.err.println("Malformed command: " + instruction);
		}
	}

	private void interpretDrone(String instruction)
	{
		// parsing
		String[] words = instruction.split(" gives low to ");
		String[] bees = words[1].split(" and high to ");
		ID droneID = new ID(words[0]);
		ID lowID = new ID(bees[0]);
		ID highID = new ID(bees[1]); // TODO trim()?

		// executing
		if (lookup(droneID) == null)
		{
			Drone drone = new Drone(droneID, this);
			drone.setRules(lowID, highID);
			drones[droneID.getNumber()] = drone;
		}
		else
		{
			drones[droneID.getNumber()].setRules(lowID, highID);
		}
		_addBeeHelper(lowID);
		_addBeeHelper(highID);

//		System.out.println(droneID);
//		System.out.println(lowID);
//		System.out.println(highID);
	}
	
	
	private void interpretPupa(String instruction)
	{
		String[] words = instruction.split(" goes to ");
		int value = Integer.parseInt(words[0].substring("food ".length()));
		ID id = new ID(words[1]);
		
		_addBeeHelper(id);
		lookup(id).receiveFood(new Food(value));

//		System.out.println(value);
//		System.out.println(id);
	}
	
	/**
	 * Make a new bee if it's not already in the registers
	 * @param id
	 */
	private void _addBeeHelper(ID id)
	{
		if (lookup(id) == null)
		{
			if (id.getType().equals("drone"))
			{
				Drone bee = new Drone(id, this);
				drones[id.getNumber()] = bee;
			}
			else
			{
				Pupa bee = new Pupa(id, this);
				pupae[id.getNumber()] = bee;
			}
		}
	}

	/**
	 * Look up a bee in the register
	 * 
	 * @param id Bee ID
	 * @return Bee object
	 */
	public Bee lookup(ID id)
	{
		try
		{
			Bee[] register = id.getType().equals("drone") ? drones : pupae;
			return register[id.getNumber()];
		}
		catch (Exception e)
		{
			System.err.println("Bad id: " + id);
		}
		
		return null; // bad boy
	}
	
	/**
	 * Print all the bees that are not null
	 * @param register either 'drones' or 'pupae'
	 */
	private void printAllBees(Bee[] register)
	{
		Bee bee;
		for (int i = 0; i < register.length; i++)
		{
			bee = register[i];
			if(bee != null)
			{
				System.out.println(bee);
			}
		}
	}

}
