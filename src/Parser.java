import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;

//Team_44
//Here we have the main method that will create the processes and run the OS :)

public class Parser {
	OS PogOperatingSystem;
	Scheduler PogScheduler;
	int CurrentStime;
	private UserInputMutex userInMux;
	private UserOutputMutex userOutMux;
	private FileMutex fileMux;

	public Parser(int Quantumtime) {
		PogOperatingSystem = new OS();
		PogScheduler = new Scheduler(Quantumtime, this);
		userInMux = new UserInputMutex(MutexValue.ONE);
		userOutMux = new UserOutputMutex(MutexValue.ONE);
		fileMux = new FileMutex(MutexValue.ONE);
	}

	public void semWait(String s, Process p) {
		if (s == "userOutput") {
			if (userOutMux.getMutVal().equals(MutexValue.ONE)) {
				userOutMux.setOwnerID(p.getProcessID());
				userOutMux.setMutVal(MutexValue.ZERO);
			} else {
				Queue<Process> A = userOutMux.getUserOutputBlockedqueue();
				Queue<String> AS = userOutMux.getUserOutputBlockedqueueID();
				A.add(p);
				AS.add("Process " + p.getProcessID());
				Scheduler.ReadyQueue.remove(p);
				Scheduler.ReadyQueueID.remove("Process " + p.getProcessID());
				Scheduler.BlockQueue.add(p);
				Scheduler.BlockQueueID.add("Process " + p.getProcessID());
				this.PogOperatingSystem.setNoOfProcesses(this.PogOperatingSystem.getNoOfProcesses() - 1);
				p.setBlockedState(true);
				userOutMux.setUserOutputBlockedqueue(A);
				userOutMux.setUserOutputBlockedqueueID(AS);
				System.out.println("At time : " + Scheduler.Currenttime + "\nThe Current Readyqueue is: "
						+ Scheduler.ReadyQueueID.toString() + "\nThe Current Blockedqueue is: "
						+ Scheduler.BlockQueueID.toString() + "\nThe Current Blockedqueue for resource UserOutput is: "
						+ userOutMux.getUserOutputBlockedqueueID().toString());

			}
		} else if (s == "userInput") {
			if (userInMux.getMutVal().equals(MutexValue.ONE)) {
				userInMux.setOwnerID(p.getProcessID());
				userInMux.setMutVal(MutexValue.ZERO);
			} else {
				Queue<Process> A = userInMux.getUserInputBlockedqueue();
				Queue<String> AS = userInMux.getUserInputBlockedqueueID();
				A.add(p);
				AS.add("Process " + p.getProcessID());
				Scheduler.ReadyQueue.remove(p);
				Scheduler.ReadyQueueID.remove("Process " + p.getProcessID());
				Scheduler.BlockQueue.add(p);
				Scheduler.BlockQueueID.add("Process " + p.getProcessID());
				this.PogOperatingSystem.setNoOfProcesses(this.PogOperatingSystem.getNoOfProcesses() - 1);
				p.setBlockedState(true);
				userInMux.setUserInputBlockedqueue(A);
				userInMux.setUserInputBlockedqueueID(AS);
				System.out.println("At time : " + Scheduler.Currenttime + "\nThe Current Readyqueue is: "
						+ Scheduler.ReadyQueueID.toString() + "\nThe Current Blockedqueue is: "
						+ Scheduler.BlockQueueID.toString() + "\nThe Current Blockedqueue for resource UserInput is: "
						+ userInMux.getUserInputBlockedqueueID().toString());
			}
		} else {
			if (fileMux.getMutVal().equals(MutexValue.ONE)) {
				fileMux.setOwnerID(p.getProcessID());
				fileMux.setMutVal(MutexValue.ZERO);
			} else {
				Queue<Process> A = fileMux.getFileBlockedqueue();
				Queue<String> AS = fileMux.getFileBlockedqueueID();
				A.add(p);
				AS.add("Process " + p.getProcessID());
				Scheduler.ReadyQueue.remove(p);
				Scheduler.ReadyQueueID.remove("Process " + p.getProcessID());
				Scheduler.BlockQueue.add(p);
				Scheduler.BlockQueueID.add("Process " + p.getProcessID());
				this.PogOperatingSystem.setNoOfProcesses(this.PogOperatingSystem.getNoOfProcesses() - 1);
				p.setBlockedState(true);
				fileMux.setFileBlockedqueue(A);
				fileMux.setFileBlockedqueueID(AS);
				System.out.println("At time : " + Scheduler.Currenttime + "\nThe Current Readyqueue is: "
						+ Scheduler.ReadyQueueID.toString() + "\nThe Current Blockedqueue is: "
						+ Scheduler.BlockQueueID.toString() + "\nThe Current Blockedqueue for resource File is: "
						+ fileMux.getFileBlockedqueueID().toString());
			}
		}
	}

