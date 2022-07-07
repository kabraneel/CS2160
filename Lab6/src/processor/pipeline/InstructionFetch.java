package processor.pipeline;
import processor.Processor;

import generic.Simulator;

// new imports
import configuration.Configuration;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;
import generic.Element;
import generic.Event;

public class InstructionFetch implements Element {

	boolean conflict = false;
	Processor containingProcessor;
	public IF_EnableLatchType IF_EnableLatch;
	public IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	int currentPC;

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
			if(IF_EnableLatch.isIF_busy()) {
				System.out.println("IF-latch busy");
					return;
			}

			//check if there is any output from EX_IF
			if(EX_IF_Latch.IF_enable){
				if(EX_IF_Latch.isbranchtaken){
					System.out.println("I got branch intruction");
					containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.targetbranch);
					EX_IF_Latch.setisbranchtaken(false);
					EX_IF_Latch.setIF_enable(false);
					Simulator.clashes +=1;
				}
			}

			currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			System.out.println("In instruction fetch currentPC(going to create event for this inst)");
			System.out.println(currentPC);
			// int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			// System.out.println("In instruction fetch newInstruction");
			// System.out.println(newInstruction);


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
			// if(!conflict){
			// 	IF_OF_Latch.setInstruction(newInstruction);
			// 	IF_OF_Latch.set_pc_val(currentPC);
			// 	containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			// }

			// add code
			Simulator.getEventQueue().addEvent(
							new MemoryReadEvent(
									Clock.getCurrentTime()+Configuration.mainMemoryLatency,
									this,containingProcessor.getMainMemory(),
									containingProcessor.getRegisterFile().getProgramCounter()));
					IF_EnableLatch.setIF_busy(true);
					containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);

			Simulator.numinstructions +=1;

			// IF_OF_Latch.setInstruction(newInstruction);
			// containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);

			// IF_EnableLatch.setIF_enable(false);
			// IF_OF_Latch.setOF_enable(true);
		}
		else{
			System.out.println("IF-latch disable");
		}
	}

	@Override
	public void handleEvent(Event e) {
		if(IF_OF_Latch.OF_busy) {
			e.setEventTime(Clock.getCurrentTime()+1);
			Simulator.getEventQueue().addEvent(e);
		}
		else {
			MemoryResponseEvent event=(MemoryResponseEvent) e;
			IF_OF_Latch.setInstruction(event.getValue());
			IF_OF_Latch.set_pc_val(currentPC);
			System.out.println("Got this from main memory(2 lines)");
			System.out.println(event.getValue());
			System.out.println(currentPC);
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_busy(false);
		}

	}

}
