package processor.pipeline;
import generic.Instruction;
import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;

	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public void performRW()
	{


		if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			Instruction i = MA_RW_Latch.getInstruction();
			if(i!=null){
				boolean complete = MA_RW_Latch.getiscomplete();
				boolean towrite = MA_RW_Latch.gettowrite();

				int finalval = MA_RW_Latch.getfinalval();
				int finaldest = MA_RW_Latch.getfinaldest();

				System.out.println("final dest RW");

				System.out.println(finaldest);
				System.out.println("final value RW");

				System.out.println(finalval);

				if(towrite == true){
					containingProcessor.getRegisterFile().setValue(finaldest, finalval);
				}

				if(complete == true){
					Simulator.setSimulationComplete(true);
				}
			}
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			// containingProcessor.getMAUnit().EX_MA_Latch.MA_enable = true;
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
