
class MB
{
	int h;
	int l;
	public MB() {

	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}
}

public class PCP {
	int processId;
	ProcessState processState;
	int PC;
	MB Mb;

	public PCP(int processId,ProcessState processState,int PC,MB Mb) {
		this.processId = processId;
		this.processState = processState;
		this.PC = PC;
		this.Mb = Mb;
	}


	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public ProcessState getProcessState() {
		return processState;
	}

	public void setProcessState(ProcessState processState) {
		this.processState = processState;
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pC) {
		PC = pC;
	}

    public MB getALLMB() {
    	return Mb;
    }
	public String getMB() {
		return "from "+this.Mb.l+" to "+this.Mb.h;
	}

	public void setMB(int l ,int h) {
		this.Mb.setH(h);
		this.Mb.setL(l);
	}



}
