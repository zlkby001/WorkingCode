package ui;

import java.util.Date;

public class AlertLogMergerBean {
	private int index;
	private String programName;
	private String measureHexValue;
	private String starttime;
	private String endtime;
	private int log_nums; //出现次数
	
	//private String typeName;	//类型名称
	//private String processDescription;	//进程描述信息
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getMeasureHexValue() {
		return measureHexValue;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setMeasureHexValue(String measureHexValue) {
		this.measureHexValue = measureHexValue;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getLog_nums() {
		return log_nums;
	}
	public void setLog_nums(int log_nums) {
		this.log_nums = log_nums;
	}
	

}
