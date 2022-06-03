import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Disk {
	public static ArrayList<Process> DiskArr = new ArrayList<>();

	public Disk() {

	}

	public static void writeOnDisk(Process p) throws IOException {
		File f = new File("Process_" + p.getProcessID() + ".txt");
		FileWriter fw = new FileWriter(f);
		PrintWriter pw = new PrintWriter(fw);
		for (int i = 0; i < p.getProgram().length; i++) {
			pw.println(p.getProgram()[i]);
		}
		pw.println("Process ID: " + p.getProcessPCB().getProcessId() + "\nProcess State: "
				+ p.getProcessPCB().getProcessState().toString() + "\nPC: " + p.getProcessPCB().getPC()
				+ "\nFirst location: " + p.getProcessPCB().getALLMB().getL() + "\nLast location: "
				+ p.getProcessPCB().getALLMB().getH());
		for (int i = 0; i < p.getVariables().size(); i++) {
			System.out.println("The Variable name: " + p.getVariables().get(i).getObjectName() + ",and its Data:"
					+ p.getVariables().get(i).getObjectData());
		}
		pw.close();
	}

	public static void deleteFileOnDisk(Process p) throws IOException {
		File f = new File("Process_" + p.getProcessID() + ".txt");
		FileWriter fw = new FileWriter(f);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(" ");
		pw.close();
	}
}
