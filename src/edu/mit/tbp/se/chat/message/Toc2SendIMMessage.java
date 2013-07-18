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
	  this.sDestinationUser = normalise(destinationUser);
	  this.sMessage = encodeText(message);
	  sAuto = auto ? " auto" : ""; 

}

  @Override
  public String toWireFormat() {
	  //Return a wire format value that consists of "toc_send_im" & Username & Message & Auto
	  return ("toc_send_im " + sDestinationUser + " " + sMessage + sAuto);
  }
  
  /** Convert a buddy name to normalised format - remove spaces and convert to lower case
   * @param input The un-normalised buddy name
   * @return the normalised buddy name
   */    
  public static String normalise(String input) {
	  //Create an array to store all strings
      StringBuffer output=new StringBuffer();
      //Convert all strings to lower case
      String temp=input.toLowerCase();
      //For all strings in the array
      for (int i=0;i<input.length();i++)
      {
    	  //At this particular character index
          char c=temp.charAt(i);
          //Check for ranges for each character. One range check would be from 0-9 and the other from a-z
          if ((c>= '0' && c<='9')||(c>='a' && c<='z'))
          {
        	  //Append to string if all spaces are removed, and all strings are lower case
              output.append(c);
          }
      }
      
      //Return the final normalised value
      return(output.toString());
  }
  
  /** Encode a text message so that it is suitable for transmission using toc_send_im
  * The objective of this method is to append "//" for all special characters, and append the end of the text with "\"
  * @param input The text to be encoded
  * @return The encoded text
  */    
 public static String encodeText(String input)
 {	
	 //Create a string buffer object
     StringBuffer output = new StringBuffer("\"");
     
     for (int i=0;i<input.length();i++)
     {
    	 //At this particular character index
         char c=input.charAt(i);
         switch (c)
         {
             case '\"':
             case '(':
             case ')':
             case '$':
             case '\\':
             case '{':
             case '}':
             case '[':
             case ']':
                 output.append('\\');
                 break;
         }
         //Create a final appended string that has all the characters that was worked on from the loop
         output.append(c);
     }
     
     //Append the end of the string with a "\"
     output.append('\"');
     
     //Return the encoded string
     return(output.toString());
 }

}