	public void semSignal(String s, Process p) {
		if (s == "userOutput") {
			if (userOutMux.getOwnerID() == p.getProcessID()) {
				if (userOutMux.getUserOutputBlockedqueue().isEmpty()) {
					userOutMux.setMutVal(MutexValue.ONE);
				} else {
					Queue<Process> A = userOutMux.getUserOutputBlockedqueue();
					Queue<String> AS = userOutMux.getUserOutputBlockedqueueID();
					Process lastProcess = A.remove();
					A.remove(lastProcess);
					AS.remove("Process " + lastProcess.getProcessID());
					lastProcess.setBlockedState(false);
					userOutMux.setUserOutputBlockedqueue(A);
					userOutMux.setUserOutputBlockedqueueID(AS);
					Scheduler.BlockQueue.remove(lastProcess);
					Scheduler.BlockQueueID.remove("Process " + lastProcess.getProcessID());
					Scheduler.ReadyQueue.add(lastProcess);
					Scheduler.ReadyQueueID.add("Process " + lastProcess.getProcessID());
					this.PogOperatingSystem.setNoOfProcesses(this.PogOperatingSystem.getNoOfProcesses() + 1);
					userOutMux.setOwnerID(lastProcess.getProcessID());
					System.out.println("At time : " + Scheduler.Currenttime + "\nThe Current Blockedqueue is: "
							+ Scheduler.BlockQueueID.toString() + "\nThe Current Readyqueue is: "
							+ Scheduler.ReadyQueueID.toString()
							+ "\nThe Current Blockedqueue for resource userOutput is: "
							+ userOutMux.getUserOutputBlockedqueueID().toString());
				}
			}
		} else if (s == "userInput") {
			if (userInMux.getOwnerID() == p.getProcessID()) {
				if (userInMux.getUserInputBlockedqueue().isEmpty()) {
					userInMux.setMutVal(MutexValue.ONE);
				} else {
					Queue<Process> A = userInMux.getUserInputBlockedqueue();
					Queue<String> AS = userInMux.getUserInputBlockedqueueID();
					Process lastProcess = A.remove();
					A.remove(lastProcess);
					AS.remove("Process " + lastProcess.getProcessID());
					lastProcess.setBlockedState(false);
					userInMux.setUserInputBlockedqueue(A);
					userInMux.setUserInputBlockedqueueID(AS);
					Scheduler.BlockQueue.remove(lastProcess);
					Scheduler.BlockQueueID.remove("Process " + lastProcess.getProcessID());
					Scheduler.ReadyQueue.add(lastProcess);
					Scheduler.ReadyQueueID.add("Process " + lastProcess.getProcessID());
					this.PogOperatingSystem.setNoOfProcesses(this.PogOperatingSystem.getNoOfProcesses() + 1);
					userInMux.setOwnerID(lastProcess.getProcessID());
					System.out.println("At time : " + Scheduler.Currenttime + "\nThe Current Blockedqueue is: "
							+ Scheduler.BlockQueueID.toString() + "\nThe Current Readyqueue is: "
							+ Scheduler.ReadyQueueID.toString()
							+ "\nThe Current Blockedqueue for resource userInput is: "
							+ userInMux.getUserInputBlockedqueueID().toString());
				}
			}
		} else {
			if (fileMux.getOwnerID() == p.getProcessID()) {
				if (fileMux.getFileBlockedqueue().isEmpty()) {
					fileMux.setMutVal(MutexValue.ONE);
				} else {
					Queue<Process> A = fileMux.getFileBlockedqueue();
					Queue<String> AS = fileMux.getFileBlockedqueueID();
					Process lastProcess = A.remove();
					A.remove(lastProcess);
					AS.remove("Process " + lastProcess.getProcessID());
					lastProcess.setBlockedState(false);
					fileMux.setFileBlockedqueue(A);
					fileMux.setFileBlockedqueueID(AS);
					Scheduler.BlockQueue.remove(lastProcess);
					Scheduler.BlockQueueID.remove("Process " + lastProcess.getProcessID());
					Scheduler.ReadyQueue.add(lastProcess);
					Scheduler.ReadyQueueID.add("Process " + lastProcess.getProcessID());
					this.PogOperatingSystem.setNoOfProcesses(this.PogOperatingSystem.getNoOfProcesses() + 1);
					fileMux.setOwnerID(lastProcess.getProcessID());
					System.out.println("At time : " + Scheduler.Currenttime + "\nThe Current Blockedqueue is: "
							+ Scheduler.BlockQueueID.toString() + "\nThe Current Readyqueue is: "
							+ Scheduler.ReadyQueueID.toString() + "\nThe Current Blockedqueue for resource File is: "
							+ fileMux.getFileBlockedqueueID().toString());
				}
			}
		}
	}

