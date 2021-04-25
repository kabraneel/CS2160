package processor.pipeline;
import generic.Instruction;

public class MA_RW_LatchType {

	boolean RW_enable;
	boolean iscomplete;
	boolean towrite;
	int finaldest;
	int finalval;
	Instruction i;
	int rs1,rs2,rd,imm;
	boolean no_op;

	public MA_RW_LatchType()
	{
		no_op = false;
		RW_enable = false;
		rs1 = -1;
		rs2 = -1;
		rd = -1;
		imm = -1;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public boolean getiscomplete() {
		return iscomplete;
	}

	public void setiscomplete(boolean x) {
		iscomplete = x;
	}

	public int getfinaldest() {
		return finaldest;
	}

	public void setfinaldest(int x) {
		finaldest = x;
	}

	public boolean gettowrite() {
		return towrite;
	}

	public void settowrite(boolean x){
		towrite = x;
	}

	public int getfinalval() {
		return finalval;
	}

	public void setfinalval(int x) {
		finalval = x;
	}

	public Instruction getInstruction() {
		return i;
	}

	public void set_Instruction(Instruction instruction) {
		this.i = instruction;
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
