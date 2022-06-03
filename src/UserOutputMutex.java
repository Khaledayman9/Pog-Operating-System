import java.util.LinkedList;
import java.util.Queue;

public class UserOutputMutex {
	private Queue<Process> userOutputBlockedqueue;
	private Queue<String> userOutputBlockedqueueID;
	private Queue<Process> userOutputReadyqueue;
	private Queue<String> userOutputReadyqueueID;
	private int ownerID;
	private MutexValue MutVal;
	public UserOutputMutex(MutexValue MutVal) {
		this.MutVal = MutVal;
		this.userOutputBlockedqueue  = new LinkedList<> ();
		this.userOutputReadyqueue  = new LinkedList<> ();
		this.userOutputBlockedqueueID = new LinkedList<>();
		this.userOutputReadyqueueID = new LinkedList<>();
	}
	public MutexValue getMutVal() {
		return MutVal;
	}
	public Queue<Process> getUserOutputBlockedqueue() {
		return userOutputBlockedqueue;
	}
	public void setUserOutputBlockedqueue(Queue<Process> userOutputBlockedqueue) {
		this.userOutputBlockedqueue = userOutputBlockedqueue;
	}
	public Queue<Process> getUserOutputReadyqueue() {
		return userOutputReadyqueue;
	}
	public Queue<String> getUserOutputBlockedqueueID() {
		return userOutputBlockedqueueID;
	}
	public void setUserOutputBlockedqueueID(Queue<String> userOutputBlockedqueueID) {
		this.userOutputBlockedqueueID = userOutputBlockedqueueID;
	}
	public Queue<String> getUserOutputReadyqueueID() {
		return userOutputReadyqueueID;
	}
	public void setUserOutputReadyqueueID(Queue<String> userOutputReadyqueueID) {
		this.userOutputReadyqueueID = userOutputReadyqueueID;
	}
	public void setUserOutputReadyqueue(Queue<Process> userOutputReadyqueue) {
		this.userOutputReadyqueue = userOutputReadyqueue;
	}
	public int getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	public void setMutVal(MutexValue mutVal) {
		MutVal = mutVal;
	}

}


