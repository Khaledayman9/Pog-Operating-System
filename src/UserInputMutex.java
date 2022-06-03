import java.util.LinkedList;
import java.util.Queue;

public class UserInputMutex {
	private Queue<Process> userInputBlockedqueue;
	private Queue<String> userInputBlockedqueueID;
	private Queue<Process> userInputReadyqueue;
	private Queue<String> userInputReadyqueueID;
	private int ownerID;
	private MutexValue MutVal;
	public UserInputMutex(MutexValue MutVal) {
		this.MutVal = MutVal;
		this.userInputBlockedqueue = new LinkedList<> ();
		this.userInputReadyqueue = new LinkedList<> ();
		this.userInputBlockedqueueID = new LinkedList<> ();
		this.userInputReadyqueueID = new LinkedList<> ();
	}
	public Queue<String> getUserInputBlockedqueueID() {
		return userInputBlockedqueueID;
	}
	public void setUserInputBlockedqueueID(Queue<String> userInputBlockedqueueID) {
		this.userInputBlockedqueueID = userInputBlockedqueueID;
	}
	public Queue<String> getUserInputReadyqueueID() {
		return userInputReadyqueueID;
	}
	public void setUserInputReadyqueueID(Queue<String> userInputReadyqueueID) {
		this.userInputReadyqueueID = userInputReadyqueueID;
	}
	public Queue<Process> getUserInputBlockedqueue() {
		return userInputBlockedqueue;
	}
	public void setUserInputBlockedqueue(Queue<Process> userInputBlockedqueue) {
		this.userInputBlockedqueue = userInputBlockedqueue;
	}
	public Queue<Process> getUserInputReadyqueue() {
		return userInputReadyqueue;
	}
	public void setUserInputReadyqueue(Queue<Process> userInputReadyqueue) {
		this.userInputReadyqueue = userInputReadyqueue;
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
