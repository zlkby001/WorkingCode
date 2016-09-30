package cn.ac.sklois.imm.mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.ics.usb.UsbBean;

import cn.ac.sklois.imm.admin.AdminBean;
import cn.ac.sklois.imm.admin.AdminsDBBean;
import cn.ac.sklois.imm.admin.UserBean;
import cn.ac.sklois.imm.upload.VerifyDigestBean;


public class MappingService {

	public int AddMapping(MappingBean mb){
		MappingsDBBean dbBean=new MappingsDBBean();
		AttIDtoNameDBBean AttdbBean=new AttIDtoNameDBBean();
		boolean b=AttIDIsExisted(mb.getAtt().getAttributeID());
		
		if(!b){//������ID�����ڣ�����ʹ�ã�����ֱ���˳������ʧ�ܡ�
			dbBean.destroy();
			return 1;
		}
		if(dbBean.add(mb)){//�ɹ����
			dbBean.destroy();
			return 0;
		}
		else{//δ�ɹ����
			dbBean.destroy();
			return 2;
		}
	}
	
	
	
	public boolean addWhitebyfilenameandhash(String filename,String digest,int userid){
		boolean b=false;
		MappingsDBBean mb=new MappingsDBBean();
		b=mb.addWhitelistbyfilenameandhash(filename,digest,userid);
		return b;
	}
	
	public boolean AddInfo(FullInfoBean fb)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b;
		b = dbBean.add(fb);
		
		dbBean.destroy();
		return b;
	}
	public int checkaudit()
	{
		MappingsDBBean mb = new MappingsDBBean();
		int res=mb.checkaudit();
		return res;
	}

	public boolean createwlsnapshot(WhitelistSearchBean wsearch,int userid)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b;
		b = dbBean.createwlsnapshot(wsearch,userid);
		
		dbBean.destroy();
		return b;
	}
	
	public boolean AddWhite(int id,int userid)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b;
		b = dbBean.addWhiteByHistory(id,userid);
		
		dbBean.destroy();
		return b;
	}
	
	public boolean addknowledge(int id,String kname)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b;
		b = dbBean.addknowledge(id,kname);
		dbBean.destroy();
		return b;
	}
	
	public boolean DumpHistoryToKnowledge(HistorySearchBean hsearch,int userid,String kname)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b;
		b = dbBean.DumpHistoryToKnowledge(hsearch,userid,kname);
		
		dbBean.destroy();
		return b;
	}
	
	public boolean DumpHistoryToWhite(HistorySearchBean hsearch,int userid)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b;
		b = dbBean.DumpHistoryToWhite(hsearch,userid);
		
		dbBean.destroy();
		return b;
	}
	
	//attid+attvalue+hash��Ψһȷ��һ��ֵ
	boolean DeleteMapping(Attribute att,String HashValue){
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b=dbBean.deletebyAttributeAndHash(att, HashValue);
		dbBean.destroy();
		return b;
	}
	
	boolean DeleteInfo(int aid)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		boolean b = dbBean.deletebyID(aid);
		dbBean.destroy();
		return b;
	}
	
	boolean DeleteUserHistory(int userid)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		boolean b = dbBean.deleteUserHistorylist(userid);
		dbBean.destroy();
		return b;
	}
	
	boolean ClearHistory(HistorySearchBean hsearch,int userid)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		boolean b = dbBean.ClearHistory(hsearch,userid);
		dbBean.destroy();
		return b;
	}
	
	boolean ClearWhitelist(WhitelistSearchBean wsearch,int userid)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		boolean b = dbBean.ClearWhitelist(wsearch,userid);
		dbBean.destroy();
		return b;
	}
	ArrayList ExportWhitelist(WhitelistSearchBean wsearch,int userid)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		ArrayList b = dbBean.ExportWhitelist(wsearch,userid);
		dbBean.destroy();
		return b;
	}
	
	boolean DeleteWhiteInfo(int aid)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		boolean b = dbBean.deleteWhitebyID(aid);
		dbBean.destroy();
		return b;
	}
	
	public boolean DeleteVerifyFailInfo(int aid)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		boolean b = dbBean.deleteVerifyInfobyID(aid);
		dbBean.destroy();
		return b;
	}
	
	public int getIDByNameHash(String name,String digest)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		int i = dbBean.findByHashandName(name, digest);
		dbBean.destroy();
		return i;
	}
	
	public int getIDByNameHashSN(String name,String digest,String softwarename)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		int i = dbBean.findByHashandNameandSN(name, digest,softwarename);
		dbBean.destroy();
		return i;
	}
	
	public MappingBean getMappingbyIDValueHash(int aid,String avalue, String hash){//Ϊ����ʾ����
		MappingsDBBean dbBean=new MappingsDBBean();
		Attribute a=new Attribute();
		a.setAttributeID(aid);
		a.setAttributeValue(avalue);
		MappingBean m=dbBean.findbyAttributeAndHash(a,hash);
		dbBean.destroy();
		if(m==null){
			return null;
		}
		else{
			return m;
		}
	}
	
	public FullInfoBean getMappingbyID(int aid){
		MappingsDBBean dbBean=new MappingsDBBean();
		FullInfoBean m = new FullInfoBean();
		m=dbBean.findbyID(aid);
		dbBean.destroy();
		if(m==null){
			return null;
		}
		else{
			return m;
		}
	}
	
	public FullInfoBean getWhitebyID(int aid){
		MappingsDBBean dbBean=new MappingsDBBean();
		FullInfoBean m = new FullInfoBean();
		m=dbBean.findWhitebyID(aid);
		dbBean.destroy();
		if(m==null){
			return null;
		}
		else{
			return m;
		}
	}
	
	public VerifyDigestBean getVerifyInfobyID(int aid){
		MappingsDBBean dbBean=new MappingsDBBean();
		VerifyDigestBean m = new VerifyDigestBean();
		m=dbBean.findVerifyInfobyID(aid);
		dbBean.destroy();
		if(m==null){
			return null;
		}
		else{
			return m;
		}
	}
