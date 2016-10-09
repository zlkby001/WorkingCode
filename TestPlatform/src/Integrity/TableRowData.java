package Integrity;

import java.util.Vector;

//TableRowsData denotes the all the table rows, 
//	and rowsVector stores the rows vector 
//TableRowData denotes only one table row
//	and rowVector stores all fields in a row.
public class TableRowData 
{
	protected Vector rowVector = new Vector();
	protected String stdMeasurement = "";
	protected String status;
	public TableRowData()
	{
		status = "";
	}
	
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String st)
	{
		status = st;
	}
	public String getStdMeasurement()
	{
		return stdMeasurement;
	}
	public void setStdMeasurement(String std)
	{
		stdMeasurement = std;
	}
	
	public void composeTableRow()
	{		
	}
	
	public Vector getRowVector()
	{
		return rowVector;
	}
	public void clearRowVector()
	{
		rowVector.clear();
	}
}