	public int noOfLinesPerFile(String fn) throws IOException {
		File f = new File(fn + ".txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int numberOfLines = 0;
		while ((line = br.readLine()) != null) {
			numberOfLines++;
		}
		return numberOfLines;
	}

	public int noOfLinesPerFileMod(String fn) throws IOException {
		File f = new File(fn + ".txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int numberOfLines = 0;
		while ((line = br.readLine()) != null) {
			String[] x = line.split(" ");
			if (!(this.EqlessThanTwo(x)) && (x[2].equals("readFile") || x[2].equals("input"))) {
				for (int j = 0; j < 2; j++) {
					numberOfLines++;
				}
			} else {
				numberOfLines++;
			}
		}
		return numberOfLines;
	}

	public void assign(String x, String y) {
		x = y;
	}

	public boolean EqlessThanTwo(String arr[]) {
		if (arr.length <= 2) {
			return true;
		} else {
			return false;
		}
	}

	public String[] readLinesMod(String fn) throws IOException {
		File f = new File(fn + ".txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int numberOfLines = noOfLinesPerFileMod(fn);
		String[] arr = new String[numberOfLines];
		int i = 0;
		while ((line = br.readLine()) != null) {
			String[] x = line.split(" ");
			boolean flag = false;
			if (!(this.EqlessThanTwo(x)) && (x[2].equals("input"))) {
				for (int j = 0; j < 2; j++) {
					if (!flag) {
						arr[i] = x[2];
						i++;
						flag = true;
					} else {
						arr[i] = x[0] + " " + x[1];
						i++;
					}
				}
			} else if (!(this.EqlessThanTwo(x)) && (x[2].equals("readFile"))) {
				for (int j = 0; j < 2; j++) {
					if (!flag) {
						arr[i] = x[2] + " " + x[3];
						i++;
						flag = true;
					} else {
						arr[i] = x[0] + " " + x[1];
						i++;
					}
				}
			} else {
				arr[i] = line;
				i++;
			}
		}
		br.close();
		fr.close();
		return arr;
	}

	public String[] readLines(String fn) throws IOException {
		File f = new File(fn + ".txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int numberOfLines = noOfLinesPerFile(fn);
		String[] arr = new String[numberOfLines];
		int i = 0;
		while ((line = br.readLine()) != null) {
			arr[i] = line;
			i++;
		}
		br.close();
		fr.close();
		return arr;
	}

	public ArrayList<String> readLinesRem(String fn) throws IOException {
		File f = new File(fn + ".txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int numberOfLines = noOfLinesPerFile(fn);
		ArrayList<String> arr = new ArrayList<String>(numberOfLines);
		int i = 0;
		while ((line = br.readLine()) != null) {
			arr.add(i, line);
			i++;
		}
		br.close();
		fr.close();
		return arr;
	}

	public void execute(String instruction, String instructionMod, Process p) throws IOException {
		String[] instructionLine = instruction.split(" ");
		String[] instructionLineMod = instructionMod.split(" ");
		if (instructionLineMod[0].equals("semWait")) {
			if (instructionLineMod[1].equals("userInput")) {
				this.semWait("userInput", p);
			} else if (instructionLineMod[1].equals("userOutput")) {
				this.semWait("userOutput", p);
			} else {
				this.semWait("file", p);
			}
		}
		if (instructionLineMod[0].equals("semSignal")) {
			if (instructionLineMod[1].equals("userInput")) {
				this.semSignal("userInput", p);
			} else if (instructionLineMod[1].equals("userOutput")) {
				this.semSignal("userOutput", p);
			} else {
				this.semSignal("file", p);
			}
		}
		if (instructionLineMod[0].equals("input")) {
			String Input = PogOperatingSystem.input();
			PogOperatingSystem.writeDataToMemory(new MemoryObject("Input", Input, p.getProcessID()));
		}
		if (instructionLineMod[0].equals("readFile")) {
			String varName = instructionLineMod[1];
			int Location = -1;
			for (int i = 0; i < PogOperatingSystem.getMemory().size(); i++) {
				if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName)
						&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
					Location = i;
				}
			}
			if (Location == -1) {
				System.out.println("The variable '" + varName + "'does not exist!!!" + "\n Pog Out !");
			} else {
				String varData = PogOperatingSystem.getMemory().get(Location).getObjectData();
				try {
					String result = PogOperatingSystem.readFile(varData);
					PogOperatingSystem.writeDataToMemory(new MemoryObject("readFileInput", result, p.getProcessID()));
				} catch (Exception e) {
					System.out.println("There is not a file with that specified name (value of variable '" + varName
							+ "' ,that is: " + varData + ") ,error at readFile");
				}
			}
		}
		if (instructionLineMod[0].equals("assign")) {
			if (instructionLine[2].equals("input")) {
				String varName1 = instructionLineMod[1];
				String varName2 = "Input";
				int Location1 = -1;
				int Location2 = -1;
				for (int i = 0; i < PogOperatingSystem.getMemory().size(); i++) {
					if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName1)
							&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
						Location1 = i;
					}
					if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName2)
							&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
						Location2 = i;
					}
				}
				if (Location1 != -1 && Location2 == -1) {
					System.out.println(
							"The input data is not available at the memory since the user did not input any thing & there isn't a memory entry with that specified name !"
									+ "\n Thus the value of variable '" + varName1 + "' remains the same"
									+ "\nPog Out !");
				} else if (Location1 == -1 && Location2 != -1) {
					String varData2 = (PogOperatingSystem.readDataFromMemory(Location2).getObjectData());
					PogOperatingSystem.writeDataToMemory(new MemoryObject(varName1, varData2, p.getProcessID()));
					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, varData2, p.getProcessID()));
						System.out.println("The variable '" + varName1 + "' does not exist in the memory"
								+ "\n, Thus we created an instance of the variable having the value: " + varData2
								+ "\nPog Out !");
					}
				} else if (Location1 == -1 && Location2 == -1) {
					this.assign(varName1, "");
					PogOperatingSystem.writeDataToMemory(new MemoryObject(varName1, "", p.getProcessID()));
					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, "", p.getProcessID()));
						System.out.println("The input variable does not exist in the memory"
								+ "\n, and the input data is not available at the memory since there is not an entry in the memory with that specified name !"
								+ "\n, Thus we created an instance of the variable '" + varName1 + "'" + "\nPog Out !");
					}
				} else {
					String varData2 = (PogOperatingSystem.readDataFromMemory(Location2).getObjectData());
					String varData1 = (PogOperatingSystem.readDataFromMemory(Location1).getObjectData());
					this.assign(varData1, varData2);
					PogOperatingSystem.readDataFromMemory(Location1).setObjectData(varData2);
					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, varData2, p.getProcessID()));
						System.out.println("At time : " + Scheduler.Currenttime + "\nThe data variable named: "
								+ varName1 + " that belongs to process: Process " + p.getProcessID()
								+ "\nwas assined to value: " + varData2 + "\nPog Out !");
					}
				}
			} else if (instructionLine[2].equals("readFile")) {
				String varName1 = instructionLineMod[1];
				String varName2 = "readFileInput";
				int Location1 = -1;
				int Location2 = -1;
				for (int i = 0; i < PogOperatingSystem.getMemory().size(); i++) {
					if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName1)
							&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
						Location1 = i;
					}
					if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName2)
							&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
						Location2 = i;
					}
				}
				if (Location1 == -1 && Location2 == -1) {
					this.assign(varName1, "");
					PogOperatingSystem.writeDataToMemory(new MemoryObject(varName1, "", p.getProcessID()));
					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, "", p.getProcessID()));
						System.out.println("The input variable does not exist in the memory"
								+ "\n, and the file's data read is not available at the memory since there is not a file with that specified name !"
								+ "\n, Thus we created an instance of the variable '" + varName1 + "'" + "\nPog Out !");
					}
				} else if (Location1 != -1 && Location2 == -1) {
					System.out.println(
							"The file's data read is not available at the memory since there is not a file with that specified name !"
									+ "\n Thus the value of variable '" + varName1 + "' remains the same"
									+ "\nPog Out !");
				} else if (Location1 == -1 && Location2 != -1) {
					String varData2 = (PogOperatingSystem.readDataFromMemory(Location2).getObjectData());
					PogOperatingSystem.writeDataToMemory(new MemoryObject(varName1, varData2, p.getProcessID()));

					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, varData2, p.getProcessID()));
						System.out.println("The variable '" + varName1 + "' does not exist in the memory"
								+ "\n, Thus we created an instance of the variable having the value: " + varData2
								+ "\nPog Out !");
					}
				} else {
					String varData1 = (PogOperatingSystem.readDataFromMemory(Location1).getObjectData());
					String varData2 = (PogOperatingSystem.readDataFromMemory(Location2).getObjectData());
					this.assign(varData1, varData2);
					PogOperatingSystem.readDataFromMemory(Location1).setObjectData(varData2);
					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, varData2, p.getProcessID()));
						System.out.println("At time : " + Scheduler.Currenttime + "\nThe data variable named: "
								+ varName1 + " that belongs to process: Process " + p.getProcessID()
								+ "\nwas assined to value: " + varData2 + "\nPog Out !");
					}
				}
			} else {
				String varName1 = instructionLineMod[1];
				String varName2 = instructionLineMod[2];
				int Location1 = -1;
				int Location2 = -1;
				for (int i = 0; i < PogOperatingSystem.getMemory().size(); i++) {
					if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName1)
							&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
						Location1 = i;
					}
					if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName2)
							&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
						Location2 = i;
					}
				}
				if (Location1 == -1 && Location2 == -1) {
					this.assign(varName1, "");
					PogOperatingSystem.writeDataToMemory(new MemoryObject(varName1, "", p.getProcessID()));
					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, "", p.getProcessID()));
						System.out.println("The input variables both do not exist in the memory"
								+ "\n, Thus we created an instance of the variable '" + varName1 + "'" + "\nPog Out !");
					}
				} else if (Location1 != -1 && Location2 == -1) {
					System.out.println(
							"The second variable is not available at the memory since there is not a file with that specified name !"
									+ "\n Thus the value of variable '" + varName1 + "' remains the same"
									+ "\nPog Out !");
				} else if (Location1 == -1 && Location2 != -1) {
					String varData2 = (PogOperatingSystem.readDataFromMemory(Location2).getObjectData());
					PogOperatingSystem.writeDataToMemory(new MemoryObject(varName1, varData2, p.getProcessID()));
					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, varData2, p.getProcessID()));
						System.out.println("The variable '" + varName1 + "' does not exist in the memory"
								+ "\n, Thus we created an instance of the variable having the value: " + varData2
								+ "\nPog Out !");
					}
				} else {
					String varData1 = (PogOperatingSystem.readDataFromMemory(Location1).getObjectData());
					String varData2 = (PogOperatingSystem.readDataFromMemory(Location2).getObjectData());
					this.assign(varData1, varData2);

					if (p.getVariables().size() == p.getVarSize()) {
						System.out.println("Process " + p.getProcessID()
								+ "have reached the maximum variable size in the memory which is " + p.getVarSize()
								+ " ,so we cant have more variables!");
					} else {
						p.getVariables().add(new MemoryObject(varName1, varData2, p.getProcessID()));
						System.out.println("At time : " + Scheduler.Currenttime + "\nThe data variable named: "
								+ varName1 + " that belongs to process: Process " + p.getProcessID()
								+ "\nwas assined to value: " + varData2 + "\nPog Out !");
					}
					PogOperatingSystem.readDataFromMemory(Location1).setObjectData(varData2);
				}
			}
		}
		if (instructionLine[0].equals("printFromTo")) {
			String varName1 = instructionLine[1];
			String varName2 = instructionLine[2];
			int Location1 = -1;
			int Location2 = -1;
			for (int i = 0; i < PogOperatingSystem.getMemory().size(); i++) {
				if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName1)
						&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
					Location1 = i;
				}
				if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName2)
						&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
					Location2 = i;
				}
			}
			if (Location1 == -1 || Location2 == -1) {
				System.out.println("One of the variables does not exist!!!" + "\n Pog Out !");
			} else {
				try {
					int varData1 = Integer.parseInt(PogOperatingSystem.readDataFromMemory(Location1).getObjectData());
					int varData2 = Integer.parseInt(PogOperatingSystem.readDataFromMemory(Location2).getObjectData());
					PogOperatingSystem.printFromTo(varData1, varData2);
					System.out.println("\nPog Out !");
				} catch (Exception e) {
					System.out.println(
							"The variables you have some of them or all of them are not integers, so we cant print the range between them!");
				}

			}
		}
		if (instructionLine[0].equals("writeFile")) {
			String varName1 = instructionLine[1];
			String varName2 = instructionLine[2];
			int Location1 = -1;
			int Location2 = -1;
			for (int i = 0; i < PogOperatingSystem.getMemory().size(); i++) {
				if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName1)
						&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
					Location1 = i;
				}
				if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName2)
						&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
					Location2 = i;
				}
			}
			if (Location1 == -1 && Location2 == -1) {
				System.out.println("Both of the variables do not exist !" + "\n Pog Out !");
			} else if ((Location1 == -1 && Location2 != -1) || (Location1 != -1 && Location2 == -1)) {
				System.out.println("One of the variables does not exist !" + "\n Pog Out !");
			} else {
				String varData1 = String.valueOf(PogOperatingSystem.readDataFromMemory(Location1).getObjectData());
				String varData2 = String.valueOf(PogOperatingSystem.readDataFromMemory(Location2).getObjectData());
				PogOperatingSystem.writeFile(varData1, varData2);
			}
		}
		if (instructionLine[0].equals("print")) {
			String varName = instructionLine[1];
			int Location = -1;
			for (int i = 0; i < PogOperatingSystem.getMemory().size(); i++) {
				if (PogOperatingSystem.getMemory().get(i).getObjectName().equals(varName)
						&& PogOperatingSystem.getMemory().get(i).getProcessId() == p.getProcessID()) {
					Location = i;
				}
			}
			if (Location == -1) {
				System.out.println("The variable '" + varName + "' does not exist!!!" + "\n Pog Out !");
			} else {
				String varData = String.valueOf(PogOperatingSystem.readDataFromMemory(Location).getObjectData());
				PogOperatingSystem.print("Value of variable '" + varName + "' is : " + varData);
			}
		}
	}

	public int findProcessNum(String fn) throws IOException {
		int i;
		for (i = 0; i < fn.length() - 1; i++) {
			if (Character.isDigit(fn.charAt(i))) {
				break;
			}
		}
		int c = Character.getNumericValue(fn.charAt(i));
		return c;
	}

	public void createProcess(String fn, int timeSelected) throws IOException {
		String[] processProgram = this.readLines(fn);
		String[] ModprocessProgram = this.readLinesMod(fn);
		int ID = this.findProcessNum(fn);
		Process p = new Process(ID, processProgram);
		p.setModprogram(ModprocessProgram);
		p.setSelectedTimetick(timeSelected);
		PogOperatingSystem.setNoOfProcesses(PogOperatingSystem.getNoOfProcesses() + 1);
		Scheduler.listOfTimedprocesses.add(p);
	}

	public static void main(String[] args) throws IOException {
		Parser p = new Parser(2); // Here 2 stands for the Quantumtime (time slice)
		p.createProcess("Program_1", 0);
		p.createProcess("Program_2", 1);
		p.createProcess("Program_3", 4);

		p.PogScheduler.RobinRoundScheduling();
	}
}
