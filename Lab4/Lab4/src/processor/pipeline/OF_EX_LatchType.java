package processor.pipeline;
import generic.Instruction;
import generic.Operand;

public class OF_EX_LatchType {

	boolean EX_enable;
	Instruction i;

	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public Instruction getInstruction() {
		return i;
	}

	public void set_Instruction(Instruction instruction) {
		this.i = instruction;
	}

}
