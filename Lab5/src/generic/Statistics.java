package generic;

import java.io.PrintWriter;

public class Statistics {

	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles;
	static int clashes;
	static int datahaz;


	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);

			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Number of ControlHazards taken = " + clashes);
			writer.println("Number of DataHazards taken = " + datahaz);

			// TODO add code here to print statistics in the output file

			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}

	// TODO write functions to update statistics
	public void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public void setclashes(int clashes) {
		Statistics.clashes = clashes;
	}

	public void setdatahaz(int datahaz) {
		Statistics.datahaz = datahaz;
	}

	// public void setNumberOfInstructions(int numberOfInstructions) {
	// 	Statistics.numberOfInstructions = numberOfInstructions;
	// }
	//
	// public void setNumberOfCycles(int numberOfCycles) {
	// 	Statistics.numberOfCycles = numberOfCycles;
	// }
}