/*	
	public boolean ModifyMappingbyIDValueHash(MappingBean mb){//Ϊ����ʾ����
		MappingsDBBean dbBean=new MappingsDBBean();
		
		boolean b=dbBean.ModifybyAttributeAndHash(mb);
		dbBean.destroy();
		return b;
	}
*/	
	public boolean ModifyMappingbyID(int aid,FullInfoBean fb)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b=dbBean.ModifybyID(aid, fb);
		dbBean.destroy();
		return b;
	}
	
	public boolean ModifyWhitebyID(int aid,FullInfoBean fb,int userid)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b=dbBean.ModifyWhitelistbyID(aid, fb,userid);
		dbBean.destroy();
		return b;
	}
	
	public boolean ModifyTustedbyID(int aid)
	{
		MappingsDBBean dbBean=new MappingsDBBean();
		boolean b=dbBean.ModifyTrustedbyID(aid);
		dbBean.destroy();
		return b;
	}
	
	public boolean ModifyMappingbyIDValueHash(int aid, String avalue, String hash, MappingBean mb){//Ϊ����ʾ����
		MappingsDBBean dbBean=new MappingsDBBean();
		
		boolean b=dbBean.ModifybyAttributeAndHash(aid,avalue,hash,mb);
		dbBean.destroy();
		return b;
	}
	
	
	
/*	
	public ArrayList ListMappings(int start, int num){
		MappingsDBBean dbBean=new MappingsDBBean();
//////		ArrayList c=dbBean.findLimit(start, num);
		
		dbBean.destroy();
		
///		return c;
	}
	*/
public ArrayList ListVerifyFailMappings(String sname, String fname,int trusted, String clientIP){
		
		ArrayList fullmappingc=new ArrayList();
		MappingsDBBean dbBean=new MappingsDBBean();
		fullmappingc = dbBean.findFailbyName(sname, fname, trusted,clientIP);
		dbBean.destroy();
		return fullmappingc;
	}

public ArrayList ListWhite(String sname,String sdigest,int userid,String description,String time,String type){
	
//	zhyjun
		MappingsDBBean dbBean=new MappingsDBBean();

		ArrayList mappingc=dbBean.findwhitebyAttAndSoftware( sname, sdigest,userid,description,time,type);
		dbBean.destroy();
		
	return mappingc;
	
	 
	
	

}
	
	public ArrayList ListMappings(int classID, String sname,  int trusted, String softwareName){
		
//		ArrayList fullmappingc=new ArrayList();
		
		
//		//����һ�������ҳ�AID�ļ���
//		ArrayList attc=ListAttIDsbyName(aname);
//		
//		
//		
//		//Ȼ����AID��AValue����һ�������ҡ�
//		//Ϊ����ʾ�����㣬Ӧ�ý���һ���µ���ݽṹ����IDtoName��Mappingһ������档	
//		Iterator it = attc.iterator(); 
//		while(it.hasNext()){		
//			Object obj=it.next();
//			AttIDtoName a=(AttIDtoName)obj;
			MappingsDBBean dbBean=new MappingsDBBean();
//			ArrayList mappingc=dbBean.findbyAttAndSoftware(a.getAttributeID(), avalue, sname, classID,sedition);
			ArrayList mappingc=dbBean.findbyAttAndSoftware( trusted, sname, classID,softwareName);
			dbBean.destroy();
			
//			Iterator it1 = mappingc.iterator();
//			while(it1.hasNext()){
//				Object obj1=it1.next();
////				FullInfoBean mb =(FullInfoBean)obj1;
////				FullMappingBean fmb=new FullMappingBean();
////				fmb.setAttidtoname(a);
////				fmb.setMb(mb);
//				fullmappingc.add(obj1);
//			}
//			
//		}
//		
//		return fullmappingc;
			
	
		return mappingc;
		
		 
		
		
	
	}
	
