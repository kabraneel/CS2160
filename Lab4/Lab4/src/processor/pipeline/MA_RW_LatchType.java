package processor.pipeline;

public class MA_RW_LatchType {

	boolean RW_enable;
	boolean iscomplete;
	boolean towrite;
	int finaldest;
	int finalval;

	public MA_RW_LatchType()
	{
		RW_enable = false;
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
}
