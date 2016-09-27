package ui;

public class WhiteFilterBean {


		private int index;
		private String programName;
		private String measureHexValue;
		private int processID;	//进程ID
		private int type;	//类型编号	
		private String softwareName;	//软件名
		private String typeName;	//类型名称
		private String processDescription;	//进程描述信息
		
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
		
	
		public void setMeasureHexValue(String measureHexValue) {
			this.measureHexValue = measureHexValue;
		}
		
	
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
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
	}


