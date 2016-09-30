package cn.ac.sklois.imm.admin;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.FileAppender;


public class Logtest {
    static public Logger logger = Logger.getLogger(Logtest.class.getName());
    public Logtest()
    {
    	//System.out.println(System.getProperty("user.dir"));
    	//System.out.println(Class.class.getClass().getResource("/").getPath());
    	PropertyConfigurator.configure("log4j.properties");
    }
    public static void main(String arg[])
    {
    	Logtest log = new Logtest();
    	log.logger.info("test");
    }
}
