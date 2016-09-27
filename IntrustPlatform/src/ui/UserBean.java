package ui;

public class UserBean {
	//user
	private String name;
	private String lab;
	
	//device
	private String ip;
	private String mac;
	private String os;
	
	//ptm
	private String sequence;
	private String manufacture;
	private String pubkey;
	private String EK;
	
	public void setEK(String EK){
		this.EK=EK;
	}
	
	public String getEK(){
		return this.EK;
	}
	
	public void setName(String name){
		this.name=name;
	}
	public void setLab(String lab){
		this.lab=lab;
	}
	public void setIp(String ip){
		this.ip=ip;
	}
	public void setMac(String mac){
		this.mac=mac;
	}
	public void setOs(String os){
		this.os=os;
	}
	public void setSequence(String sequence){
		this.sequence=sequence;
	}
	public void setManufacture(String manufacture){
		this.manufacture=manufacture;
	}
	public void setPubkey(String pubkey){
		this.pubkey=pubkey;
	}
	
	public String getName(){
		return name;
	}
	public String getLab(){
		return lab;
	}
	public String getIp(){
		return ip;
	}
	public String getMac(){
		return mac;
	}
	public String getOs(){
		return os;
	}
	public String getSequence(){
		return sequence;
	}
	public String getManufacture(){
		return manufacture;
	}
	public String getPubkey(){
		return pubkey;
	}
}
