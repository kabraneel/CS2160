package generic;

import processor.Clock;
import processor.Processor;

import processor.memorysystem.MainMemory;
import java.io.*;


public class Simulator {

	static Processor processor;
	static boolean simulationComplete;
	static Statistics stats = new Statistics();


	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);

		simulationComplete = false;
	}

	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		MainMemory memory = processor.getMainMemory();
		try
		{
			FileInputStream file = new FileInputStream(assemblyProgramFile);
			DataInputStream data = new DataInputStream(file);

			//print the memory adresss
			int main = data.readInt();
			System.out.println(main);

			//print the global data and instructions
			int readint = 0, line = 0;
		 while(data.available() > 0)
		 {
			 readint = data.readInt();
			 memory.setWord(line, readint);
			 System.out.println(readint);
			 line ++ ;
		 }
		 data.close();

		 //update the old memory to new
		 processor.setMainMemory(memory);

		 //assign PC to the main adress
		 processor.getRegisterFile().setProgramCounter(main);

		 //set value of registers
		 processor.getRegisterFile().setValue(0, 0);
		 processor.getRegisterFile().setValue(1, 65535);
		 processor.getRegisterFile().setValue(2, 65535);
		}

		catch(FileNotFoundException fe)
	 {
		 System.out.println("FileNotFoundException : " + fe);
	 }
	 catch(IOException ioe)
	 {
		 System.out.println("IOException : " + ioe);
	 }

	 System.out.println(processor.getMainMemory().getContentsAsString(0,40));
	 System.out.println(processor.getRegisterFile().getContentsAsString());
	}

	public static void simulate()
	{
		int numcycles = 0;
		int numinstructions = 0;
		while(simulationComplete == false)
		{
			//for every new instruction
			numinstructions++;

			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			System.out.println(processor.getRegisterFile().getContentsAsString());

			//clock cyles executed are incremented by 5
			numcycles += 5;

		}

		// TODO
		stats.setNumberOfInstructions(numinstructions);
		stats.setNumberOfCycles(numcycles);
		// set statistics
	}

	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
