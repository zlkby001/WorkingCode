package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ServerIPaddress {
	private static String ip = "127.0.0.1";
	private static int port = 8080;	
	
	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		ServerIPaddress.ip = ip;
		setIpConfig(ip,port+"");
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		ServerIPaddress.port = port;
		setIpConfig(ip,port+"");
	}


	
	
	
	/**
	 * 更新配置文件中的IP和端口号
	 */
	public static void setIpConfig(String ip,String port)
	{
		String config = ip +":"+ port;
		File ServerInfo = new File("C:\\TCC\\ServerIP");
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(ServerInfo));
			output.write(config);
			output.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}	
	
	/**
	 * 从配置文件加载IP和端口号
	 */
	public static void getIpConfig()
	{
		String[] info = readServerIP().split(":");
		if(info.length>0){
			ip = info[0];
			port = Integer.parseInt(info[1]);
		}
	}	
	
	
	/**
	 * 从配置文件读取配置字符串
	 */
	private static String readServerIP() {
		String file = "C:\\TCC\\ServerIP";
		String s = "";
		File mfile = new File(file);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);
			s = br.readLine();
//			if (s != null) {
//				System.out.println(s);
//			}

		} catch (FileNotFoundException e) {
			
			String config = ip +":"+ port;
			return config;
			
			
			
			//System.out.println("文件未找到：" + mfile);
		} catch (IOException ioe) {
			System.out.println("文件读取错误：" + mfile);
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
				if (fr != null) {
					fr.close();
					fr = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return s;

	}
}
