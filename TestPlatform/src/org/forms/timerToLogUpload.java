package org.forms;
import java.io.File;
import java.util.TimerTask;

public class timerToLogUpload extends TimerTask{  		
	
    @Override  
    public void run() {    	
    	File file=new File("C:/TCC/audit_log");   
    	if(file!=null){
    		audit.log_upload();
    	}else{
    		return;
    	}
    	
	}
}  