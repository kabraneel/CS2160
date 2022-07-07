package processor.pipeline;
import generic.Instruction;
import generic.Operand;

public class OF_EX_LatchType {

	boolean EX_enable;
	Instruction i;
	int rs1=0,rs2=0,rd=0,imm=0;
	boolean no_op;
	boolean EX_busy;
	int pc_val;

	public void setEX_busy(boolean eX_busy){
		 EX_busy=eX_busy;
	}
	public boolean isEX_busy() {
		return EX_busy;
	}

	public OF_EX_LatchType()
	{
		EX_enable = false;
		no_op = false;
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

	public boolean getno_op() {
		return no_op;
	}

	public void setno_op(boolean val) {
		this.no_op = val;
	}

	public int get_pc_val() {
		return pc_val;
	}

	public void set_pc_val(int x) {
		this.pc_val = x;
	}

}
