package cn.ac.sklois.imm.admin;

public class AdminBean {
	private String OperatorID;
	private String Password;
	private String Name;
	private String Address;
	private String PhoneNum;
	private String Email;

	private String Usertype;
	private String EndType;
	

	/*add by lh*/
	private String gender;
	private String certclass;
	private String certnumber;
	private String pass;
	
	public String getEndType() {
		return EndType;
	}
	public void setEndType(String endType) {
		EndType = endType;
	}
	
	//end
	public String getUsertype() {
		return Usertype;
	}
	public void setUsertype(String usertype) {
		Usertype = usertype;
	}
	public String getOperatorID() {
		return OperatorID;
	}
	public void setOperatorID(String operatorID) {
		OperatorID = operatorID;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPhoneNum() {
		return PhoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	//add by lh
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCertclass() {
		return certclass;
	}
	public void setCertclass(String certclass) {
		this.certclass = certclass;
	}
	public String getCertnumber() {
		return certnumber;
	}
	public void setCertnumber(String certnumber) {
		this.certnumber = certnumber;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	//end
	
}
