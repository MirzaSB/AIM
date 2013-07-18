package edu.mit.tbp.se.chat.message;

public class TocInitDoneMessage extends TOCMessage {

  /**
   * Create a new TocInitDoneMessage.
   **/
	
	//Set the variable to a specific "done" string
	private static final String sDoneMessage = "toc_init_done";
    
	public TocInitDoneMessage() {
		// pass
	}

	@Override
	public String toWireFormat() {
		// Return the done string
		return sDoneMessage;
	}

}
