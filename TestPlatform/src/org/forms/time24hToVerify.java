package org.forms;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


import ui.AuthVerify;

public class time24hToVerify extends TimerTask{  
	
	public MessageBox mb;
	public Shell shell;
	private long dayLeft = 0;
	
	public time24hToVerify(Shell temp){
		shell = temp;		
		mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);  
	}
	
    @Override  
    public void run() {    	
    	
    	dayLeft = verify();
    	
    	if(dayLeft>0 || dayLeft==0 ){    	
	    	shell.getDisplay().asyncExec(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Date date = new Date(this.scheduledExecutionTime()); 				
					mb.setText("警告");
		            mb.setMessage("当前客户端仍为试用版本，试用期剩余"+dayLeft+"天，请尽快获取合法授权！" );
		            mb.open();
				}    		 
	    	 });
    	}
    	
    	if(dayLeft==-3){    	
	    	shell.getDisplay().asyncExec(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Date date = new Date(this.scheduledExecutionTime()); 				
					mb.setText("警告");
		            mb.setMessage("软件试用已过期，请获取授权后再使用软件！" );
		            mb.open();
				}    		 
	    	 });
    	}       
    } 
    
    private long verify(){
		
		int type = -100;		//授权类型
		long cmp = -1;			//剩余天数		
		
		AuthVerify av=new AuthVerify();
		int ret=av.Verify();
		//System.out.println("verify ret value:"+ret);
		
		if(ret==-1||ret==0||ret==-3)
			type = ret;
		else {	
			//System.out.println("AA");System.out.println(ret);
			type=2;	
			String strDate;
			if(ret<900000000){
				//System.out.println("BB");
				strDate = (ret+"").substring(1, 9);
			}
			else
			{//System.out.println("CC");
				return -1;
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date lastDate = null;
			try {
				lastDate = formatter.parse(strDate);		//到期时间
			} catch (ParseException e) {
				e.printStackTrace();
				type = -100;
			}
			Date currentDate = new Date();	//当前时间
			cmp = (lastDate.getTime() - currentDate.getTime())/(24*60*60*1000) + 1;
			
		}
		
		if(type==2){
			return cmp;
		}else{
			if(type==-3)
				return type;
			else
				return -1;
		}
	}
}  
