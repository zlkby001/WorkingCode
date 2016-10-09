package ui;
import java.util.regex.*; 

public class TCCUtilit {
	
	static public boolean IPisCorrect(String ip)
	{//正则表达式判断IP正确性

	Pattern p=Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
	Matcher m = p.matcher(ip);
	boolean b = m.matches();

	return b;
	}
	

}
