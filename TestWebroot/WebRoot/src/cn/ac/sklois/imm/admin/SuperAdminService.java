package cn.ac.sklois.imm.admin;

import java.util.ArrayList;

public class SuperAdminService extends AdminService {
	
	public ArrayList ListAllAdmins(){
		AdminsDBBean dbBean=new AdminsDBBean();
		ArrayList c=dbBean.findAll();
		
		dbBean.destroy();
		
		return c;
	}
	
	//added by lh
	public ArrayList ListAllPassedAdmins(){
		AdminsDBBean dbBean=new AdminsDBBean();
		ArrayList c=dbBean.findAllPassed();
		
		dbBean.destroy();
		
		return c;
	}
	public ArrayList ListAllUnPassedAdmins(){
		AdminsDBBean dbBean=new AdminsDBBean();
		ArrayList c=dbBean.findAllUnpassed();
		
		dbBean.destroy();
		
		return c;
	}
	
	//end
	
	boolean DeleteAdmin(int oid){
		AdminsDBBean dbBean=new AdminsDBBean();
		boolean b=dbBean.deletebyOperatorID(oid);
		dbBean.destroy();
		return b;
	}
	
	public int checkAdmin(int OperatorID){
		AdminsDBBean dbBean=new AdminsDBBean();
		AdminBean a=dbBean.findbyOperatorID(OperatorID);
		if(a==null){//��admin�����ڣ��޷��޸ġ�
			dbBean.destroy();
			return 2;
		}
		
		//�޸�Ҫ�޸ĵģ������Ĳ��䣬���Ǵ����ݿ��в鵽�ġ�
		a.setPass("Y");
		
		boolean b=dbBean.updatebyOperatorID(OperatorID, a);
		if(b){//�޸ĳɹ�
			dbBean.destroy();
			return 0;
		}else{
			dbBean.destroy();
			return 3;//�������ݿ�ʱʧ�ܡ�
		}
	}
	
	
}
