package processor.pipeline;
import generic.Instruction;

public class EX_MA_LatchType {

	boolean MA_enable;
	boolean iscomputed;
	int destination;
	int computed;
	Instruction instruction;

	public EX_MA_LatchType()
	{
		MA_enable = false;
		iscomputed = true;
		computed = 0;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}


	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int x) {
		this.destination = x;
	}

	public int getcomputed() {
		return computed;
	}

	public void setcomputed(int x) {
		this.computed = x;
	}

}
