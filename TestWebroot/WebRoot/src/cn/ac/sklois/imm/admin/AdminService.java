package cn.ac.sklois.imm.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java1234.util.GridPageBean;

public class AdminService {
	/*public int register(String password,String name, String address, String phoneNum, String email){
		AdminBean a=new AdminBean();
		a.setPassword(password);
		a.setName(name);
		a.setAddress(address);
		a.setPhoneNum(phoneNum);
		a.setEmail(email);
		AdminsDBBean dbBean=new AdminsDBBean();
		if(nameIsExisted(name)!=0){//锟斤拷锟斤拷锟窖撅拷锟斤拷锟斤拷
			dbBean.destroy();
			return 1;
		}
		if(dbBean.add(a)){//锟缴癸拷注锟斤拷
			dbBean.destroy();
			return 0;
		}
		else{
			dbBean.destroy();
			return 2;
		}
	}
	*/
	public int register1(String password,String name, String usertype,String endtype, String address, String phoneNum, String email, String gender, String certclass, String certnumber){
		AdminBean a=new AdminBean();
		a.setPassword(password);
		a.setName(name);
		a.setUsertype(usertype);
		a.setEndType("client");
		a.setAddress(address);
		a.setPhoneNum(phoneNum);
		a.setEmail(email);
		a.setGender(gender);
		a.setCertclass(certclass);
		a.setCertnumber(certnumber);
		a.setPass("N");
		
		AdminsDBBean dbBean=new AdminsDBBean();
		if(nameIsExisted(name)!=0){
			dbBean.destroy();
			return 1;
		}
		if(dbBean.add1(a)){//锟缴癸拷注锟斤拷
			dbBean.destroy();
			return 0;
		}
		else{
			dbBean.destroy();
			return 2;
		}
	}
	//modified by lh
	public int register(String password,String name, String endtype,String address, String phoneNum, String email, String gender, String certclass, String certnumber,String usertype){
		AdminBean a=new AdminBean();
		a.setPassword(password);
		a.setName(name);
		a.setEndType("server");
		a.setAddress(address);
		a.setPhoneNum(phoneNum);
		a.setEmail(email);
		a.setGender(gender);
		a.setCertclass(certclass);
		a.setCertnumber(certnumber);
		a.setPass("N");
		a.setUsertype("manager");
		
		AdminsDBBean dbBean=new AdminsDBBean();
		if(nameIsExisted(name)!=0){//锟斤拷锟斤拷锟窖撅拷锟斤拷锟斤拷
			dbBean.destroy();
			return 1;
		}
		if(dbBean.add(a)){//锟缴癸拷注锟斤拷
			dbBean.destroy();
			return 0;
		}
		else{
			dbBean.destroy();
			return 2;
		}
	}
	//end
	
	public AdminBean loginbyOID(int oid, String password){
		AdminsDBBean dbBean=new AdminsDBBean();
		AdminBean a=dbBean.findbyOperatorID(oid);
		dbBean.destroy();
		if(a==null)return null;
		if(!password.equals(a.getPassword())){
			return null;
		}else{
			return a;
		}
	}
	
	public AdminBean loginbyName(String name, String password){
		AdminsDBBean dbBean=new AdminsDBBean();
		AdminBean a	=dbBean.findbyName(name);
		dbBean.destroy();
		if(a==null)return null;
		if(!password.equals(a.getPassword())){
			return null;
		}else{
			return a;
		}
	}
	
	public boolean logout(){//锟斤拷时为锟秸ｏ拷锟斤拷锟斤拷锟皆猴拷锟斤拷锟节硷拷录锟斤拷志锟斤拷锟斤拷锟睫改持久存储锟斤拷锟斤拷菘锟斤拷械锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		return true;
	}
	
