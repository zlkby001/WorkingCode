package ui;

import org.wsoperate.RegistOperate;
import org.wsoperate.WSOperator;

public class TreeDownloaderProcess extends Thread {
	
	private boolean gettreeinfo()
	{
		WSOperator wso = new WSOperator();
    	if(wso.getWSStatus() == true) {
    		System.out.println("服务器连接成功");
    		RegistOperate ro = new RegistOperate();
    		String result = ro.getlistinfo();
    		if(result!=null)
    		{
    			if(!ro.store_tree(result))
    				return false;
    		}
    		else
    			return false;
    		
    	}
    	else {
    		System.out.println("服务器连接失败");  
    		return false;
    	}
		return true;
	}
	
	public void run(){
		if(gettreeinfo())
			System.out.println("download tree success!");
		else
			System.out.println("download tree failed!");
	}

}
