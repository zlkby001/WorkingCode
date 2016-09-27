package ui;

/*
 * 数据库表信息描述
seq	integer	序列号，自动增加，唯一
pname	Text 	程序名称（非空）
company	Text(100)	程序发布机构
description	Text(1000)	程序描述（非空，默认为未知）
stype	Text(100)	软件类型，如系统软件、工控软件等。（非空，默认为未知）
ftype	Text(20)	文件类型（exe等）
 */


public class ProcessInfoBean {
	private String filename;
	private String mv;
	private int processID;
	
	public int getProcessID() {
		return processID;
	}
	public void setProcessID(int processID) {
		this.processID = processID;
	}
	public String getMeasure(){
		return mv;
	}
	public void setMeasure(String mvalue){
		this.mv=mvalue;
	}
	public String getFileName(){
		return filename;
	}
	public void setFileName(String pn){
		this.filename=pn;	
	}
}
