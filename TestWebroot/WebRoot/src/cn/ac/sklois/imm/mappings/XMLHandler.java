package cn.ac.sklois.imm.mappings;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;   
import org.jdom.Element;   
import org.jdom.JDOMException;   
import org.jdom.input.SAXBuilder;   
import org.jdom.output.XMLOutputter;   

import cn.ac.sklois.imm.admin.Logtest;



public class XMLHandler {

	public static void createXml(String fileName,ArrayList c) { //根据c写一个Xml文件，名字为fileName  
		Document document;   
		Element  root;   
		root=new Element("mappings");   
		document=new Document(root);
		
		
		FullInfoBean m=null;
		Iterator it = c.iterator();
		
		while(it.hasNext()){
			Object obj=it.next();
			m=(FullInfoBean)obj;
				   
			Element mapping=new Element("mapping");
					
			//Attribute
			Element attribute=new Element("attribute");
			//
			Element attributeName=new Element("attribute-name"); 			
			attributeName.setText(m.getAttributeName());   
			attribute.addContent(attributeName);
			
			Element className = new Element("class-name");
			className.setText(m.getClassName());
			attribute.addContent(className);
			//
			Element trusted=new Element("trusted");
			if(m.getTrusted()==1)
				trusted.setText("可信");
			else
				trusted.setText("不可信");
			
			attribute.addContent(trusted);
			//
			mapping.addContent(attribute);  
			
			
			
			//MeasurementValues------SoftwareInfo
			Element softwareInfo=new Element("software-info");
			//
			Element softwareName=new Element("software-name");
			softwareName.setText(m.getSoftwareName());
			softwareInfo.addContent(softwareName);
			
			Element digest=new Element("hash-values");
			digest.setText(m.getDigest());
			softwareInfo.addContent(digest);
			
//			Element RPMname=new Element("RPM-name");
//			RPMname.setText(m.getRPMname());
//			softwareInfo.addContent(RPMname);
			//
			Element edition=new Element("edition");
			edition.setText(m.getEdition());
			softwareInfo.addContent(edition);
			//
			Element manufacturer=new Element("manufacturer");
			manufacturer.setText(m.getManufacturer());
			softwareInfo.addContent(manufacturer);
			//
			Element issuetime=new Element("issuetime");
			issuetime.setText(m.getIssueTime());
			softwareInfo.addContent(issuetime);
			//
			Element description=new Element("description");
			description.setText(m.getDescription());
			softwareInfo.addContent(description);
			//
			Element copyright=new Element("copyright");
			copyright.setText(m.getCopyRight());
			softwareInfo.addContent(copyright);
			//
			mapping.addContent(softwareInfo);
			
			
//			//Creation
//			Element creation=new Element("creation");
//			//
//			Element creation_time=new Element("creation-time");
//			creation_time.setText((m.getCreation().getOperationTime()).toString());
//			creation.addContent(creation_time);
//			//
//			Element creation_oid=new Element("creation-oid");
//			creation_oid.setText(Integer.toString(m.getCreation().getOperatorID()));
//			creation.addContent(creation_oid);
//			//
//			mapping.addContent(creation);
//			
//		
//			
//			//Modification
//			Element modification=new Element("modification");
//			//
//			Element modification_time=new Element("modification-time");
//			modification_time.setText((m.getModification().getOperationTime()).toString());
//			modification.addContent(modification_time);
//			//
//			Element modification_oid=new Element("modification-oid");
//			modification_oid.setText(Integer.toString(m.getModification().getOperatorID()));
//			modification.addContent(modification_oid);
//			//
//			mapping.addContent(modification);
//			
			
			/////////
			root.addContent(mapping);
			
		}
			
		//////	 
		XMLOutputter XMLOut = new XMLOutputter();  
		
		try {   
			XMLOut.output(document, new FileOutputStream(fileName));   
		} catch (FileNotFoundException e) {   
			e.printStackTrace();   
		} catch (IOException e) {   
			e.printStackTrace();   
		} 
		
		
	}

	

	public static ArrayList parserXml(String fileName) {  //读取XML文件，返回一个collection 
		SAXBuilder builder=new SAXBuilder(false);  
		ArrayList c=new ArrayList();
		
		try {   
			Document document=builder.build("file:\\"+fileName);   
			Element mappings=document.getRootElement();
			
			List mpl=mappings.getChildren();
			Iterator i = mpl.iterator(); 
			while(i.hasNext()){
				Element mapping=(Element)i.next();
				FullInfoBean mb=new FullInfoBean();
				//attribute
				Element attribute=mapping.getChild("attribute");
				Attribute att=new Attribute();
				
				Element attributeName=attribute.getChild("attribute-name");
				MappingService ms = new MappingService();
				AttIDtoName a = new AttIDtoName(); 
				a = ms.getAttbyName(attributeName.getText());
				if(a == null)
					mb.setAttributeID(-1);
				else
					mb.setAttributeID(a.getAttributeID());
				
				Element trusted = attribute.getChild("trusted");
				if(trusted.getText().equals("可信"))
					mb.setTrusted(1);
				else if(trusted.getText().equals("不可信"))
					mb.setTrusted(0);
				else
					mb.setTrusted(-1);
				
				Element className=attribute.getChild("class-name");
				int classID = ms.getClassIDByName(className.getText());
				mb.setClassID(classID);
								
				//software_info
				Element software_info=mapping.getChild("software-info");
				MeasurementValues mvs=new MeasurementValues();

				Element softwareName=software_info.getChild("software-name");
				mb.setSoftwareName(softwareName.getText());
				
				Element hash=software_info.getChild("hash-values");
				mb.setDigest(hash.getText());
//				
//				Element RPMname=software_info.getChild("RPM-name");
//				mb.setRPMname(RPMname.getText());
//				
				Element edition=software_info.getChild("edition");
				mb.setEdition(edition.getText());
				
				Element manufacturer=software_info.getChild("manufacturer");
				mb.setManufacturer(manufacturer.getText());
				
				Element issuetime=software_info.getChild("issuetime");
				mb.setIssueTime(issuetime.getText());
				
				Element description=software_info.getChild("description");
				mb.setDescription(description.getText());
				
				Element copyright=software_info.getChild("copyright");
				mb.setCopyRight(copyright.getText());
								
//				//creation
//				Element creation=mapping.getChild("creation");
//				Operation cn=new Operation();
//				
//				Element creation_time=creation.getChild("creation-time");
//				cn.setOperationTime(Timestamp.valueOf(creation_time.getText()));
//				
//				Element creation_oid=creation.getChild("creation-oid");
//				cn.setOperatorID(Integer.parseInt(creation_oid.getText()));
//				
//				mb.setCreation(cn);
//				
//				//modification
//				Element modification=mapping.getChild("modification");
//				Operation mn=new Operation();
//				
//				Element modification_time=modification.getChild("modification-time");
//				mn.setOperationTime(Timestamp.valueOf(modification_time.getText()));
//				
//				Element modification_oid=modification.getChild("modification-oid");
//				mn.setOperatorID(Integer.parseInt(modification_oid.getText()));
//				
//				mb.setModification(mn);
//				
				//加入ArrayList
				c.add(mb);
				
			}//while	  
		} catch (JDOMException e) {   

		e.printStackTrace();   
		} catch (IOException e) {   

		e.printStackTrace();   
		}    

		return c;
		   
	
	}

	
}
