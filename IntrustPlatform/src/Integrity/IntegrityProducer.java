package Integrity;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import ui.ErrorListBean;
import ui.ProcessInfoBean;

//orginal measurement ==> XML doc ==> PlatformMeasureBean vector  
public class IntegrityProducer {
	private static String pfXmlFileName = "C://TCC//platform.xml";//"C://platform.xml";
	private static String pfMeasureFileName = "C://TCC//measurevalue";//"C://windows//measurevalue"; // 度量日志
	private static String pfActiveFileName = "C://TCC//process.log";//"C://windows//process.log"; // 活动进程
	// private static String pfMeasureFileName = "file400";
	private static String pfWhiteListFileName = "C://TCC//wl";//"C://wl"; // 白名单
	
	private static String pfFailLogFileName = "C://TCC//result0";//"C://result0"; // 验证失败记录
	
	private Vector pmVector = null; // PlatformMeasureBean Vector
	private Vector wlVector = null; // WhiteList Vector
	private Vector flVector = null;	// FailLog Vector
	// XML Variables
	private Document doc = null;
	private Set processIDSet = null;

	public IntegrityProducer() {
		pmVector = new Vector();
		processIDSet = new HashSet();
	}

	// Get/Set Serail function
	
	public static String getPfFailLogFileName() {
		return pfFailLogFileName;
	}

	public static void setPfFailLogFileName(String pfFailLogFileName) {
		IntegrityProducer.pfFailLogFileName = pfFailLogFileName;
	}

	public static String getPfWhiteListFileName() {
		return pfWhiteListFileName;
	}

	public static void setPfWhiteListFileName(String pfWhiteListFileName) {
		IntegrityProducer.pfWhiteListFileName = pfWhiteListFileName;
	}

	public static String getPFXMLFileName() {
		return pfXmlFileName;
	}

	public static String getPFMeasureFileName() {
		return pfMeasureFileName;
	}

	public static String getPFActiveFileName() {
		return pfActiveFileName;
	}

	public static void setPFXMLFileName(String fileName) {
		pfXmlFileName = fileName;
	}

	public static void setPFMeasureFileName(String fileName) {
		pfMeasureFileName = fileName;
	}

	public Vector getPlatformMeasureVector() {
		return this.pmVector;
	}

	private Vector generateLog(String strFile, int index, String component) {
		Vector fileVector = new Vector();
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);

