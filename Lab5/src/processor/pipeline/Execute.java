package processor.pipeline;

import generic.Instruction;
import generic.Operand;
import generic.Misc;

import processor.Processor;

public class Execute {

	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	Instruction i;
	static int x;

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
						break;
					}
					case sub :
					case subi : {
						int tmp = i.getSourceOperand1().getValue() - i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case mul :
					case muli :{
						int tmp = i.getSourceOperand1().getValue() * i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case div :
					case divi :{
						int tmp = i.getSourceOperand1().getValue() / i.getSourceOperand2().getValue();
						int tmp1 = i.getSourceOperand1().getValue() % i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						System.out.println("in divi123!");
						containingProcessor.getRegisterFile().setValue(31, tmp1);
						break;
					}
					case and :
					case andi :{
						int tmp = i.getSourceOperand1().getValue() & i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case or :
					case ori :{
						int tmp = i.getSourceOperand1().getValue() | i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case xor :
					case xori :{
						int tmp = i.getSourceOperand1().getValue() ^ i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case slt :
					case slti :{
						int tmp = (i.getSourceOperand1().getValue() < i.getSourceOperand2().getValue()) ? 1 : 0;
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case sll :
					case slli :{
						int tmp = i.getSourceOperand1().getValue() << i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case srl :
					case srli :{
						int tmp = i.getSourceOperand1().getValue() >> i.getSourceOperand2().getValue();
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case sra :
					case srai :	{
						int tmp = i.getSourceOperand1().getValue();
						for (int i1 = 0; i1<i.getSourceOperand2().getValue(); i1++)  tmp *= 10;
						EX_MA_Latch.setcomputed(tmp);
						break;
					}
					case load :
					case store :	{
						System.out.println("FROM EX load store");
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
							int pc = containingProcessor.getRegisterFile().getProgramCounter();
							offset = pc + tmp -1;
							// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
							// it is possible that we need to make pc = pc + tmp - 1
							isbranchtaken = true;
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
							int pc = containingProcessor.getRegisterFile().getProgramCounter();
							offset = pc + tmp -1;

							// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
							isbranchtaken = true;
						}

						break;
					}
					case blt :	{
						if (i.getSourceOperand1().getValue() < i.getSourceOperand2().getValue()){
							int tmp = i.getDestinationOperand().getValue();
							if ((tmp & (1 << 16))>0){
								tmp += 32767<<17;
							}
							int pc = containingProcessor.getRegisterFile().getProgramCounter();
							offset = pc + tmp -1;

							// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
							isbranchtaken = true;
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
							int pc = containingProcessor.getRegisterFile().getProgramCounter();
							offset = pc + tmp -1;

							// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
							isbranchtaken = true;
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
						int pc = containingProcessor.getRegisterFile().getProgramCounter();
						offset = pc + tmp -1;

						// containingProcessor.getRegisterFile().setProgramCounter(pc + tmp -1);
						isbranchtaken = true;
						// it is possible that we need to make pc = pc + tmp - 1
						break;
					}

					case end :		{
						break;
					}

					default: Misc.printErrorAndExit("unknown instruction!!");
				}

				if(isbranchtaken){

					EX_IF_Latch.setisbranchtaken(true);
					EX_IF_Latch.settargetbranch(offset-1);
					EX_IF_Latch.setIF_enable(true);

					containingProcessor.getOFUnit().IF_OF_Latch.OF_enable = false;
					containingProcessor.getIFUnit().IF_EnableLatch.IF_enable = false;

					EX_MA_Latch.setInstruction(null);
				}

				EX_MA_Latch.setrs1(OF_EX_Latch.getrs1());
				EX_MA_Latch.setrs2(OF_EX_Latch.getrs2());
				EX_MA_Latch.setrd(OF_EX_Latch.getrd());
				EX_MA_Latch.setimm(OF_EX_Latch.getimm());
			}
			else{
				EX_MA_Latch.setrs1(-1);
				EX_MA_Latch.setrs2(-1);
				EX_MA_Latch.setrd(-1);
				EX_MA_Latch.setimm(-1);
			}
			// containingProcessor.getOFUnit().IF_OF_Latch.OF_enable = true;
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
			EX_MA_Latch.setInstruction(i);

		}
	}

}