	public boolean modifyPassword(int OperatorID,String oldpassword,String newpassword){
		AdminsDBBean dbBean=new AdminsDBBean();
		AdminBean a=dbBean.findbyOperatorID(OperatorID);
		if(a==null){//锟睫革拷失锟斤拷
			dbBean.destroy();
			return false;
		}
		if(!oldpassword.equals(a.getPassword())){//锟睫革拷失锟斤拷
			return false;
		}
		a.setPassword(newpassword);
		boolean b=dbBean.updatebyOperatorID(OperatorID, a);
		if(b){//锟睫改成癸拷
			dbBean.destroy();
			return true;
		}else{//锟睫革拷失锟斤拷
			dbBean.destroy();
			return false;
		}
	}
	
	public int modifyPersonalInfo(int OperatorID,String name, String address, String phoneNum, String email,String gender){
		AdminsDBBean dbBean=new AdminsDBBean();
		AdminBean a=dbBean.findbyOperatorID(OperatorID);
		if(a==null){//锟斤拷admin锟斤拷锟斤拷锟节ｏ拷锟睫凤拷锟睫改★拷
			dbBean.destroy();
			return 2;
		}
		int res=nameIsExisted(name);
		if(res!=0&&res!=OperatorID){//锟斤拷锟斤拷锟窖撅拷锟斤拷锟节ｏ拷锟揭诧拷锟角改讹拷之前锟斤拷锟斤拷锟街★拷
			dbBean.destroy();
			return 1;
		}
		//锟睫革拷要锟睫改的ｏ拷锟斤拷锟斤拷牟锟斤拷洌拷锟斤拷谴锟斤拷锟捷匡拷锟叫查到锟侥★拷
		a.setName(name);
		a.setAddress(address);
		a.setPhoneNum(phoneNum);
		a.setEmail(email);
		a.setGender(gender);
		
		boolean b=dbBean.updatebyOperatorID(OperatorID, a);
		if(b){//锟睫改成癸拷
			dbBean.destroy();
			return 0;
		}else{
			dbBean.destroy();
			return 3;//锟斤拷锟斤拷锟斤拷菘锟绞笔э拷堋锟�
		}
	}
	
	public AdminBean getAdmin(int oid){//为锟斤拷锟斤拷示锟斤拷锟斤拷
		AdminsDBBean dbBean=new AdminsDBBean();
	    AdminBean a=dbBean.findbyOperatorID(oid);
		dbBean.destroy();
		if(a==null){
			return null;
		}
		else{
			return a;
		}
	}
	
	public UserBean getUser(int oid){//为锟斤拷锟斤拷示锟斤拷锟斤拷
		AdminsDBBean dbBean=new AdminsDBBean();
		UserBean a=dbBean.findUserbyID(oid);
		dbBean.destroy();
		if(a==null){
			return null;
		}
		else{
			return a;
		}
	}
	
	
	public int nameIsExisted(String name){
		AdminsDBBean dbBean=new AdminsDBBean();
		AdminBean a=dbBean.findbyName1(name);
		if(a==null){//锟斤拷锟斤拷锟斤拷
			dbBean.destroy();
			return 0;
		}else{//锟斤拷锟斤拷
			dbBean.destroy();
			return Integer.parseInt(a.getOperatorID());
		}
	}
	
	//added by lh
	public int modifyCert(int OperatorID, String certclass,String certnumber){
		AdminsDBBean dbBean=new AdminsDBBean();
		AdminBean a=dbBean.findbyOperatorID(OperatorID);
		if(a==null){//锟斤拷admin锟斤拷锟斤拷锟节ｏ拷锟睫凤拷锟睫改★拷
			dbBean.destroy();
			return 2;
		}
		
		//锟睫革拷要锟睫改的ｏ拷锟斤拷锟斤拷牟锟斤拷洌拷锟斤拷谴锟斤拷锟捷匡拷锟叫查到锟侥★拷
		a.setCertclass(certclass);
		a.setCertnumber(certnumber);
		a.setPass("N");
		boolean b=dbBean.updatebyOperatorID(OperatorID, a);
		if(b){//锟睫改成癸拷
			dbBean.destroy();
			return 0;
		}else{
			dbBean.destroy();
			return 3;//锟斤拷锟斤拷锟斤拷菘锟绞笔э拷堋锟�
		}
	}
	//end
	public ArrayList ListAllUsers(String ip,String username,String pubkey){
		AdminsDBBean dbBean=new AdminsDBBean();
		ArrayList c=dbBean.findAllusers(ip,username,pubkey);
		
		dbBean.destroy();
		
		return c;
	}
	
