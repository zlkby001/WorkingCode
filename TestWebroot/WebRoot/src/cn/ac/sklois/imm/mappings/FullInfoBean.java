package cn.ac.sklois.imm.mappings;

public class FullInfoBean {
	
	private int ID;
	
	private String softwareName="";
	
	private String digest="";
	
	private String edition="";
	
	private int classID;
	
	private int attributeID;
	
	private int trusted;
	
	private String className="";
	
	private String attributeName="";
	
	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String manufacturer="";
	
	private String issueTime="";
	
	private String description="";
	
	private String copyRight="";
	
	private int createID;
	
	private String createName;
	
	private String fileName;


	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public int getCreateID() {
		return createID;
	}

	public void setCreateID(int createID) {
		this.createID = createID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public int getAttributeID() {
		return attributeID;
	}

	public void setAttributeID(int attributeID) {
		this.attributeID = attributeID;
	}

	public int getTrusted() {
		return trusted;
	}

	public void setTrusted(int trusted) {
		this.trusted = trusted;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(String issueTime) {
		this.issueTime = issueTime;
	}

	public String getDescription() {
		if(description == null||description.equals("null")) description="";
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}
	
}
