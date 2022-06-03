
public class MemoryObject {
	private String objectName;
	private String objectData;
	private int processId;
	public MemoryObject(String objectName,String objectData,int processId) {
		this.objectName = objectName;
		this.objectData = objectData;
		this.processId = processId;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public String getObjectData() {
		return objectData;
	}
	public void setObjectData(String objectData) {
		this.objectData = objectData;
	}

}
