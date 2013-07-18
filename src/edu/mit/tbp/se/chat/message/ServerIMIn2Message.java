package edu.mit.tbp.se.chat.message;

public class ServerIMIn2Message extends TOCMessage {
	
	private String wireformat;
	
	//Create private strings for source user, auto reply, and main message of IM
	private String sSourceUser;
	private boolean bAuto;
	private String sMessage;
	
	//Create a constructor for the class
	public ServerIMIn2Message(String wireformat) {
		this.wireformat = wireformat;
	}
	
	//Create a method that would retrieve the source user from the wireformat string
	public String getSourceUser() {
    	sSourceUser = wireformat.split("\\s")[1];
    	return sSourceUser;
	}
	
	//Create a method that would retrieve the auto reply flag from the wireformat string
	public boolean getAutoResponse() {
    	String sTemp = wireformat.split("\\s")[wireformat.split("\\s").length-1];
    	if (sTemp.equals("auto")) {
    		bAuto = true;
    	}
    	else {
    		bAuto = false;
    	}
    	return bAuto;
	}
	
	//Create a method that would retrieve the message from the wireformat string
	public String getMessage() {
    	if (wireformat.split("\\s")[wireformat.split("\\s").length-1].equals("auto")) {
    		sMessage = wireformat.split("\\s")[wireformat.split("\\s").length-2];
    	}
    	else {
    		sMessage = wireformat.split("\\s")[wireformat.split("\\s").length-1];
    	}
    	return sMessage;
	}
	
	//Create a method that would create a new wireformat based on the above methods' parameters and AIM specification document
	public String toWireFormat() {
		String sReturn = "ServerIMIn2Message: " + sSourceUser + " " + bAuto + " " + sMessage;
    	return sReturn;
	}
}
