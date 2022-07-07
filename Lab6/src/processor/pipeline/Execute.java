package processor.pipeline;

import generic.Instruction;
import generic.Operand;
import generic.Misc;

// new imports
import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.ExecutionCompleteEvent;
import generic.Simulator;
import generic.Statistics;
import processor.Clock;
import processor.Processor;

import processor.Processor;

public class Execute  implements Element {

	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	Instruction i;
	static int x;

	// new statement
	public void event_scheduler(String opcodestr, Execute execute){
		long latency;
		switch(opcodestr)
		{
		case "mul":
			latency=Configuration.multiplier_latency;
			break;
		case "div":
			latency=Configuration.divider_latency;
			break;

		default:
			latency=Configuration.ALU_latency;
			break;
		}

		Simulator.getEventQueue().addEvent(
				new ExecutionCompleteEvent(
						Clock.getCurrentTime()+latency,
						execute,execute));
		OF_EX_Latch.setEX_busy(true);

	}

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performEX()
	{
		//TODO
		if(OF_EX_Latch.isEX_enable()){

			if(OF_EX_Latch.isEX_busy()){
				System.out.println("EX Busy");
				return;
			}
			int pc_val = OF_EX_Latch.get_pc_val();
			boolean is_do_ma = false;
			i = OF_EX_Latch.getInstruction();
			if(i != null){

				boolean isbranchtaken = false;
				int offset = -1;
				switch(i.getOperationType()){
					//R3I type
					case add :
					case addi :	{
						int tmp = i.getSourceOperand1().getValue() + i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case sub :
					case subi : {
						int tmp = i.getSourceOperand1().getValue() - i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case mul :
					case muli :{
						int tmp = i.getSourceOperand1().getValue() * i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("mul",this);
						break;
					}
					case div :
					case divi :{
						int tmp = i.getSourceOperand1().getValue() / i.getSourceOperand2().getValue();
						int tmp1 = i.getSourceOperand1().getValue() % i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						System.out.println("in divi123!");
						containingProcessor.getRegisterFile().setValue(31, tmp1);
						event_scheduler("div",this);
						break;
					}
					case and :
					case andi :{
						int tmp = i.getSourceOperand1().getValue() & i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case or :
					case ori :{
						int tmp = i.getSourceOperand1().getValue() | i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case xor :
					case xori :{
						int tmp = i.getSourceOperand1().getValue() ^ i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case slt :
					case slti :{
						int tmp = (i.getSourceOperand1().getValue() < i.getSourceOperand2().getValue()) ? 1 : 0;
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case sll :
					case slli :{
						int tmp = i.getSourceOperand1().getValue() << i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case srl :
					case srli :{
						int tmp = i.getSourceOperand1().getValue() >> i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case sra :
					case srai :	{
						int tmp = i.getSourceOperand1().getValue();
						for (int i1 = 0; i1<i.getSourceOperand2().getValue(); i1++)  tmp *= 10;
						EX_MA_Latch.setcomputed(tmp);
						event_scheduler("default",this);
						break;
					}
					case load :
					case store :	{
						System.out.println("FROM EX load store");
						event_scheduler("default",this);
						break;
					}

					case beq :	{
						if (i.getSourceOperand1().getValue() == i.getSourceOperand2().getValue()){
							int tmp = i.getDestinationOperand().getValue();
							if ((tmp & (1 << 16))>0){
								tmp += 32767<<17;
							}
							System.out.println("FROM EX beq offset value");
							System.out.println(tmp);
							// int pc = containingProcessor.getRegisterFile().getProgramCounter();
							offset = pc_val + tmp;
							// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
							// it is possible that we need to make pc = pc + tmp - 1
							isbranchtaken = true;
						}
						else{
							is_do_ma = true;
						}

						break;
					}
					case bne :	{
						if (i.getSourceOperand1().getValue() != i.getSourceOperand2().getValue()){
							int tmp = i.getDestinationOperand().getValue();
							if ((tmp & (1 << 16))>0){
								tmp += 32767<<17;
							}
							System.out.println("FROM EX beq offset value");
							System.out.println(tmp);
							// int pc = containingProcessor.getRegisterFile().getProgramCounter();
							offset = pc_val + tmp;

							// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
							isbranchtaken = true;
						}
						else{
							is_do_ma = true;
						}

						break;
					}
					case blt :	{
						if (i.getSourceOperand1().getValue() < i.getSourceOperand2().getValue()){
							int tmp = i.getDestinationOperand().getValue();
							if ((tmp & (1 << 16))>0){
								tmp += 32767<<17;
							}
							// int pc = containingProcessor.getRegisterFile().getProgramCounter();
							offset = pc_val + tmp;

							// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
							isbranchtaken = true;
						}
						else{
							is_do_ma = true;
						}

						break;
					}
					case bgt : 	{
						if (i.getSourceOperand1().getValue() > i.getSourceOperand2().getValue()){
							int tmp = i.getDestinationOperand().getValue();
							if ((tmp & (1 << 16))>0){
								tmp += 32767<<17;
							}
							System.out.println("FROM EX beq offset value");
							System.out.println(tmp);
							// int pc = containingProcessor.getRegisterFile().getProgramCounter();
							offset = pc_val + tmp;

							// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
							isbranchtaken = true;
						}
						else{
							is_do_ma = true;
						}
						break;
					}

					//RI type :
					case jmp :	{
						int tmp = i.getSourceOperand1().getValue() + i.getSourceOperand2().getValue();
						if ((tmp & (1 << 16))>0){
							tmp += 32767<<17;
						}
						System.out.println("FROM EX beq offset value");
						System.out.println(tmp);
						// int pc = containingProcessor.getRegisterFile().getProgramCounter();
						offset = pc_val + tmp;

						// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
						isbranchtaken = true;
						// it is possible that we need to make pc = pc + tmp - 1
						break;
					}

					case end :		{
							is_do_ma = true;
						break;
					}

					default: Misc.printErrorAndExit("unknown instruction!!");
				}

				if(isbranchtaken){

					EX_IF_Latch.setisbranchtaken(true);
					EX_IF_Latch.settargetbranch(offset);
					EX_IF_Latch.setIF_enable(true);

					containingProcessor.getOFUnit().IF_OF_Latch.OF_enable = false;
					containingProcessor.getIFUnit().IF_EnableLatch.IF_enable = true;

					EX_MA_Latch.setInstruction(null);
					// new line
					Simulator.getEventQueue().deleteeventqueue(Clock.getCurrentTime());
					OF_EX_Latch.setEX_enable(false);
				}

				if (is_do_ma){
					OF_EX_Latch.setEX_enable(false);
					EX_MA_Latch.setMA_enable(true);
					EX_MA_Latch.setInstruction(i);
					EX_MA_Latch.setrs1(OF_EX_Latch.getrs1());
					EX_MA_Latch.setrs2(OF_EX_Latch.getrs2());
					EX_MA_Latch.setrd(OF_EX_Latch.getrd());
					EX_MA_Latch.setimm(OF_EX_Latch.getimm());
				}

			}
			else{
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(true);
				EX_MA_Latch.setInstruction(i);
			}
			// else{
			// 	// EX_MA_Latch.setrs1(-1);
			// 	// EX_MA_Latch.setrs2(-1);
			// 	// EX_MA_Latch.setrd(-1);
			// 	// EX_MA_Latch.setimm(-1);
			// }
			// containingProcessor.getOFUnit().IF_OF_Latch.OF_enable = true;

		}
		else{
			System.out.println("EX-latch disable");
		}
	}

	@Override
	public void handleEvent(Event e) {
		if(EX_MA_Latch.isMA_busy()) {
			e.setEventTime(Clock.getCurrentTime()+1);
			Simulator.getEventQueue().addEvent(e);
		}
		else {
			System.out.println("EX work done");
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
			OF_EX_Latch.setEX_busy(false);

			EX_MA_Latch.setrs1(OF_EX_Latch.getrs1());
			EX_MA_Latch.setrs2(OF_EX_Latch.getrs2());
			EX_MA_Latch.setrd(OF_EX_Latch.getrd());
			EX_MA_Latch.setimm(OF_EX_Latch.getimm());
			EX_MA_Latch.setInstruction(i);
		}

	}

}
