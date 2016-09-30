package com.ics.util;

import java.io.InputStream;
import java.util.Properties;

import com.ics.dao.impl.AlertDaoImpl;

public class syslog {
	public static String syslogIP;
	public static int syslogPort;
	public static String syslogOpen;
	public static int minCycle; //(microsecond)
	/*
	public syslog(){
		Properties p = new Properties();
		InputStream in = AlertDaoImpl.class.getClassLoader().getResourceAsStream("../../config.properties");
		//String path=AlertDaoImpl.class.getClassLoader().getResource("../../config.properties").getPath();
		try{
			//p.load(new FileReader(path));
			p.load(in);
		}catch(Exception e){
			e.printStackTrace();
        }
		syslogIP=p.getProperty("syslogIP");
		syslogPort=p.getProperty("syslogPort");
		syslogOpen=p.getProperty("syslogOpen");
	}
	*/

}
