package generic;

import processor.Clock;
import processor.Processor;

import processor.memorysystem.MainMemory;
import java.io.*;


public class Simulator {

	public static int clashes = 0;
	public static int datahaz = 0;
	public static int numinstructions = 0;
	static Processor processor;
	static boolean simulationComplete;
	static Statistics stats = new Statistics();
	static EventQueue eventQueue;

	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		eventQueue = new EventQueue();
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

	public static EventQueue getEventQueue() {
		return eventQueue;
	}

	public static void simulate()
	{
		int numcycles = 0;
		// int numinstructions = 0;
		while(simulationComplete == false)
		{
			//for every new instruction
			System.out.println("new simulation loop starts");

			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			// if(Clock.getCurrentTime()>600){
			// 	break;
			// }
			Clock.incrementClock();
			System.out.println("Clock Time = " + Clock.getCurrentTime());
			System.out.println(processor.getRegisterFile().getContentsAsString());

			//clock cyles executed are incremented by 5
			numcycles += 1;

		}
		System.out.println("NUMBER OF CLASHES");
		System.out.println(clashes);


		// TODO
		stats.setNumberOfInstructions(numinstructions);
		stats.setNumberOfCycles(numcycles);
		stats.setclashes(clashes);
		stats.setdatahaz(datahaz);
		// set statistics
	}

	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
