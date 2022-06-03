import java.util.ArrayList;

public class Process {
	// private static int globalId = 0;
	private int ProcessID;
	private String[] program;
	private String[] Modprogram;
	private int lastLocation = 0;
	private int lastLocationMod = 0;
	private boolean runningState;
	private boolean blockedState;
	private boolean firstLoc;
	private boolean secondLoc;
	private int selectedTimetick = 0;
	private PCP processPCB;
	private int VarSize;
	private boolean LargeFittableProcess;
	private boolean LargeNoFittableProcess;
	private ArrayList<MemoryObject> Variables;

	public Process(int ProcessID, String[] program) {
		this.ProcessID = ProcessID;
		this.processPCB = new PCP(this.ProcessID, ProcessState.NOTRUNNING, 0, new MB());
		// this.ProcessID = globalId;
		// globalId++;
		this.program = program;
		this.Variables = new ArrayList<>(VarSize);
		this.runningState = false;
		this.LargeFittableProcess = false;
		this.LargeNoFittableProcess = false;
		this.VarSize = 3;
		this.firstLoc = false;
		this.secondLoc = false;
	}

	public boolean isFirstLoc() {
		return firstLoc;
	}

	public void setFirstLoc(boolean firstLoc) {
		this.firstLoc = firstLoc;
	}

	public boolean isSecondLoc() {
		return secondLoc;
	}

	public void setSecondLoc(boolean secondLoc) {
		this.secondLoc = secondLoc;
	}

	public boolean isLargeFittableProcess() {
		return LargeFittableProcess;
	}

	public void setLargeFittableProcess(boolean largeFittableProcess) {
		LargeFittableProcess = largeFittableProcess;
	}

	public boolean isLargeNoFittableProcess() {
		return LargeNoFittableProcess;
	}

	public void setLargeNoFittableProcess(boolean largeNoFittableProcess) {
		LargeNoFittableProcess = largeNoFittableProcess;
	}

	public void printProgram() {
		for (int i = 0; i < this.program.length; i++) {
			System.out.println(this.program[i]);
		}
	}

	public PCP getProcessPCB() {
		return processPCB;
	}

	public int getVarSize() {
		return VarSize;
	}

	public void setVarSize(int varSize) {
		VarSize = varSize;
	}

	public void setProcessPCB(PCP processPCB) {
		this.processPCB = processPCB;
	}

	public void printVariables() {
		for (int i = 0; i < this.getVariables().size(); i++) {
			System.out.println("The Variable name: " + this.getVariables().get(i).getObjectName() + ",and its Data:"
					+ this.getVariables().get(i).getObjectData());
		}
	}

	public void printPCB() {
		System.out.println("Process ID: " + this.processPCB.getProcessId() + "\nProcess State: "
				+ this.processPCB.getProcessState().toString() + "\nPC: " + this.processPCB.getPC()
				+ "\nFirst location: " + this.getProcessPCB().getALLMB().getL() + "\nLast location: "
				+ this.getProcessPCB().getALLMB().getH());
	}

	public ArrayList<MemoryObject> getVariables() {
		return Variables;
	}

	public void setVariables(ArrayList<MemoryObject> variables) {
		Variables = variables;
	}

	public boolean isBlockedState() {
		return blockedState;
	}

	public int getSelectedTimetick() {
		return selectedTimetick;
	}

	public void setSelectedTimetick(int selectedTimetick) {
		this.selectedTimetick = selectedTimetick;
	}

	public void setBlockedState(boolean blockedState) {
		this.blockedState = blockedState;
	}

	public int getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(int lastLocation) {
		this.lastLocation = lastLocation;
	}

	public int getProcessID() {
		return ProcessID;
	}

	public void setProcessID(int processID) {
		ProcessID = processID;
	}

	public boolean isRunningState() {
		return runningState;
	}

	public void setRunningState(boolean runningState) {
		this.runningState = runningState;
	}

	public String[] getProgram() {
		return program;
	}

	public void setProgram(String[] program) {
		this.program = program;
	}

	public String[] getModprogram() {
		return Modprogram;
	}

	public void setModprogram(String[] modprogram) {
		Modprogram = modprogram;
	}

	public int getLastLocationMod() {
		return lastLocationMod;
	}

	public void setLastLocationMod(int lastLocationMod) {
		this.lastLocationMod = lastLocationMod;
	}

}
