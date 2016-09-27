package Integrity;

import java.util.*;

public class PlatformMeasureBean extends TableRowData
{
	public static final int DIGEST_SIZE = 32;
	private String programName;
	private String measureHexValue;
	private byte[] measureValue = null;
	private int processID;
	private int type;
	private String softwareName;
	
	public String getSoftwareName() {
		return softwareName;
	}
	
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	
	public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public PlatformMeasureBean()
	{
		measureValue = new byte[DIGEST_SIZE];
	}
	
	public PlatformMeasureBean(String programName, String measureHexValue) {
		super();
		// TODO Auto-generated constructor stub
		this.programName = programName;
		setMeasureHexValue(measureHexValue);
		composeTableRow();
	}
	public PlatformMeasureBean(Vector v)
	{
		this.programName = (String)v.get(0);
		String m = (String)v.get(1);
		setMeasureHexValue(m);
		this.status = (String)v.get(2);
	}
	
	public String getProgramName()
	{
		return programName;
	}
	public String getMeasureHexValue()
	{
		return measureHexValue;
	}
	public byte[] getMasureValue()
	{
		return measureValue;
	}
	
	public void setProgramName(String name)
	{
		this.programName = name;
	}
	public void setMeasureHexValue(String value)
	{
		this.measureHexValue = value;
		if(value == "" || value.length() != 2*DIGEST_SIZE)
		{
	//		Debug.println("Measurement Hex Value fault");
			return;
		}
		
		try
		{
			this.measureValue = EncodeUtil.hexDecode(value);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
	}
	public void setMeasureValue(byte[] value)
	{
		System.arraycopy(value, 0, measureValue, 0, DIGEST_SIZE);
		this.measureHexValue = EncodeUtil.hexEncode(measureValue);
	}
	public void composeTableRow()
	{
		rowVector.clear();
		rowVector.addElement(programName);
		rowVector.addElement(measureHexValue);		
		rowVector.addElement(status);
	}	
}