public HashMap ListDate(int classID, String sname,  int trusted, String softwareName,int userid){
		
//		ArrayList fullmappingc=new ArrayList();
		
		
//		//����һ�������ҳ�AID�ļ���
//		ArrayList attc=ListAttIDsbyName(aname);
//		
//		
//		
//		//Ȼ����AID��AValue����һ�������ҡ�
//		//Ϊ����ʾ�����㣬Ӧ�ý���һ���µ���ݽṹ����IDtoName��Mappingһ������档	
//		Iterator it = attc.iterator(); 
//		while(it.hasNext()){		
//			Object obj=it.next();
//			AttIDtoName a=(AttIDtoName)obj;
			MappingsDBBean dbBean=new MappingsDBBean();
//			ArrayList mappingc=dbBean.findbyAttAndSoftware(a.getAttributeID(), avalue, sname, classID,sedition);
			HashMap mappingc=dbBean.findDate( trusted, sname, classID,softwareName,userid);
			dbBean.destroy();
			
//			Iterator it1 = mappingc.iterator();
//			while(it1.hasNext()){
//				Object obj1=it1.next();
////				FullInfoBean mb =(FullInfoBean)obj1;
////				FullMappingBean fmb=new FullMappingBean();
////				fmb.setAttidtoname(a);
////				fmb.setMb(mb);
//				fullmappingc.add(obj1);
//			}
//			
//		}
//		
//		return fullmappingc;
			
	
		return mappingc;
		
		 
		
		
	
	}
	
