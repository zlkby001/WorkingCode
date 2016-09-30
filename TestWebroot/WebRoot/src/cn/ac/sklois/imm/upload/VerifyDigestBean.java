package cn.ac.sklois.imm.upload;

public class VerifyDigestBean {
	private int ID;
	
	private String name;
	
	private String digest;

	private int trusted;
	
	private String IP;
	
	private String software;
	
	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
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

	public int getTrusted() {
		return trusted;
	}

	public void setTrusted(int trusted) {
		this.trusted = trusted;
	}

}
