package cn.ac.sklois.imm.mappings;

public class DigestBean {
	private int ID;
	
	private String name;
	
	private String digest;
	
	private String edition;
	
	private int classID;
	
	private int attributeID;
	
	private int trusted;
	
	private String className;
	
	private String attributeName;
	
	private int createID;
	
	private String createName;
	
	private String RPMname;
	
	public String getRPMname() {
		return RPMname;
	}

	public void setRPMname(String rPMname) {
		RPMname = rPMname;
	}

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

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