public ArrayList ListHistory(int classID, String sname,  int trusted, String softwareName,int userid,String sdate){
		
//		ArrayList fullmappingc=new ArrayList();
		
		
//		//����һ�������ҳ�AID�ļ���
//		ArrayList attc=ListAttIDsbyName(aname);
//		
//		
//		
//		//Ȼ����AID��AValue����һ�������ҡ�
//		//Ϊ����ʾ�����㣬Ӧ�ý���һ���µ���ݽṹ����IDtoName��Mappingһ������档	
//		Iterator it = attc.iterator(); 
//		while(it.hasNext()){		
//			Object obj=it.next();
//			AttIDtoName a=(AttIDtoName)obj;
			MappingsDBBean dbBean=new MappingsDBBean();
//			ArrayList mappingc=dbBean.findbyAttAndSoftware(a.getAttributeID(), avalue, sname, classID,sedition);
			ArrayList mappingc=dbBean.findHistory( trusted, sname, classID,softwareName,userid,sdate);
			dbBean.destroy();
			
//			Iterator it1 = mappingc.iterator();
//			while(it1.hasNext()){
//				Object obj1=it1.next();
////				FullInfoBean mb =(FullInfoBean)obj1;
////				FullMappingBean fmb=new FullMappingBean();
////				fmb.setAttidtoname(a);
////				fmb.setMb(mb);
//				fullmappingc.add(obj1);
//			}
//			
//		}
//		
//		return fullmappingc;
	
		return mappingc;
	
	}
	

	
	public ArrayList ImportMappings(String fileName,int createID){//�����ļ�����������ݿ⣬���������ݿⲻ�ɹ��������б�
		
		ArrayList c=XMLHandler.parserXml(fileName);
		Iterator it = c.iterator();
		FullInfoBean mb,tmp;
		MappingsDBBean dbBean=new MappingsDBBean();
		ArrayList res=new ArrayList();
		StringBuffer mes = new StringBuffer();
		while(it.hasNext()){
			Object obj=it.next();
			mb=(FullInfoBean)obj;
			if(mb.getAttributeID()==-1||mb.getClassID()==-1||mb.getTrusted()==-1)
				res.add(mb.getFileName()+"  "+mb.getDigest()+"  ");
			else
			{
				int i = getIDByNameHash(mb.getFileName(),mb.getDigest());
				if (i==0)
				{
					mb.setCreateID(createID);
					if(!(dbBean.add(mb)))
						res.add(mb.getFileName()+"  "+mb.getDigest()+"  ");
				}
				else
				{
					if(!dbBean.ModifybyID(i, mb))
						res.add(mb.getFileName()+"  "+mb.getDigest()+"  ");
				}
			}
					
		}
		
		dbBean.destroy();
		return res;
		
	}
	
	public void ExportMappings(String fileName,ArrayList c){//��ɵ�����ļ�����
		
		XMLHandler.createXml(fileName, c);//���XML��������ΪfileName��
	}
	
	/*
	public boolean nameIsExisted(Attribute att){
		MappingsDBBean dbBean=new MappingsDBBean();
		MappingBean a=dbBean.findbyAttribute(att);
		if(a==null){//������
			dbBean.destroy();
			return false;
		}else{//����
			dbBean.destroy();
			return true;
		}
	}*/
	
	
	
	//added by lh
	public boolean AttIDIsExisted(int aid){
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		AttIDtoName a=dbBean.findbyAttID(aid);
		if(a==null){//������
			dbBean.destroy();
			return false;
		}else{//����
			dbBean.destroy();
			return true;
		}
	}
	
	public boolean AttNameIsExisted(String aname){
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		AttIDtoName a=dbBean.findbyAttName(aname);
		if(a==null){//������
			dbBean.destroy();
			return false;
		}else{//����
			dbBean.destroy();
			return true;
		}
	}
	
	public int AddAttID(AttIDtoName a){
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		if(AttIDIsExisted(a.getAttributeID())){//������ID�Ѿ�����,����
			dbBean.destroy();
			return 1;
		}
		if(dbBean.add(a)){//�ɹ����
			dbBean.destroy();
			return 0;
		}
		else{//δ�ɹ���ӣ�������ݿ�������
			dbBean.destroy();
			return 2;
		}
	}
	
	public boolean DeleteAttID(int aid){
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		boolean b=dbBean.deletebyAttID(aid);
		dbBean.destroy();
		return b;
	}
	
	public ArrayList ListAttIDs(int aid, String aname, String aclass){
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		ArrayList c=dbBean.findbyAttIDNameClass(aid, aname, aclass);
		
		dbBean.destroy();
		
		return c;  
	}
	
	public ArrayList ListAttIDsbyName(String aname){
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		ArrayList c=dbBean.findAttsbyAttName( aname);
		
		dbBean.destroy();
		
		return c;
	}
	
	
	public boolean modifyAttID(AttIDtoName a){
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		boolean b=dbBean.updatebyAttID(a);
		dbBean.destroy();
		return b;
	}
	
	public AttIDtoName getAtt(int aid){//Ϊ����ʾ����
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		AttIDtoName a=dbBean.findbyAttID(aid);
		dbBean.destroy();
		if(a==null){
			return null;
		}
		else{
			return a;
		}
	}
	public AttIDtoName getAttbyName(String aname){//Ϊ����ʾ����
		AttIDtoNameDBBean dbBean=new AttIDtoNameDBBean();
		AttIDtoName a=dbBean.findbyAttName(aname);
		dbBean.destroy();
		if(a==null){
			return null;
		}
		else{
			return a;
		}
	}
	
	public int getClassIDByName(String cname)
	{
		MappingsDBBean dbBean = new MappingsDBBean();
		int i = dbBean.getclassID(cname);
		dbBean.destroy();
		return i;
	}
	
	public int deleteVerifyLog(String id,String date){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.deleteVerifyLog(id,date);
	}
	
	public int addFactory(String name,String fid){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.addFactory(name,fid);
	}
	
	public int updateFactory(String id,String name){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.updateFactory(id,name);
	}
	
	public int deleteFactory(String id){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.deleteFactory(id);
	}
	
	public int addSystem(String name,String fid){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.addSystem(name,fid);
	}
	
	public int deleteSystem(String id){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.deleteSystem(id);
	}
	
	public int updateSystem(String id,String name){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.updateSystem(id,name);
	}
	
	public int addTechnology(String name,String fid){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.addTechnology(name,fid);
	}
	
	public int deleteTechnology(String id){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.deleteTechnology(id);
	}
	
	public int updateTechnology(String id,String name){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.updateTechnology(id,name);
	}
	
	public ArrayList getUsbInfo(UsbBean bean){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.getUsbInfo(bean);
	}
	
	public ArrayList getUserList(){
		MappingsDBBean dbBean=new MappingsDBBean();		
		return dbBean.getUserList();
	}
	
	
	//end
	
	
}
