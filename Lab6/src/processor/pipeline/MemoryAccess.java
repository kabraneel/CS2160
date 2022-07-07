package processor.pipeline;
import generic.Instruction;
import processor.Processor;

import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	public EX_MA_LatchType EX_MA_Latch;
	public MA_RW_LatchType MA_RW_Latch;

	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}

	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable()){
				if(EX_MA_Latch.isMA_busy()) {
					System.out.println("MA-latch busy");
					return;
				}

				Instruction i = EX_MA_Latch.getInstruction();
				if(i!=null){

				int final1 = EX_MA_Latch.getcomputed();
				boolean towrite = true;
				boolean iscomplete = false;

				int dest = i.getDestinationOperand().getValue();
				System.out.println("FROM MA DO OP1 OP2");
				System.out.println(i.getDestinationOperand().getValue());
				System.out.println(i.getSourceOperand1().getValue());
				System.out.println(i.getSourceOperand2().getValue());

				// console.out(temp.getSourceOperand2().getValue());


				switch(i.getOperationType()){
					case bgt:
					case beq:
					case jmp:
					case blt:
					case bne: 			towrite = false;
										EX_MA_Latch.setMA_enable(false);
										MA_RW_Latch.setRW_enable(true);
										break;


					case store:	/*containingProcessor.getMainMemory().setWord(i.getSourceOperand1().getValue()+i.getSourceOperand2().getValue(), i.getDestinationOperand().getValue());*/
											towrite = false;
											System.out.println("Going to set MemoryWriteEvent(Store)");
											Simulator.getEventQueue().addEvent(
													new MemoryWriteEvent(
															Clock.getCurrentTime()+Configuration.mainMemoryLatency,
															this,containingProcessor.getMainMemory(),
															i.getSourceOperand1().getValue()+i.getSourceOperand2().getValue(),
															i.getDestinationOperand().getValue()));
											EX_MA_Latch.setMA_busy(true);

											// Simulator.setSimulationComplete(true);

											break;

					case load:
											// System.out.println("hi2");
											System.out.println("Going to set MemoryReadEvent(load)");
											// final1 = containingProcessor.getMainMemory().getWord(i.getSourceOperand1().getValue() + i.getSourceOperand2().getValue());
											dest = i.getDestinationOperand().getValue();
											Simulator.getEventQueue().addEvent(
													new MemoryReadEvent(
															Clock.getCurrentTime()+Configuration.mainMemoryLatency,
															this,containingProcessor.getMainMemory(),
															i.getSourceOperand1().getValue() + i.getSourceOperand2().getValue() ));
											EX_MA_Latch.setMA_busy(true);
											// towrite = false;
											break;
					case end:
										System.out.println("MA end found");
										towrite = false;
										iscomplete = true;
										EX_MA_Latch.setMA_enable(false);
										MA_RW_Latch.setRW_enable(true);
										break;
					default:
										EX_MA_Latch.setMA_enable(false);
										MA_RW_Latch.setRW_enable(true);
										break;

				}


				MA_RW_Latch.settowrite(towrite);
				MA_RW_Latch.setfinalval(final1);
				MA_RW_Latch.setiscomplete(iscomplete);
				MA_RW_Latch.setfinaldest(dest);

				MA_RW_Latch.setrs1(EX_MA_Latch.getrs1());
				MA_RW_Latch.setrs2(EX_MA_Latch.getrs2());
				MA_RW_Latch.setrd(EX_MA_Latch.getrd());
				MA_RW_Latch.setimm(EX_MA_Latch.getimm());

			}
					// containingProcessor.getEXUnit().OF_EX_Latch.EX_enable = true;
			else{
				System.out.println("MA no inst case");
				MA_RW_Latch.setrs1(-1);
				MA_RW_Latch.setrs2(-1);
				MA_RW_Latch.setrd(-1);
				MA_RW_Latch.setimm(-1);
				// containingProcessor.getMAUnit().EX_MA_Latch.getrd();
				EX_MA_Latch.setrs1(-1);
				EX_MA_Latch.setrs2(-1);
				EX_MA_Latch.setrd(-1);
				EX_MA_Latch.setimm(-1);
			}

			MA_RW_Latch.set_Instruction(i);
			// EX_MA_Latch.setMA_enable(false);
			// MA_RW_Latch.setRW_enable(true);
		}
		else{
			System.out.println("MA-latch disable");
		}
	}

	@Override
	public void handleEvent(Event e) {
		MemoryResponseEvent event=(MemoryResponseEvent) e;
		System.out.println("MA got the response");
		// MA_RW_Latch.settowrite(towrite);
		// MA_RW_Latch.setfinalval(final1);
		// MA_RW_Latch.setiscomplete(iscomplete);
		MA_RW_Latch.setfinalval(event.getValue());

		MA_RW_Latch.setrs1(EX_MA_Latch.getrs1());
		MA_RW_Latch.setrs2(EX_MA_Latch.getrs2());
		MA_RW_Latch.setrd(EX_MA_Latch.getrd());
		MA_RW_Latch.setimm(EX_MA_Latch.getimm());
		System.out.println(EX_MA_Latch.getrs1());
		System.out.println(EX_MA_Latch.getrs2());
		System.out.println(EX_MA_Latch.getrd());
		System.out.println(EX_MA_Latch.getimm());
		System.out.println(" ");

		EX_MA_Latch.setMA_busy(false);
		MA_RW_Latch.setRW_enable(true);
		EX_MA_Latch.setMA_enable(false);


	}

}
