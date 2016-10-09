package ui;

import java.util.Calendar;

public class ErrorListBean {
	
	private String programName;
	private String measureHexValue;
	private String measuretime;
	private String typeName;	//类型名称
	private String processDescription;	//进程描述信息
	private Calendar measuredate; //by vonwaist
	
	public Calendar getMeasuredate() {
		return measuredate;
	}
	public void setMeasuredate(Calendar measuredate) {
		this.measuredate = measuredate;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String pname) {
		this.programName = pname;
	}
	public String getMeasureValue(){
		return measureHexValue;
	}
	public void setMeasureValue(String mv){
		this.measureHexValue = mv;
	}
	public String getMeasuretime(){
		return measuretime;
	}
	public void setMeasuretime(String mt){
		this.measuretime = mt;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}
	public String getProcessDescription() {
		return processDescription;
	}
}