	public ArrayList ListActiveUsers(String ip,String username,String pubkey,String EK){
		AdminsDBBean dbBean=new AdminsDBBean();
		ArrayList c=dbBean.findactiveusers(ip,username,pubkey,EK);
		
		dbBean.destroy();
		
		return c;
	}
	
	public ArrayList ListVerifylog(String ip,String username,String pubkey,String verifydate){
		AdminsDBBean dbBean=new AdminsDBBean();
		ArrayList c=dbBean.ListVerifylog(ip,username,pubkey,verifydate);
		
		dbBean.destroy();
		
		return c;
	}
	
	public boolean dorestore(int newuserid,int olduserid){
		AdminsDBBean ad=new AdminsDBBean();
		boolean b=ad.dorestore(newuserid,olduserid);
		ad.destroy();
		return b;
		
	}
	
	public boolean deleteuser(int userid){
		AdminsDBBean ad=new AdminsDBBean();
		boolean b=ad.deleteuserbyid(userid);
		ad.destroy();
		return b;
		
	}
	
	public boolean deleteAlert(int userId){
		AdminsDBBean ad=new AdminsDBBean();
		boolean b=ad.deleteAlertbyid(userId);
		ad.destroy();
		return b;
		
	}
	
	public boolean deleteAlertDetail(String ids){
		AdminsDBBean ad=new AdminsDBBean();
		boolean b=ad.deleteAlertDetailbyid(ids);
		//new Logtest().logger.info(ids + "   "+ b);
		ad.destroy();
		return b;
		
	}
	
	public boolean changeAlertStatus(String ids){
		AdminsDBBean ad=new AdminsDBBean();
		boolean b=ad.changeAlertStatus(ids);
		ad.destroy();
		return b;
		
	}
	//=============================  Get ResultSet Methods  =======================================
	
	/**
	 * 获取全部用户wyth
	 * @param ip
	 * @param username
	 * @param pubkey
	 * @return
	 */
	public ResultSet ResultAllUsers(String ip,String username,String pubkey){
		AdminsDBBean dbBean=new AdminsDBBean();
		ResultSet c=dbBean.GetAllusers(ip,username,pubkey);		
		return c;
	}
	
	public ResultSet getallrestoreusers(int newuserid){
		AdminsDBBean dbBean=new AdminsDBBean();
		ResultSet c=dbBean.getallrestoreusers(newuserid);		
		return c;
	}
	
	/**
	 * 获取报警信息
	 */
	public ResultSet ResultAlerts(GridPageBean pb){
		AdminsDBBean dbBean=new AdminsDBBean();
		ResultSet c=dbBean.GetAlerts(pb);		
		return c;
	}
	
	/**
	 * 获取详细报警信息
	 */
	public ResultSet ResultAlertsDetail(String oid,String filename,String digest,String sname,String time,String ip,GridPageBean pb){
		AdminsDBBean dbBean=new AdminsDBBean();
		ResultSet c=dbBean.GetAlertsDetail(oid,filename,digest,sname,time,ip,pb);		
		return c;
	}
	public ResultSet ResultAuditDetail(String oid,String action,String role,String user,String time,String type,GridPageBean pb){
		AdminsDBBean dbBean=new AdminsDBBean();
		ResultSet c=dbBean.GetAuditDetail(oid,action,role,user,time,type,pb);		
		return c;
	}
	
	/**
	 * 获取usb白名单
	 * 
	 */
	public ResultSet ResultUsbLocal(String oid){
		AdminsDBBean dbBean=new AdminsDBBean();
		ResultSet c=dbBean.GetUsbLocal(oid);		
		return c;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public ResultSet ResultAdmin(int oid){
//		AdminsDBBean dbBean=new AdminsDBBean();
//		ResultSet a=dbBean.GetOperatorID(oid);
//		
//		if(a==null){
//			return null;
//		}
//		else{
//			return a;
//		}
//	}
	
}
