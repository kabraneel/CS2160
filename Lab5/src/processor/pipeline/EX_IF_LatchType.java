package processor.pipeline;

public class EX_IF_LatchType {

	int targetbranch;
	boolean isbranchtaken;
	boolean IF_enable;

	public EX_IF_LatchType()
	{
		isbranchtaken = false;
		IF_enable = false;
	}

	public void settargetbranch(int b){
		targetbranch = b;
	}

	public void setisbranchtaken(boolean b){
		isbranchtaken = b;
	}

	public void setIF_enable(boolean b){
	 IF_enable = b;
	}

}
