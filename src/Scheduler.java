import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	public static Queue<Process> ReadyQueue;
	public static Queue<String> ReadyQueueID;
	public static Queue<Process> BlockQueue;
	public static Queue<String> BlockQueueID;
	public static ArrayList<Process> listOfTimedprocesses;
	public static int Currenttime;
	public static int noOfProcessesFinished;
	private int Pcurrenttime;
	private int Quantumtime; // time_slice
	Parser parser;

	public Scheduler(int Quantumtime, Parser parser) {
		this.Quantumtime = Quantumtime;
		this.parser = parser;
		Currenttime = 0;
		noOfProcessesFinished = 0;
		this.Pcurrenttime = 0;
		ReadyQueue = new LinkedList<>();
		BlockQueue = new LinkedList<>();
		ReadyQueueID = new LinkedList<>();
		BlockQueueID = new LinkedList<>();
		listOfTimedprocesses = new ArrayList<>();
	}

	public int getPcurrenttime() {
		return Pcurrenttime;
	}

	public void setPcurrenttime(int pcurrenttime) {
		Pcurrenttime = pcurrenttime;
	}

	public int getQuantumtime() {
		return Quantumtime;
	}

	public void setQuantumtime(int quantumtime) {
		Quantumtime = quantumtime;
	}

	public void insertProcesses() throws IOException {

	}

	public boolean EqlessThanTwo(String arr[]) {
		if (arr.length <= 2) {
			return true;
		} else {
			return false;
		}
	}

	public void RobinRoundScheduling() throws IOException {
		System.out.println("**Welcome to Pogster OS**");
		while (!(parser.PogOperatingSystem.getNoOfProcesses() == parser.PogOperatingSystem
				.getNoOfCompletedProcesses())) {
			if (Currenttime == 0) {
				System.out.println(
						"-----------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"Beginning of the Pog Scheduling ,At time : " + Currenttime + "\n         *Pog Start*       ");
			}
			for (int j = 0; j < listOfTimedprocesses.size(); j++) {
				if (listOfTimedprocesses.get(j).getSelectedTimetick() == Currenttime) {
					ReadyQueue.add(listOfTimedprocesses.get(j));
					ReadyQueueID.add("Process " + listOfTimedprocesses.get(j).getProcessID());
					parser.PogOperatingSystem.writeDataToMainMemory(listOfTimedprocesses.get(j));
					listOfTimedprocesses.remove(listOfTimedprocesses.get(j));
					System.out.println(
							"-----------------------------------------------------------------------------------------------------------------");
					System.out.println("#At time: " + Currenttime + "\nA new Process was added to the Readyqueue!"
							+ "\nThe Current Readyqueue is: " + ReadyQueueID.toString());
				}
			}
			parser.PogOperatingSystem.printMemory();
			Process currentProcess = ReadyQueue.remove();
			String currentProcessID = ReadyQueueID.remove();
			boolean finished = false;
			boolean blocked = false;
			boolean canceled = false;
			if (currentProcess.isLargeNoFittableProcess()) {
				canceled = true;
			} else if (currentProcess.isLargeFittableProcess()) {
				for (int i = 0; i < Disk.DiskArr.size(); i++) {
					if (Disk.DiskArr.get(i) != null
							&& Disk.DiskArr.get(i).getProcessID() == currentProcess.getProcessID()) {
						Disk.DiskArr.get(i).getProcessPCB().setMB(0,
								Disk.DiskArr.get(i).getProgram().length + Disk.DiskArr.get(i).getVarSize() + 5);
						if (OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] == null
								&& OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] == null) {
							OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] = Disk.DiskArr.get(i);
							Disk.DiskArr.remove(i);
						} else if (OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] != null
								&& OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] == null) {
							Process x = OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()];
							OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] = Disk.DiskArr.get(i);
							Disk.DiskArr.remove(i);
							Disk.DiskArr.add(x);
							Disk.writeOnDisk(x);
						} else if (OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] == null
								&& OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] != null) {
							Process x = OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()];
							OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] = Disk.DiskArr.get(i);
							OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] = null;
							Disk.DiskArr.remove(i);
							Disk.DiskArr.add(x);
							Disk.writeOnDisk(x);
						} else {
							Process x = OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()];
							Process y = OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()];
							OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] = Disk.DiskArr.get(i);
							OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] = null;
							Disk.DiskArr.remove(i);
							Disk.DiskArr.add(x);
							Disk.DiskArr.add(y);
							Disk.writeOnDisk(x);
							Disk.writeOnDisk(y);
						}
						/// Disk.deleteFileOnDisk(currentProcess);
					}
				}
			} else {
				for (int i = 0; i < Disk.DiskArr.size(); i++) {
					if (Disk.DiskArr.get(i) != null
							&& Disk.DiskArr.get(i).getProcessID() == currentProcess.getProcessID()) {
						if (OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] == null) {
							Disk.DiskArr.get(i).getProcessPCB().setMB(0,
									Disk.DiskArr.get(i).getProgram().length + Disk.DiskArr.get(i).getVarSize() + 5);
							if (Disk.DiskArr.get(i).getProcessPCB().getPC() > 20 && Disk.DiskArr.get(i).isSecondLoc()) {
								Disk.DiskArr.get(i).getProcessPCB()
										.setPC(Disk.DiskArr.get(i).getProcessPCB().getPC() - 20);
								Disk.DiskArr.get(i).setFirstLoc(true);
								Disk.DiskArr.get(i).setSecondLoc(false);
							}
							OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] = Disk.DiskArr.get(i);
							Disk.DiskArr.remove(i);
						} else if (OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] == null
								&& !(OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()]
										.isLargeFittableProcess())) {
							Disk.DiskArr.get(i).getProcessPCB().setMB(20, 20 + Disk.DiskArr.get(i).getProgram().length
									+ Disk.DiskArr.get(i).getVarSize() + 5);
							if (Disk.DiskArr.get(i).getProcessPCB().getPC() < 20 && Disk.DiskArr.get(i).isFirstLoc()) {
								Disk.DiskArr.get(i).getProcessPCB()
										.setPC(Disk.DiskArr.get(i).getProcessPCB().getPC() + 20);
								Disk.DiskArr.get(i).setFirstLoc(false);
								Disk.DiskArr.get(i).setSecondLoc(true);
							}
							OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] = Disk.DiskArr.get(i);
							Disk.DiskArr.remove(i);
						} else if (OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] == null
								&& (OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()]
										.isLargeFittableProcess())) {
							Disk.DiskArr.get(i).getProcessPCB().setMB(0,
									Disk.DiskArr.get(i).getProgram().length + Disk.DiskArr.get(i).getVarSize() + 5);
							if (Disk.DiskArr.get(i).getProcessPCB().getPC() > 20 && Disk.DiskArr.get(i).isSecondLoc()) {
								Disk.DiskArr.get(i).getProcessPCB()
										.setPC(Disk.DiskArr.get(i).getProcessPCB().getPC() - 20);
								Disk.DiskArr.get(i).setFirstLoc(true);
								Disk.DiskArr.get(i).setSecondLoc(false);
							}
							Process x = OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()];
							OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] = Disk.DiskArr.get(i);
							Disk.DiskArr.get(i).setFirstLoc(true);
							Disk.DiskArr.remove(i);
							Disk.DiskArr.add(x);
							Disk.writeOnDisk(x);
						} else {
							Process x = OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()];
							if (Disk.DiskArr.get(i).getProcessPCB().getPC() < 20 && Disk.DiskArr.get(i).isFirstLoc()) {
								Disk.DiskArr.get(i).getProcessPCB()
										.setPC(Disk.DiskArr.get(i).getProcessPCB().getPC() + 20);
								Disk.DiskArr.get(i).setFirstLoc(false);
								Disk.DiskArr.get(i).setSecondLoc(true);
							}
							Disk.DiskArr.get(i).getProcessPCB().setMB(20, 20 + Disk.DiskArr.get(i).getProgram().length
									+ Disk.DiskArr.get(i).getVarSize() + 5);
							Disk.DiskArr.get(i).setSecondLoc(true);
							OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] = Disk.DiskArr.get(i);
							Disk.DiskArr.remove(i);
							Disk.DiskArr.add(x);
							Disk.writeOnDisk(x);
						}
						Disk.deleteFileOnDisk(currentProcess);
					}
				}
			}

			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------");
			System.out.println("#At time : " + Currenttime + "\nThe Current Readyqueue is: " + ReadyQueueID.toString());
			for (int i = 0; i < Quantumtime; i++) {
				for (int j = 0; j < listOfTimedprocesses.size(); j++) {
					if (listOfTimedprocesses.get(j).getSelectedTimetick() == Currenttime) {
						ReadyQueue.add(listOfTimedprocesses.get(j));
						ReadyQueueID.add("Process " + listOfTimedprocesses.get(j).getProcessID());
						parser.PogOperatingSystem.writeDataToMainMemory(listOfTimedprocesses.get(j));
						listOfTimedprocesses.remove(listOfTimedprocesses.get(j));
						System.out.println(
								"-----------------------------------------------------------------------------------------------------------------");
						System.out.println("#At time: " + Currenttime + "\nA new Process was added to the Readyqueue!"
								+ "\nThe Current Readyqueue is: " + ReadyQueueID.toString());
					}
				}
				if (currentProcess.isLargeNoFittableProcess()) {
					System.out.println("Process " + currentProcess.getProcessID()
							+ " is larger than the memory, So it cannot fit in to memory and cannot execute!!");
					canceled = true;
					break;
				} else if (currentProcess.isBlockedState()) {
					blocked = true;
				} else {
					currentProcess.getProcessPCB().setProcessState(ProcessState.RUNNING);
					currentProcess.setRunningState(true);
					String[] currentProcessProgram = currentProcess.getProgram();
					String[] currentProcessProgramMod = currentProcess.getModprogram();
					String[] line = currentProcessProgram[currentProcess.getLastLocation()].split(" ");
					String[] Modline = currentProcessProgramMod[currentProcess.getLastLocationMod()].split(" ");
					String instLine = currentProcessProgram[currentProcess.getLastLocation()];
					String instLineMod = currentProcessProgramMod[currentProcess.getLastLocationMod()];
					int currentLineLocation = currentProcess.getLastLocation() + 1;
					int currentLineLocationMod = currentProcess.getLastLocationMod() + 1;
					System.out.println(
							"-----------------------------------------------------------------------------------------------------------------");
					System.out.println("#At time : " + Currenttime + "\nProcess " + currentProcess.getProcessID()
							+ " is currently executing its " + currentLineLocationMod + "th"
							+ " instruction which is: '" + instLineMod + "' instruction !");
					parser.execute(instLine, instLineMod, currentProcess);

					if (!(Modline[0].equals("input") || Modline[0].equals("readFile"))) {
						currentProcess.setLastLocation(currentProcess.getLastLocation() + 1);
						currentProcess.setLastLocationMod(currentProcess.getLastLocationMod() + 1);
						currentProcess.getProcessPCB().setPC(currentProcess.getProcessPCB().getPC() + 1);
					} else {
						currentProcess.setLastLocationMod(currentProcess.getLastLocationMod() + 1);
					}
					parser.PogOperatingSystem.printMemory();
					currentProcess.getProcessPCB().setProcessState(ProcessState.NOTRUNNING);
					currentProcess.setRunningState(false);
					Currenttime++;
					if (currentProcess.getLastLocation() >= currentProcess.getProgram().length) {
						finished = true;
						System.out.println(
								"-----------------------------------------------------------------------------------------------------------------");
						System.out.println("#At time : " + Currenttime + "\nProcess " + currentProcess.getProcessID()
								+ " finished executing its instructions !");
						break;
					}
				}
			}
			if (canceled) {
				System.out.println(
						"-----------------------------------------------------------------------------------------------------------------");
				System.out.println("#At time : " + Currenttime + "\n Process " + currentProcess.getProcessID()
						+ " is canceled due to the large size of the process which is bigger than the memory!"
						+ "\nThe Current Readyqueue is: " + ReadyQueueID.toString());
				noOfProcessesFinished++;
				parser.PogOperatingSystem
						.setNoOfCompletedProcesses(parser.PogOperatingSystem.getNoOfCompletedProcesses() + 1);
			}
			if (finished) {
				parser.PogOperatingSystem
						.setNoOfCompletedProcesses(parser.PogOperatingSystem.getNoOfCompletedProcesses() + 1);
				noOfProcessesFinished++;
				System.out.println(
						"-----------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"#At time : " + Currenttime + "\nThe Current Readyqueue is: " + ReadyQueueID.toString());
				System.out.println("The Memory is: ");

				if (OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] != null
						&& OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()].getProcessID() == currentProcess
								.getProcessID()) {
					OS.MainMemory[parser.PogOperatingSystem.getFirstLocation()] = null;
				} else if (OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] != null
						&& OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()].getProcessID() == currentProcess
								.getProcessID()) {
					OS.MainMemory[parser.PogOperatingSystem.getSecondLocation()] = null;
				}

			}
			if (blocked) {
				System.out.println("#At time: " + Currenttime + "\nProcess " + currentProcess.getProcessID()
						+ " is currently blocked so it cant execute its " + (currentProcess.getLastLocation() + 1)
						+ "th instruction !");
			}
			if (currentProcess.getLastLocation() < currentProcess.getProgram().length
					&& !(currentProcess.isBlockedState()) && canceled == false) {
				ReadyQueue.add(currentProcess);
				ReadyQueueID.add(currentProcessID);
				System.out.println(
						"-----------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"#At time : " + Currenttime + "\nThe Current Readyqueue is: " + ReadyQueueID.toString());
			}
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------");
		parser.PogOperatingSystem.printMemory();
		System.out.println("At time : " + Currenttime + "\nAll Processes finished executing." + "\nPog Shutdown !");
	}
}