			String s = null;
			Set MeasurementSet = new HashSet();
			processIDSet.clear();
			while ((s = br.readLine()) != null) {
				int p1 = s.indexOf(" ");
				int p2 = s.indexOf(" ", p1 + 1);
				int p0 = s.indexOf(" ", p2 + 1);
				int p5 = s.lastIndexOf(" ");
				int p4 = s.lastIndexOf(" ", p5 - 1);
				int p3 = s.lastIndexOf(" ", p4 - 1);

				String unique = s.substring(p1 + 1, p4);
				if (!MeasurementSet.contains(unique)) {
					MeasurementSet.add(unique);
					String measure = s.substring(p1 + 1, p2).toUpperCase();
					String program = s.substring(p2 + 1, p3);
					String software = s.substring(p3 + 1, p4);
					String type = s.substring(p4 + 1, p5);
					int typeID = 0;
					if (type.equals("OTHER"))
						typeID = 1;
					int processID = Integer.parseInt(s.substring(p5 + 1));
					PlatformMeasureBean pmb = new PlatformMeasureBean();
					pmb.setProgramName(program);
					pmb.setMeasureHexValue(measure);
					pmb.setMeasureValue(EncodeUtil.hexDecode(measure));
					pmb.setProcessID(processID);
					pmb.setSoftwareName(software);
					pmb.setType(typeID);

					if (typeID == 1) {
						if (index == 0)
							processIDSet.add(processID);
						else if (index == 1)
							if (software.equalsIgnoreCase(component))
								processIDSet.add(processID);
					}
					fileVector.addElement(pmb);
				}
			}

		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			System.out.println("文件未找到：" + mfile);
		} catch (IOException ioe) {
			// Debug.println("Measurement file read error");
			// ioe.printStackTrace();
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
		return fileVector;

	}

	private void getMeasurementLog(String strFile) {
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			// br = new BufferedReader(fr);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					strFile), "GBK")); // by zhyjun
			pmVector.clear(); // remove all elements
			Vector tempVector = new Vector(); // restore all elements
			String s = null;
			Set processIDSet = new HashSet();
			Set MeasurementSet = new HashSet();
			while ((s = br.readLine()) != null) {
				int p1 = s.indexOf(" ");
				int p2 = s.indexOf(" ", p1 + 1);
				// int p0 = s.indexOf(" ", p2+1);
				// int p3 = s.indexOf(" ", p2+1);
				// int p4 = s.indexOf(" ", p3+1);
				// int p5 = s.indexOf(" ", p4+1);
				int p3 = s.indexOf(" ", p2+1);
				int p4 = s.indexOf(" ", p3+1);
				int p5 = s.indexOf(" ", p4+1);
				int p6 = s.indexOf(" ", p5+1);
				//int p5 = s.lastIndexOf(" ");
				//int p4 = s.lastIndexOf(" ", p5 - 1);
				//int p3 = s.lastIndexOf(" ", p4 - 1);

				String unique = s.substring(p1 + 1, p4);
				if (!MeasurementSet.contains(unique)) {
					MeasurementSet.add(unique);
					String measure = s.substring(p1 + 1, p2).toUpperCase();
					String program = s.substring(p2 + 1, p3);
					String software = s.substring(p3 + 1, p4);
					String type = s.substring(p4 + 1, p5);
					int typeID = 0;
					if (type.equals("OTHER")) // other鍗硉ype==1 璇存槑鏄繘绋嬩富exe妯″潡
												// softwarename鏄繘绋嬪悕
						typeID = 1;
					int processID = Integer.parseInt(s.substring(p5 + 1, p6));
					PlatformMeasureBean pmb = new PlatformMeasureBean();
					pmb.setProgramName(program);
					pmb.setMeasureHexValue(measure);
					pmb.setMeasureValue(EncodeUtil.hexDecode(measure));
					pmb.setProcessID(processID);
					pmb.setSoftwareName(software); // 所属进程名
					pmb.setType(typeID);
					if (typeID == 1)
						processIDSet.add(processID);
					tempVector.addElement(pmb);
				}
			}
			for (int i = 0; i < tempVector.size(); i++) {
				Set softwareSet = new HashSet();
				PlatformMeasureBean pmb = (PlatformMeasureBean) tempVector
						.get(i);
				if (!processIDSet.contains(pmb.getProcessID())) {
					pmb.setSoftwareName("BasicData");
					pmVector.add(0, pmb); // 2014.4.15,edited by jiang
				} else {
					for (int j = 0; j < tempVector.size(); j++) {
						PlatformMeasureBean temp = (PlatformMeasureBean) tempVector
								.get(j);
						if ((pmb.getProcessID() == temp.getProcessID())
								&& (temp.getType() == 1))
							softwareSet.add(temp.getSoftwareName());
					}

					Iterator ir = softwareSet.iterator();
					while (ir.hasNext()) {
						String softwarename = (String) ir.next();
						PlatformMeasureBean pmb1 = new PlatformMeasureBean();
						pmb1.setProgramName(pmb.getProgramName());
						pmb1.setMeasureHexValue(pmb.getMeasureHexValue());
						pmb1.setMeasureValue(EncodeUtil.hexDecode(pmb
								.getMeasureHexValue()));
						pmb1.setProcessID(pmb.getProcessID());
						pmb1.setSoftwareName(softwarename);
						pmb1.setType(pmb.getType());

						pmVector.add(0, pmb1); // 2014.4.15,edited by jiang

					}
				}

			}
			br.close();
			br = null;
			fr.close();
			fr = null;
		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			System.out.println("文件未找到：" + mfile);
		} catch (IOException ioe) {
			// Debug.println("Measurement file read error");
			// ioe.printStackTrace();
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
	}

	// private void getMeasurementLog(String strFile)
	// {
	// pmVector.clear();
	// pmVector = generateLog(strFile,0,"ALL");
	// for(int i = 0; i < pmVector.size(); i++)
	// {
	// PlatformMeasureBean pmb = (PlatformMeasureBean)pmVector.get(i);
	// if(!processIDSet.contains(pmb.getProcessID()))
	// pmb.setSoftwareName("鍩虹鏁版嵁");
	//		
	// }
	// }
	// private void getMeasurementLog(String strFile, String component)
	// {
	// pmVector.clear();
	// Vector tempVector = new Vector();
	// tempVector = generateLog(strFile,1,component);
	// Iterator ir=processIDSet.iterator();
	// while(ir.hasNext())
	// {
	// int processID = (Integer)ir.next();
	// for(int j=0; j<tempVector.size(); j++)
	// {
	// PlatformMeasureBean temp = (PlatformMeasureBean) tempVector.get(j);
	// if(processID ==temp.getProcessID())
	// pmVector.addElement(temp);
	// }
	//		  
	// }
	// }

	private void getActiveLog(String strFile) {
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			// br = new BufferedReader(fr);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					strFile), "GBK")); // by zhyjun
			pmVector.clear(); // remove all elements
			Vector tempVector = new Vector(); // restore all elements
			// Vector componentVector = new Vector(); //restore component
			// measurements
			String s = null;
			Set MeasurementSet = new HashSet();
			Set processIDSet = new HashSet();
			while ((s = br.readLine()) != null) {
				int p1 = s.indexOf(" ");
				int p2 = s.indexOf(" ", p1 + 1);

				// int p3 = s.indexOf(" ", p2+1);
				// int p4 = s.indexOf(" ", p3+1);
				// int p5 = s.indexOf(" ", p4+1);
				// int p5 = s.lastIndexOf(" ");
				// int p4 = s.lastIndexOf(" ", p5-1);
				// int p3 = s.lastIndexOf(" ",p4-1);

				String unique = s.substring(p1 + 1);
				if (!MeasurementSet.contains(unique)) {
					MeasurementSet.add(unique);
					String measure = s.substring(p2 + 1).toUpperCase();
					String program = s.substring(p1 + 1, p2);
					// String software = s.substring(p3+1,p4);
					// String type = s.substring(p4+1,p5);
					String software = program;
					String type = "READ";
					int typeID = 0;
					if (type.equals("OTHER"))
						typeID = 1;
					// int processID = Integer.parseInt(s.substring(p5+1));
					int processID = Integer.parseInt(s.substring(0, p1));
					PlatformMeasureBean pmb = new PlatformMeasureBean();
					pmb.setProgramName(program);
					pmb.setMeasureHexValue(measure);
					pmb.setMeasureValue(EncodeUtil.hexDecode(measure));
					pmb.setProcessID(processID);
					pmb.setSoftwareName(software);
					pmb.setType(typeID);
					// pmVector.addElement(pmb);
					if (typeID == 1)
						processIDSet.add(processID);
					tempVector.addElement(pmb);
				}
			}
			for (int i = 0; i < tempVector.size(); i++) {
				Set softwareSet = new HashSet();
				PlatformMeasureBean pmb = (PlatformMeasureBean) tempVector
						.get(i);
				if (!processIDSet.contains(pmb.getProcessID())) {
					// pmb.setSoftwareName("BasicData");
					pmVector.add(0, pmb); // 2014.4.15,edited by jiang
				} else {
					for (int j = 0; j < tempVector.size(); j++) {
						PlatformMeasureBean temp = (PlatformMeasureBean) tempVector
								.get(j);
						if ((pmb.getProcessID() == temp.getProcessID())
								&& (temp.getType() == 1))
							softwareSet.add(temp.getSoftwareName());
					}

					Iterator ir = softwareSet.iterator();
					while (ir.hasNext()) {
						String softwarename = (String) ir.next();
						PlatformMeasureBean pmb1 = new PlatformMeasureBean();
						pmb1.setProgramName(pmb.getProgramName());
						pmb1.setMeasureHexValue(pmb.getMeasureHexValue());
						pmb1.setMeasureValue(EncodeUtil.hexDecode(pmb
								.getMeasureHexValue()));
						pmb1.setProcessID(pmb.getProcessID());
						pmb1.setSoftwareName(softwarename);
						pmb1.setType(pmb.getType());

						pmVector.add(0, pmb1); // 2014.4.15,edited by jiang

					}
				}

			}
			br.close();
			br = null;
			fr.close();
			fr = null;
		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			System.out.println("文件未找到：" + mfile);
		} catch (IOException ioe) {
			// Debug.println("Measurement file read error");
			// ioe.printStackTrace();
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
	}
	
	
	//add by yangbo
	private void getAlertLog(ArrayList<ErrorListBean> alertlog) {
		
			pmVector.clear(); // remove all elements
			//Vector tempVector = new Vector(); // restore all elements
			// Vector componentVector = new Vector(); //restore component
			// measurements
			//String s = null;
			//Set MeasurementSet = new HashSet();
			//Set processIDSet = new HashSet();
			for(int i=0; i<alertlog.size(); i++){
				
				int flag =0;
				for(int j=0; j<i; j++){					
					if(alertlog.get(i).getMeasureValue().equalsIgnoreCase(alertlog.get(j).getMeasureValue())){
						flag = 1;
						break;
					}				
				}
				if(flag==1){
					continue;
				}					
				
				PlatformMeasureBean pmb = new PlatformMeasureBean();
				pmb.setProgramName(alertlog.get(i).getProgramName());
				pmb.setMeasureHexValue(alertlog.get(i).getMeasureValue());
				pmb.setMeasureValue(EncodeUtil.hexDecode(alertlog.get(i).getMeasureValue()));
				pmb.setProcessID(0);
				pmb.setSoftwareName(alertlog.get(i).getProgramName());
				pmb.setType(1);
				pmVector.addElement(pmb);	
			
			}
			/*while ((s = br.readLine()) != null) {
				int p1 = s.indexOf(" ");
				int p2 = s.indexOf(" ", p1 + 1);

				// int p3 = s.indexOf(" ", p2+1);
				// int p4 = s.indexOf(" ", p3+1);
				// int p5 = s.indexOf(" ", p4+1);
				// int p5 = s.lastIndexOf(" ");
				// int p4 = s.lastIndexOf(" ", p5-1);
				// int p3 = s.lastIndexOf(" ",p4-1);

				String unique = s.substring(p1 + 1);
				if (!MeasurementSet.contains(unique)) {
					MeasurementSet.add(unique);
					String measure = s.substring(p2 + 1).toUpperCase();
					String program = s.substring(p1 + 1, p2);
					// String software = s.substring(p3+1,p4);
					// String type = s.substring(p4+1,p5);
					String software = program;
					String type = "READ";
					int typeID = 0;
					if (type.equals("OTHER"))
						typeID = 1;
					// int processID = Integer.parseInt(s.substring(p5+1));
					int processID = Integer.parseInt(s.substring(0, p1));
					PlatformMeasureBean pmb = new PlatformMeasureBean();
					pmb.setProgramName(program);
					pmb.setMeasureHexValue(measure);
					pmb.setMeasureValue(EncodeUtil.hexDecode(measure));
					pmb.setProcessID(processID);
					pmb.setSoftwareName(software);
					pmb.setType(typeID);
					// pmVector.addElement(pmb);
					if (typeID == 1)
						processIDSet.add(processID);
					tempVector.addElement(pmb);
				}
			}
			for (int i = 0; i < tempVector.size(); i++) {
				Set softwareSet = new HashSet();
				PlatformMeasureBean pmb = (PlatformMeasureBean) tempVector
						.get(i);
				if (!processIDSet.contains(pmb.getProcessID())) {
					// pmb.setSoftwareName("BasicData");
					pmVector.add(0, pmb); // 2014.4.15,edited by jiang
				} else {
					for (int j = 0; j < tempVector.size(); j++) {
						PlatformMeasureBean temp = (PlatformMeasureBean) tempVector
								.get(j);
						if ((pmb.getProcessID() == temp.getProcessID())
								&& (temp.getType() == 1))
							softwareSet.add(temp.getSoftwareName());
					}

					Iterator ir = softwareSet.iterator();
					while (ir.hasNext()) {
						String softwarename = (String) ir.next();
						PlatformMeasureBean pmb1 = new PlatformMeasureBean();
						pmb1.setProgramName(pmb.getProgramName());
						pmb1.setMeasureHexValue(pmb.getMeasureHexValue());
						pmb1.setMeasureValue(EncodeUtil.hexDecode(pmb
								.getMeasureHexValue()));
						pmb1.setProcessID(pmb.getProcessID());
						pmb1.setSoftwareName(softwarename);
						pmb1.setType(pmb.getType());

						pmVector.add(0, pmb1); // 2014.4.15,edited by jiang

					}
				}

			}
			br.close();
			br = null;
			fr.close();
			fr = null;
		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			System.out.println("文件未找到：" + mfile);
		} catch (IOException ioe) {
			// Debug.println("Measurement file read error");
			// ioe.printStackTrace();
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
		}*/
	}
	

	private void getMeasurementLog(String strFile, String component) {
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			// br = new BufferedReader(fr);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					strFile), "GBK")); // by zhyjun
			pmVector.clear(); // remove all elements
			Vector tempVector = new Vector(); // restore all elements
			// Vector componentVector = new Vector(); //restore component
			// measurements
			String s = null;
			Set MeasurementSet = new HashSet();
			Set processIDSet = new HashSet();
			while ((s = br.readLine()) != null) {
				int p1 = s.indexOf(" ");
				int p2 = s.indexOf(" ", p1 + 1);

				// int p3 = s.indexOf(" ", p2+1);
				// int p4 = s.indexOf(" ", p3+1);
				// int p5 = s.indexOf(" ", p4+1);
				int p5 = s.lastIndexOf(" ");
				int p4 = s.lastIndexOf(" ", p5 - 1);
				int p3 = s.lastIndexOf(" ", p4 - 1);

				String unique = s.substring(p1 + 1, p4);
				if (!MeasurementSet.contains(unique)) {
					MeasurementSet.add(unique);
					String measure = s.substring(p1 + 1, p2).toUpperCase();
					String program = s.substring(p2 + 1, p3);
					String software = s.substring(p3 + 1, p4);
					String type = s.substring(p4 + 1, p5);
					int typeID = 0;
					if (type.equals("OTHER"))
						typeID = 1;
					int processID = Integer.parseInt(s.substring(p5 + 1));
					PlatformMeasureBean pmb = new PlatformMeasureBean();
					pmb.setProgramName(program); // 进程名
					pmb.setMeasureHexValue(measure); // 哈希值
					pmb.setMeasureValue(EncodeUtil.hexDecode(measure)); // 哈希值解码
					pmb.setProcessID(processID); // 进程ID
					pmb.setSoftwareName(software); // 软件名
					pmb.setType(typeID); // 类型ID
					tempVector.addElement(pmb);
					if (typeID == 1 && software.equalsIgnoreCase(component))
						processIDSet.add(processID);

				}
			}
			// if(processIDSet.size()>1)
			// System.out.println("process exceeds 1"+component);
			Iterator ir = processIDSet.iterator();
			while (ir.hasNext()) {
				int processID = (Integer) ir.next();
				for (int j = 0; j < tempVector.size(); j++) {
					PlatformMeasureBean temp = (PlatformMeasureBean) tempVector
							.get(j);
					if (processID == temp.getProcessID()) {
						// temp.setSoftwareName(component);
						pmVector.add(0, temp); // 2014.4.15,edited by jiang
					}
				}

			}
			br.close();
			br = null;
			fr.close();
			fr = null;
		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			System.out.println("文件未找到：" + mfile);
		} catch (IOException ioe) {
			// Debug.println("Measurement file read error");
			// ioe.printStackTrace();
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
	}

	private void getWhiteListByName(String strFile) {
		Vector proVector = new Vector();
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);

			String s = null;
			// Set MeasurementSet = new HashSet();
			if(wlVector!=null)
				wlVector.clear();
			while ((s = br.readLine()) != null) { 
				int p1 = s.indexOf(" ");

				String value = s.substring(0, p1);
				String name = s.substring(p1 + 1, s.length());

				ProcessInfoBean prb = new ProcessInfoBean();
				prb.setMeasure(value);
				prb.setFileName(name);

				proVector.addElement(prb);
			}

		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			System.out.println("文件未找到：" + mfile);
		} catch (IOException ioe) {
			// Debug.println("Measurement file read error");
			// ioe.printStackTrace();
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
		wlVector = proVector;

	}
	
	private void getFailLogByName(String strFile) {
		Vector proVector = new Vector();
		File mfile = new File(strFile);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(mfile);
			br = new BufferedReader(fr);

			String s = null;
			// Set MeasurementSet = new HashSet();
			if(flVector!=null)
				flVector.clear();
			while ((s = br.readLine()) != null) {
				int p1 = s.indexOf("filename: ");
				int p2 = s.indexOf(" hash_value: ");
				int p3 = s.indexOf(" softwareName: ");

				String filename = s.substring(p1+10, p2);
				String hash_value = s.substring(p2 + 13, p3);
				String softwareName = s.substring(p3+15,s.length() - 1);

				PlatformMeasureBean prb = new PlatformMeasureBean();
				prb.setMeasureHexValue(hash_value);
				prb.setProgramName(filename);
				prb.setSoftwareName(softwareName);

				proVector.addElement(prb);
			}

		} catch (FileNotFoundException e) {
			// Debug.println("Measurement file not found");
			// e.printStackTrace();
			System.out.println("文件未找到：" + mfile);
		} catch (IOException ioe) {
			// Debug.println("Measurement file read error");
			// ioe.printStackTrace();
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
		flVector = proVector;

	}

	private boolean initDOM(String fileName) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
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

			public void fatalError(SAXParseException e) throws SAXException {
				System.err.println("FATAL: " + e.getMessage());
				throw e; // re-throw the error
			}
		});
		try {
			doc = db.parse(fileName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		} catch (SAXException se) {
			se.printStackTrace();
			return false;
		}

		return true;
	}

	// parse XML document to PlatformMeasureBean
	public void readXML() {
		if (!initDOM(getPFXMLFileName())) {
			System.out.println("Initalize XML Document Error!");
			return;
		}

		pmVector.clear(); // clear platform measure Vector
		Element root = doc.getDocumentElement();
		NodeList ProgramList = root.getElementsByTagName("Program");
		for (int i = 0; i < ProgramList.getLength(); i++) {
			PlatformMeasureBean pmb = new PlatformMeasureBean();
			Element program = (Element) ProgramList.item(i);

			// Program -- Name
			NodeList NameList = program.getElementsByTagName("Name");
			if (NameList.getLength() == 1) {
				Element name = (Element) NameList.item(0);
				Text nameNode = (Text) name.getFirstChild();
				String nv = EncodeUtil.Trim(nameNode.getNodeValue());
				pmb.setProgramName(nv);
			}

			// Program -- Measure
			NodeList MeasureList = program.getElementsByTagName("Measure");
			if (MeasureList.getLength() == 1) {
				Element measure = (Element) MeasureList.item(0);
				Text measureNode = (Text) measure.getFirstChild();
				String mhv = EncodeUtil.Trim(measureNode.getNodeValue());
				int len = mhv.length();
				pmb.setMeasureHexValue(mhv);
				byte[] mv = EncodeUtil.hexDecode(mhv);
				pmb.setMeasureValue(mv);
			}

			pmb.composeTableRow();
			pmVector.add(0, pmb); // 2014.4.15,edited by jiang
		}
	}

	private boolean newDOM() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		}
		doc = db.newDocument();

		return true;
	}

	private void createXML() {
		Element root = doc.createElement("PlatformMeasureLog");
		doc.appendChild(root);

		for (int i = 0; i < pmVector.size(); i++) {
			PlatformMeasureBean pmb = (PlatformMeasureBean) pmVector.get(i);
			// Program
			Element program = doc.createElement("Program");
			root.appendChild(program);
			// Program -- Software
			Element software = doc.createElement("Software");
			program.appendChild(software);
			Text softwarename = doc.createTextNode(pmb.getSoftwareName());
			software.appendChild(softwarename);
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
		try {
			osWriter = new OutputStreamWriter(new FileOutputStream(
					getPFXMLFileName()), "GBK");
			// osWriter = new OutputStreamWriter(new
			// FileOutputStream(getPFXMLFileName()));
			outWriter = new PrintWriter(osWriter);
			xmlWriter = new XMLDocumentWriter(outWriter);
			// set Indent on
			xmlWriter.setIndentOn(true);
			xmlWriter.write(doc);
			xmlWriter.close();
			outWriter.close();
			osWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// convert the orginal measurement into XML document
	public int generateFile(String filename, String component, ArrayList<ErrorListBean> alertlog) {
		setPFXMLFileName(filename);
		if (!newDOM()) {
			System.out.println("Initalize XML Document Error!");
			return -1;
		}
		if (component.equalsIgnoreCase("ALL"))
			getMeasurementLog(getPFMeasureFileName());
		// zhyjun
		else if (component.equalsIgnoreCase("active"))
			getActiveLog(getPFActiveFileName());
		// zhyjun
		// yangbo
		else if (component.equalsIgnoreCase("alertlog"))
			getAlertLog(alertlog);
		else
			getMeasurementLog(getPFMeasureFileName(), component);
		createXML();
		return 0;
	}

	public Vector getMeasurements() {
		getMeasurementLog(getPFMeasureFileName());
		return this.pmVector;

	}

	public Vector getMeasurements(String component) {
		if (!(component.equals("Active")))
			getMeasurementLog(getPFMeasureFileName(), component);
		else
			getActiveLog(getPFActiveFileName());
		return this.pmVector;

	}
	
	public Vector getWhiteList() {
		getWhiteListByName(getPfWhiteListFileName());
		return this.wlVector;

	}
	
	public Vector getFailLog() {
		getFailLogByName(getPfFailLogFileName());
		return this.flVector;

	}
}