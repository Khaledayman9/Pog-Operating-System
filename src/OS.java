import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class OS {
	private ArrayList<MemoryObject> Memory;
	private int noOfProcesses;
	private int noOfCompletedProcesses;
	private int remMainMemSize;
	private int MainMemIndex;
	private int MainMemSize;
	private int firstLocation;
	private int secondLocation;
	public static Process[] MainMemory = new Process[40];
	final int MemSize = 40;

	public OS() {
		this.Memory = new ArrayList<MemoryObject>();
		this.remMainMemSize = MemSize;
		this.firstLocation = 0;
		this.secondLocation = MemSize / 2;
		this.noOfProcesses = 0;
		this.noOfCompletedProcesses = 0;
		this.MainMemIndex = 0;
		this.MainMemSize = MemSize;

	}

	public int getMainMemSize() {
		return MainMemSize;
	}

	public int getFirstLocation() {
		return firstLocation;
	}

	public void setFirstLocation(int firstLocation) {
		this.firstLocation = firstLocation;
	}

	public int getSecondLocation() {
		return secondLocation;
	}

	public void setSecondLocation(int secondLocation) {
		this.secondLocation = secondLocation;
	}

	public void setMainMemSize(int mainMemSize) {
		MainMemSize = mainMemSize;
	}

	public int getRemMainMemSize() {
		return remMainMemSize;
	}

	public void setRemMainMemSize(int remMainMemSize) {
		this.remMainMemSize = remMainMemSize;
	}

	public int getMainMemIndex() {
		return MainMemIndex;
	}

	public void setMainMemIndex(int mainMemIndex) {
		MainMemIndex = mainMemIndex;
	}

	public int checkSize(Process a) {
		int total = a.getVarSize() + a.getProgram().length + 5;
		Process First = MainMemory[this.firstLocation];
		Process Second = MainMemory[this.secondLocation];
		if (total > 40) {
			return -2;
		} else if (total > 20 && total <= 40) {
			return -3;
		} else if (First == null) {
			return this.firstLocation;
		} else if (Second == null) {
			return this.secondLocation;
		}
		return -1;
	}

	public void writeDataToMainMemory(Process currProc) throws IOException {
		int value = this.checkSize(currProc);
		if (value == -1) {
			System.out.println("There is not enough size in the memory and the process has to be swapped with another !"
					+ "\nFor our case we swap the the process in the second block in the memory with the current process!!");
			Process a = MainMemory[this.getSecondLocation()];
			MainMemory[this.getSecondLocation()] = currProc;
			currProc.setSecondLoc(true);
			currProc.getProcessPCB().setPC(20);
			currProc.getProcessPCB().setMB(20, 20 + currProc.getVarSize() + currProc.getProgram().length + 5);
			Disk.DiskArr.add(a);
			Disk.writeOnDisk(a);
		} else if (value == -2) {
			currProc.setLargeNoFittableProcess(true);
			System.out
					.println("There is not enough size in the memory and the process cannot be swapped with another !");
		} else if (value == -3) {
			Disk.DiskArr.add(currProc);
			Disk.writeOnDisk(currProc);
			currProc.setLargeFittableProcess(true);
			System.out.println(
					"There is enough memory to store the process in the memory but we will use the 2 blocks for it when it's turn comes !");
		} else {
			MainMemory[value] = currProc;
			int ProcesSize = (currProc.getVarSize() + currProc.getProgram().length + 5);
			currProc.getProcessPCB().setPC(value);
			if (value == this.getFirstLocation()) {
				currProc.setFirstLoc(true);
				currProc.getProcessPCB().setMB(this.getFirstLocation(), this.getFirstLocation() + ProcesSize);
			} else {
				currProc.setSecondLoc(true);
				currProc.getProcessPCB().setMB(this.getSecondLocation(), this.getSecondLocation() + ProcesSize);
			}
		}
	}

	public void printMemory() {
		System.out.println(
				"___________________________________________________* The Memory *__________________________________________________");
		Process a = MainMemory[this.firstLocation];
		Process b = MainMemory[this.secondLocation];
		if (a == null) {
			System.out.println("No Process in first block yet!");
		} else {
			if (a.isLargeFittableProcess()) {
				a.printProgram();
				a.printPCB();
				a.printVariables();
			} else {
				a.printProgram();
				a.printPCB();
				a.printVariables();
			}
		}
		if (a != null && a.isLargeFittableProcess()) {
			System.out.print(" ");
		} else {
			System.out.println(
					"___________________________________________________________________________________________________________________");
		}
		if (b == null && a != null && !(a.isLargeFittableProcess())) {
			System.out.println("No Process in second block yet!");
		} else if (b == null && a == null) {
			System.out.println("No Process in second block yet!");
		} else {
			if (a != null && a.isLargeFittableProcess()) {
				System.out.print(" ");
			} else {
				b.printProgram();
				b.printPCB();
				b.printVariables();
			}
		}
		System.out.println(
				"_________________________________________________* End of Memory *_________________________________________________");
	}

	public String readFile(String filePath) throws IOException {
		String result = "";
		result = Files.readString(Paths.get(filePath + ".txt"));
		return result;
	}

	public void writeFile(String fname, String Data) throws IOException {
		File f = new File(fname + ".txt");
		FileWriter fw = new FileWriter(f);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(Data);
		pw.close();
	}

	public void writeDataToMemory(MemoryObject Data) {
		Memory.add(Data);
	}

	public MemoryObject readDataFromMemory(int i) {
		return Memory.get(i);
	}

	public void print(String x) {
		System.out.println(x);
	}

	public String input() {
		Scanner Sc = new Scanner(System.in);
		System.out.println("Please enter a value");
		String text = Sc.nextLine();
		return text;
	}

	public void printFromTo(int a, int b) {
		if (a < b) {
			int c = a++;
			System.out.print("The Range (Inclusive) is : [ ");
			while (c <= b) {
				if (c == b) {
					System.out.print(c);
				} else {
					System.out.print(c + " ---> ");
				}
				c++;
			}
			System.out.print(" ]");
		} else {
			int c = b++;
			System.out.print("The Range (Inclusive) is : [ ");
			while (c <= a) {
				if (c == a) {
					System.out.print(c);
				} else {
					System.out.print(c + " ---> ");
				}
				c++;
			}
			System.out.print(" ]");
		}
	}

	public int getNoOfProcesses() {
		return noOfProcesses;
	}

	public void setNoOfProcesses(int noOfProccesses) {
		this.noOfProcesses = noOfProccesses;
	}

	public int getNoOfCompletedProcesses() {
		return noOfCompletedProcesses;
	}

	public void setNoOfCompletedProcesses(int noOfCompletedProcesses) {
		this.noOfCompletedProcesses = noOfCompletedProcesses;
	}

	public ArrayList<MemoryObject> getMemory() {
		return Memory;
	}

	public void setMemory(ArrayList<MemoryObject> memory) {
		Memory = memory;
	}

}
