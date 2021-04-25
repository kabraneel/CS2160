package processor.pipeline;
import processor.Processor;

import generic.Simulator;

public class InstructionFetch {

	boolean conflict = false;
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performIF()
	{

		// containingProcessor.getMainMemory().setWord(0,10);
		if(IF_EnableLatch.isIF_enable())
		{

			//check if there is any output from EX_IF
			if(EX_IF_Latch.IF_enable){
				if(EX_IF_Latch.isbranchtaken){
					containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.targetbranch);
					EX_IF_Latch.setisbranchtaken(false);
					EX_IF_Latch.setIF_enable(false);
					Simulator.clashes +=1;
				}
			}

			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			System.out.println("In instruction fetch currentPC");
			System.out.println(currentPC);
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			System.out.println("In instruction fetch newInstruction");
			System.out.println(newInstruction);


			// boolean x = !conflict;
			// 	switch(String.valueOf(x){
			// 		case "true":
			// 			if(instructionString.substring(0,5).equals("11101")) {
			// 				is_end = true;
			// 			}
			// 			IF_OF_Latch.setInstruction(newInstruction);
			// 			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			//
			// }
			if(!conflict){
				IF_OF_Latch.setInstruction(newInstruction);
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			}

			// IF_OF_Latch.setInstruction(newInstruction);
			// containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);

			// IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);
		}
	}

}
