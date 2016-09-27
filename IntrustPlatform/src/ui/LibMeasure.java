package ui;
//import testlibmd.libmd;

import com.sun.jna.Library;
import com.sun.jna.Native;
//import com.sun.jna.Platform;
import com.sun.jna.ptr.IntByReference;

public class LibMeasure {
	public static int minitor=0;//（用于判断是否正在监控内核）1 for start measure, 0 for end mesure
	public static int whitemode=0;//用于判断白名单模式是否开启
	public static int autoshow=0;//用于判断是否在完整性监控界面自动定期显示度量日志在list中
	private static libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
	//static{
	//	System.load("D:\\TrustTerminal_New\\MeasureLib\\libmd\\Debug\\libmd.dll");
	//}
	public interface libmd extends Library{
		//public int fnLibmd();
		//public int add(int x, int y);
		public int getMeasureVaule();
		public int setMode(int mode);
		public int setWhiteList(String whitefile,IntByReference count);
		public int clearWhiteList();
		public int getErrorList(String efile,IntByReference count);
		public int startRepair();
		public int getMode();
		public int clearMeausreLog();
		public int getCurrentProcess();
	}
	
	public int TM_startListenMeasureValue() throws InterruptedException
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 1;
		while(this.minitor==1)
		{
			Thread.sleep(5000);
			lm.getMeasureVaule();
		}
		if(this.minitor==0)
			return 2;
		return 0;
	}
	
	public int TM_openListen()
	{
		this.minitor=1;
		return 0;
	}
	
	public int TM_closeListen()
	{
		this.minitor=0;
		return 0;
	}
	
	public int TM_startWhiteMode()
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.setMode(1);
	}
	
	public int TM_startNormalMode()
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.setMode(0);
	}
	
	public int TM_setWhiteList(String whitefile,IntByReference count)
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.setWhiteList(whitefile, count);
	}
	
	public int TM_clearWhiteList()
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.clearWhiteList();
	}
	
	public int TM_getErrorList(String efile,IntByReference count)
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.getErrorList(efile, count);
	}
	
	public int TM_startRepair()
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.startRepair();
	}
	
	public int TM_getCurrentMode()
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.getMode();
	}
	
	public int TM_clearMLog()
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.clearMeausreLog();
	}
	
	public int TM_getCurrentProcess()
	{
		//libmd lm=(libmd)Native.loadLibrary("libmd",libmd.class);
		if(lm==null)
			return 101;
		return lm.getCurrentProcess();
	}

}
