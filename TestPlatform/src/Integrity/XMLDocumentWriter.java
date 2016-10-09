package Integrity;

import org.w3c.dom.*;
import java.io.*;

public class XMLDocumentWriter {
    private PrintWriter out;
    private boolean indentOn;
    /** Initialize the output stream */
    public XMLDocumentWriter(PrintWriter out) {	this.out = out; this.indentOn = true; }

    /** Close the output stream. */
    public void close() { out.close(); }

    /** Output a DOM Node (such as a Document) to the output stream */
    public void write(Node node) { write(node, ""); }

    public void setIndentOn(boolean on) { indentOn = on; }
    
    /**
     * Output the specified DOM Node object, printing it using the specified
     * indentation string
     **/
    public void write(Node node, String indent) {
	// The output depends on the type of the node
	switch(node.getNodeType()) {
	case Node.DOCUMENT_NODE: {       // If its a Document node
	    Document doc = (Document)node;
	    if(indentOn)
	    	out.println(indent + "<?xml version='1.0'?>");  // Output header
	    else
	    	out.println("<?xml version='1.0'?>");
	    Node child = doc.getFirstChild();   // Get the first node
	    while(child != null) {              // Loop 'till no more nodes
		write(child, indent);           // Output node
		child = child.getNextSibling(); // Get next node
	    }
	    break;
	} 
	case Node.DOCUMENT_TYPE_NODE: {  // It is a <!DOCTYPE> tag
	    DocumentType doctype = (DocumentType) node;
	    out.println("<!DOCTYPE " + doctype.getName() + ">");
	    break;
	}
	case Node.ELEMENT_NODE: {        // Most nodes are Elements
	    Element elt = (Element) node;
	    if(indentOn)
	    	out.print(indent + "<" + elt.getTagName());   // Begin start tag
	    else
	    	out.print("<" + elt.getTagName());
	    NamedNodeMap attrs = elt.getAttributes();     // Get attributes
	    for(int i = 0; i < attrs.getLength(); i++) {  // Loop through them
		Node a = attrs.item(i);
		out.print(" " + a.getNodeName() + "='" +  // Print attr. name
			  fixup(a.getNodeValue()) + "'"); // Print attr. value
	    }
	    out.println(">");                             // Finish start tag

	    String newindent = indent + "    ";           // Increase indent
	    Node child = elt.getFirstChild();             // Get child
	    while(child != null) {                        // Loop 
		write(child, newindent);                  // Output child
		child = child.getNextSibling();           // Get next child
	    }

	    if(indentOn)
	    	out.println(indent + "</" + elt.getTagName() + ">");  // Output end tag
	    else
	    	out.println("</" + elt.getTagName() + ">");
	    
	    break;
	}
	case Node.TEXT_NODE: {                   // Plain text node
	    Text textNode = (Text)node;
	    String text = textNode.getData().trim();   // Strip off space
	    if ((text != null) && text.length() > 0)   // If non-empty
		{
	    	if(indentOn)
	    		out.println(indent + fixup(text));     // print text
	    	else
	    		out.println(fixup(text));
		}
	    break;
	}
	case Node.PROCESSING_INSTRUCTION_NODE: {  // Handle PI nodes
	    ProcessingInstruction pi = (ProcessingInstruction)node;
	    if(indentOn)
	    	out.println(indent + "<?" + pi.getTarget() +
			       " " + pi.getData() + "?>");
	    else
	    	out.println("<?" + pi.getTarget() + " " + pi.getData() + "?>");

	    break;
	}
	case Node.ENTITY_REFERENCE_NODE: {        // Handle entities
		if(indentOn)
			out.println(indent + "&" + node.getNodeName() + ";");
		else
			out.println("&" + node.getNodeName() + ";");
	    break;
	}
	case Node.CDATA_SECTION_NODE: {           // Output CDATA sections
	    CDATASection cdata = (CDATASection)node;
	    // Careful! Don't put a CDATA section in the program itself!
	    if(indentOn)
	    	out.println(indent + "<" + "![CDATA[" + cdata.getData() +
	    			"]]" + ">");
	    else
	    	out.println("<" + "![CDATA[" + cdata.getData() + "]]" + ">");	    	
	    break;
	}
	case Node.COMMENT_NODE: {                 // Comments
	    Comment c = (Comment)node;
	    if(indentOn)
	    	out.println(indent + "<!--" + c.getData() + "-->");
	    else
	    	out.println("<!--" + c.getData() + "-->");
	    break;
	}
	default:   // Hopefully, this won't happen too much!
	    System.err.println("Ignoring node: " + node.getClass().getName());
	    break;
	}
    }

    // This method replaces reserved characters with entities.
    String fixup(String s) {
	StringBuffer sb = new StringBuffer();
	int len = s.length();
	for(int i = 0; i < len; i++) {
	    char c = s.charAt(i);
	    switch(c) {
	    default: sb.append(c); break;
	    case '<': sb.append("&lt;"); break;
	    case '>': sb.append("&gt;"); break;
	    case '&': sb.append("&amp;"); break;
	    case '"': sb.append("&quot;"); break;
	    case '\'': sb.append("&apos;"); break;
	    }
	}
	return sb.toString();
    }
}