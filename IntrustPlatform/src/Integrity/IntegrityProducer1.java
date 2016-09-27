package Integrity;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

//orginal measurement ==> XML doc ==> PlatformMeasureBean vector  
public class IntegrityProducer1 
{
	private static String pfXmlFileName = "platform.xml";
	//private static String pfMeasureFileName = "/mnt/imm/ascii_runtime_measurements";
	//private static String pfMeasureFileName = "/mnt/imm/ima/ascii_runtime_measurements";
	
	private static String pfMeasureFileName = "ascii_runtime_measurements";
	private Vector pmVector = null;	// PlatformMeasureBean Vector

	// XML Variables
	private Document doc = null;
	
	public IntegrityProducer1()
	{
		pmVector = new Vector();
	}

	// Get/Set Serail function
	public static String getPFXMLFileName()
	{
		return pfXmlFileName; 
	}
	public static String getPFMeasureFileName()
	{
		return pfMeasureFileName;
	}
	public static void setPFXMLFileName(String fileName)
	{
		pfXmlFileName = fileName;
	}
	public static void setPFMeasureFileName(String fileName)
	{
		pfMeasureFileName = fileName;
	}
	public Vector getPlatformMeasureVector()
	{
		return this.pmVector;
	}	
	
	private void getMeasurementLog(String strFile)
	{
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);
			pmVector.clear(); // remove all elements
			String s = null;
			while((s = br.readLine()) != null)
			{
				int p1 = s.indexOf(" ");
				int p2 = s.indexOf(" ", p1+1);
				int p3 = s.indexOf(" ", p2+1);
				String measure = s.substring(p1+1, p2).toUpperCase();
				String program = s.substring(p2+1, p3);
				PlatformMeasureBean pmb = new PlatformMeasureBean();
				pmb.setProgramName(program);
				pmb.setMeasureHexValue(measure);
				pmb.setMeasureValue(EncodeUtil.hexDecode(measure));
				pmVector.addElement(pmb);
			}
			br.close();
			br = null;
			fr.close();
			fr = null;
		}
		catch(FileNotFoundException e)
		{
//			Debug.println("Measurement file not found");
			//e.printStackTrace();
			System.out.println("找不到指定文件："+mfile);
		}
		catch(IOException ioe)
		{
//			Debug.println("Measurement file read error");
			//ioe.printStackTrace();	
			System.out.println("打开文件错误："+mfile);
		}
		finally
		{
			try
			{
				if(br != null)
				{
					br.close();
					br = null;
				}
				if(fr != null)
				{
					fr.close();
					fr = null;
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}		
	}
	
	private boolean initDOM(String fileName)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace();
			return false;
		}
		db.setErrorHandler(new ErrorHandler() {
			public void warning(SAXParseException e) {
			    System.err.println("WARNING: " + e.getMessage());
			}
			public void error(SAXParseException e) {
			    System.err.println("ERROR: " + e.getMessage());
			}
			public void fatalError(SAXParseException e)
			    throws SAXException {
			    System.err.println("FATAL: " + e.getMessage());
			    throw e;   // re-throw the error
			}		
		});
		try
		{
			doc = db.parse(fileName);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return false;
		}
		catch(SAXException se)
		{
			se.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	// parse XML document to PlatformMeasureBean
	public void readXML()
	{
		if(!initDOM(getPFXMLFileName()))
		{
			System.out.println("Initalize XML Document Error!");
			return;
		}
		
		pmVector.clear();	// clear platform measure Vector
		Element root = doc.getDocumentElement();
		NodeList ProgramList = root.getElementsByTagName("Program");
		for(int i = 0; i < ProgramList.getLength(); i++)
		{
			PlatformMeasureBean pmb = new PlatformMeasureBean();
			Element program = (Element)ProgramList.item(i);
			
			// Program -- Name
			NodeList NameList = program.getElementsByTagName("Name");
			if(NameList.getLength() == 1)
			{
				Element name = (Element)NameList.item(0);
				Text nameNode = (Text)name.getFirstChild();
				String nv = EncodeUtil.Trim(nameNode.getNodeValue());
				pmb.setProgramName(nv);
			}

			// Program -- Measure
			NodeList MeasureList = program.getElementsByTagName("Measure");
			if(MeasureList.getLength() == 1)
			{
				Element measure = (Element)MeasureList.item(0);
				Text measureNode = (Text)measure.getFirstChild();
				String mhv = EncodeUtil.Trim(measureNode.getNodeValue());
				int len = mhv.length();
				pmb.setMeasureHexValue(mhv);
				byte[] mv = EncodeUtil.hexDecode(mhv);
				pmb.setMeasureValue(mv);
			}

			pmb.composeTableRow();
			pmVector.addElement(pmb);
		}		
	}
	
	private boolean newDOM()
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace();
			return false;
		}
		doc = db.newDocument();
		
		return true;		
	}
	
	private void createXML()
	{
		Element root = doc.createElement("PlatformMeasureLog");
		doc.appendChild(root);
		
		for(int i = 0; i < pmVector.size(); i++)
		{
			PlatformMeasureBean pmb = (PlatformMeasureBean)pmVector.get(i);
			// Program
			Element program = doc.createElement("Program");
			root.appendChild(program);
			// Program -- Name
			Element name = doc.createElement("Name");
			program.appendChild(name);
			Text tname = doc.createTextNode(pmb.getProgramName());
			name.appendChild(tname);
			// Program -- MeasureValue
			Element measure = doc.createElement("Measure");
			program.appendChild(measure);
			Text tmeasure = doc.createTextNode(pmb.getMeasureHexValue());
			measure.appendChild(tmeasure);
		}
		// write to xml file
		OutputStreamWriter osWriter = null;
		PrintWriter outWriter = null;
		XMLDocumentWriter xmlWriter = null;
		try
		{
			osWriter = new OutputStreamWriter(new FileOutputStream(getPFXMLFileName()));
			outWriter = new PrintWriter(osWriter);
			xmlWriter = new XMLDocumentWriter(outWriter);
			// set Indent on
			xmlWriter.setIndentOn(true);
			xmlWriter.write(doc);
			xmlWriter.close();
			outWriter.close();
			osWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// convert the orginal measurement into XML document	
	public int generateFile(String filename)
	{
		setPFXMLFileName(filename);
		if(!newDOM())
		{
			System.out.println("Initalize XML Document Error!");
			return -1;
		}
		getMeasurementLog(getPFMeasureFileName());
		createXML();
		return 0;
	}
	
	public Vector getMeasurements(){
		getMeasurementLog(getPFMeasureFileName());
		return this.pmVector;
	
	}
}