package edu.mit.tbp.se.chat.message;

public class Toc2SendIMMessage extends TOCMessage {
	
	//Declare private string variables
	private String sDestinationUser;
	private String sMessage;
	private String sAuto;	

  public Toc2SendIMMessage(String destinationUser,
			   String message) {
	  
	  this.sDestinationUser = destinationUser;
	  this.sMessage = message;
	  
  }
  
  //Create another constructor to accomodate "auto"/"auto message"
  public Toc2SendIMMessage(String destinationUser,
		   String message, boolean auto) {
	  this.sDestinationUser = Utility.normalise(destinationUser);
	  this.sMessage = Utility.encodeText(message);
	  sAuto = auto ? " auto" : ""; 

  }

  @Override
  public String toWireFormat() {
	  //Return a wire format value that consists of "toc_send_im" & Username & Message & Auto
	  return ("toc_send_im " + sDestinationUser + " " + sMessage + sAuto);
  }

}
