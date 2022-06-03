import java.util.LinkedList;
import java.util.Queue;

public class FileMutex {
	private Queue<Process> fileBlockedqueue;
	private Queue<String> fileBlockedqueueID;
	private Queue<Process> fileReadyqueue;
	private Queue<String> fileReadyqueueID;
	private int ownerID;
	private MutexValue MutVal;
	public FileMutex(MutexValue MutVal) {
		this.MutVal = MutVal;
		this.fileBlockedqueue = new LinkedList<> ();
		this.fileReadyqueue  = new LinkedList<> ();
		this.fileBlockedqueueID = new LinkedList<> ();
		this.fileReadyqueueID  = new LinkedList<> ();
	}
	public Queue<String> getFileBlockedqueueID() {
		return fileBlockedqueueID;
	}
	public void setFileBlockedqueueID(Queue<String> fileBlockedqueueID) {
		this.fileBlockedqueueID = fileBlockedqueueID;
	}
	public Queue<String> getFileReadyqueueID() {
		return fileReadyqueueID;
	}
	public void setFileReadyqueueID(Queue<String> fileReadyqueueID) {
		this.fileReadyqueueID = fileReadyqueueID;
	}
	public Queue<Process> getFileBlockedqueue() {
		return fileBlockedqueue;
	}
	public void setFileBlockedqueue(Queue<Process> fileBlockedqueue) {
		this.fileBlockedqueue = fileBlockedqueue;
	}
	public Queue<Process> getFileReadyqueue() {
		return fileReadyqueue;
	}
	public void setFileReadyqueue(Queue<Process> fileReadyqueue) {
		this.fileReadyqueue = fileReadyqueue;
	}
	public int getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	public MutexValue getMutVal() {
		return MutVal;
	}
	public void setMutVal(MutexValue mutVal) {
		MutVal = mutVal;
	}

}
