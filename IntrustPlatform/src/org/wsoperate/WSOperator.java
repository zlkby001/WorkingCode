package org.wsoperate;

import java.io.InputStream;
import java.net.URL;
import org.forms.XMLReader;;

public class WSOperator {

	public WSOperator() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean getWSStatus() {
		XMLReader xmlreader = new XMLReader();
	    String trans_value = xmlreader.searchXML("WSIP");
	    try {
	    	if(trans_value.contains("127.0.0.1"))
	    		return true;
			URL url = new URL(trans_value);
			InputStream in = url.openStream();
			in.close();
			return true;			
		} catch (Exception e) { 
			// TODO Auto-ge nerated catch block
			e.printStackTrace();			
			return true;
		}	    
	}
}
