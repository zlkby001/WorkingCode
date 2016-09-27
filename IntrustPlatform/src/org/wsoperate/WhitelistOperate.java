package org.wsoperate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.forms.XMLReader;

public class WhitelistOperate {
	private String pubkeyPath = "C:/TCC/PubKey";//"C:/WINDOWS/PubKey";	        	         	
    private File pubkeyfile = new File(pubkeyPath);
    private String TCM_PK;

	public WhitelistOperate() {
		// TODO Auto-generated constructor stub
	}
	
	public String getWlVersion() {
		try {
			BufferedReader pkreader = new BufferedReader(new FileReader(pubkeyfile));
			TCM_PK = pkreader.readLine();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//从服务器获取白名单版本信息
		XMLReader xmlreader = new XMLReader();
		String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("whitelistop");
		try {												    							    
			WhitelistServiceStub stub = new WhitelistServiceStub(null, trans_value);
			WhitelistServiceStub.QueryWhitelistinfo infofunction = new WhitelistServiceStub.QueryWhitelistinfo();							
			infofunction.setIn0(TCM_PK);
			String wlversionres = stub.queryWhitelistinfo(infofunction).getOut();	
			return wlversionres;
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return null;		
	}

	public boolean uploadWl(int wlversion, String whitelistcontent) {
		try {
			BufferedReader pkreader = new BufferedReader(new FileReader(pubkeyfile));
			TCM_PK = pkreader.readLine();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//向服务器上传白名单		
		XMLReader xmlreader = new XMLReader();
		String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("whitelistop");
		try {			
			
			WhitelistServiceStub stub = new WhitelistServiceStub(null, trans_value);
			WhitelistServiceStub.UploadWhitelist uploadfunction = new WhitelistServiceStub.UploadWhitelist();							
        	uploadfunction.setIn0(TCM_PK);
        	uploadfunction.setIn1(wlversion);
        	uploadfunction.setIn2(whitelistcontent);
        	boolean flag = stub.uploadWhitelist(uploadfunction).getOut();
			return flag;
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return false;		
	}
	
	public String downloadWl(int version) {
		try {
			BufferedReader pkreader = new BufferedReader(new FileReader(pubkeyfile));
			TCM_PK = pkreader.readLine();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		XMLReader xmlreader = new XMLReader();
	    String trans_value = xmlreader.searchXML("WSIP")+xmlreader.searchXML("whitelistop");
		try {
			WhitelistServiceStub stub = new WhitelistServiceStub(null, trans_value);
			WhitelistServiceStub.DownloadWhitelist downfunction = new WhitelistServiceStub.DownloadWhitelist();	
		    downfunction.setIn0(TCM_PK);
		    downfunction.setIn1(version);
		    String result = stub.downloadWhitelist(downfunction).getOut();
		    return result;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
}
