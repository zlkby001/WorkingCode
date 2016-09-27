package ui;

/* 数据库表信息
 * seq	integer	序列号，与proginfo中该字段一致，即外键约束
version	Text(10)	程序版本
ostype	Text(100)	操作系统，如区分xp sp2和sp3 （细粒度）
sha1	Text(40)	程序sha1值，十六进制存储
sm3	Text(64)	程序sm3值，十六进制存储
md5	Text(32)	程序md5值，十六进制存储
 * */

public class MeasurementBean {
	
	private int index;		// 序号
	private String program_name;	//程序名
	private String mv;		//度量值（Hash）
	private int processID;	//进程ID
	private int type;	//类型编号	
	private String softwareName;	//软件名
	private String typeName;	//类型名称
	private String processDescription;	//进程描述信息
	
	public String getSoftwareName() {
		return softwareName;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public int getProcessID() {
		return processID;
	}
	public void setProcessID(int processID) {
		this.processID = processID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
	public String getMv() {
		return mv;
	}
	public void setMv(String mv) {
		this.mv = mv;
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
