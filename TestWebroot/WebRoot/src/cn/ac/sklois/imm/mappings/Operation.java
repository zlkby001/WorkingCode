package cn.ac.sklois.imm.mappings;
import java.sql.Timestamp;

public class Operation {
	private Timestamp OperationTime;
	private int OperatorID;
	
	public Timestamp getOperationTime() {
		return OperationTime;
	}
	public void setOperationTime(Timestamp operationTime) {
		OperationTime = operationTime;
	}
	public int getOperatorID() {
		return OperatorID;
	}
	public void setOperatorID(int operatorID) {
		OperatorID = operatorID;
	}
	
}
