package processor.pipeline;
import generic.Instruction;

public class EX_MA_LatchType {

	boolean MA_enable;
	boolean iscomputed;
	int destination;
	int computed;
	Instruction instruction;
	int rs1,rs2,rd,imm;
	boolean no_op;
	boolean MA_busy;

	public void setMA_busy(boolean mA_busy){
		 MA_busy=mA_busy;
	}
	public boolean isMA_busy() {
		return MA_busy;
	}

	public EX_MA_LatchType()
	{
		MA_enable = false;
		iscomputed = true;
		computed = 0;
		no_op = false;

		rs1 = -1;
		rs2 = -1;
		rd = -1;
		imm = -1;

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

	public int getrs1() {
		return rs1;
	}

	public void setrs1(int x) {
		this.rs1 = x;
	}

	public int getrs2() {
		return rs2;
	}

	public void setrs2(int x) {
		this.rs2 = x;
	}

	public int getrd() {
		return rd;
	}

	public void setrd(int x) {
		this.rd = x;
	}

	public int getimm() {
		return imm;
	}

	public void setimm(int x) {
		this.imm = x;
	}
}
