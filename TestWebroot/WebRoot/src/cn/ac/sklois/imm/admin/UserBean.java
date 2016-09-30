package cn.ac.sklois.imm.admin;

public class UserBean {
	//zhyjun
	private String ID;
	private String Name;
	private String manufacture;
	private String lab;
	private String sequence;
	private String ip;
	private String mac;
	private String pubkey;
	private String os;
	private String EK;
	private int active;
	private String factory;
	private String technology;
	private String system;
	private String action;
	
	public String getaction(){
		return this.action;
	}
	
	public void setaction(String action){
		this.action=action;
	}
	public String getEK(){
		return this.EK;
	}
	
	public void setEK(String EK){
		this.EK=EK;
	}
	
	public int getactive(){
		return this.active;
	}
	
	public void setactive(int active){
		this.active=active;
	}
	
	public String getID(){
		return ID;
	}
	public void setID(String id){
		ID=id;
	}
	
	public String getName(){
		return Name;
	}
	public void setName(String name){
		Name=name;
	}
	
	public String getmanufacture(){
		return manufacture;
	}
	public void setmanufacture(String Manufacture){
		manufacture=Manufacture;
	}
	
	public String getlab(){
		return lab;
	}
	public void setlab(String Lab){
		lab=Lab;
	}
	
	public String getsequence(){
		return sequence;
	}
	public void setsequence(String Sequence){
		sequence=Sequence;
	}
	
	public String getip(){
		return ip;
	}
	public void setip(String Ip){
		ip=Ip;
	}
	
	public String getmac(){
		return mac;
	}
	public void setmac(String Mac){
		mac=Mac;
	}
	
	public String getpubkey(){
		return pubkey;
	}
	public void setpubkey(String Pubkey){
		pubkey=Pubkey;
	}
	
	public String getos(){
		return os;
	}
	public void setos(String os){
		this.os=os;
	}
	
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

}
