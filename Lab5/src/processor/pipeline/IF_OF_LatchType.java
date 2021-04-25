package processor.pipeline;

public class IF_OF_LatchType {

	boolean OF_enable;
	int instruction;
	boolean no_op;

	public IF_OF_LatchType()
	{
		OF_enable = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public boolean getno_op() {
		return no_op;
	}

	public void setno_op(boolean val) {
		this.no_op = val;
	}

}
