package edu.mit.tbp.se.chat.message;

public class Toc2SignonMessage extends TOCMessage {
	
	//Declare string variable for screen name
	private String sUsername;
	
	//Declare string variable for password
	private String sPassword;

	// server-level constants
	public static final String LOGIN_SERVER = "login.oscar.aol.com";
	public static final int LOGIN_PORT = 5190; //1234; 

	// protocol-level constants
	public static final byte[] ROASTING_STRING = "Tic/Toc".getBytes();

	// client-level constants
	public static final String version = "TIC:TBP2";
	public static final String language = "english";

	/**
	 * Create a new Toc2SignonMessage.
	 *
	 * @param username username to signon with
	 * @param password associated clear-text password
	 **/
	public Toc2SignonMessage(String sUsername,
			   String sPassword) {
		
		//Setup constructor values
	  this.sUsername = sUsername;
	  this.sPassword = sPassword;

	}

	/**
	 * Roast the user's password
	 * @return sReturn --> a roasted version of the user's password.
	 * In this method, the password value is roasted since this is coming over a FLAP connection. The main reason
	 * why the passwords are roasted is for security purposes. Roasting is done by first xoring each byte in the password 
	 * with the equivalent modulo byte in the roasting string. The result is then converted to Hex, and prepended 
	 * with "0x".
	 * Example --> "password" when roasted becomes "0x2408105c23001130". 
	 **/
	public String roastedPassword() {
	
		//Initialize integer variable for indexing
		int iROASTING_STRING_INDEX = 0;
		//Initialize integer variable to store the XOR output between two numbers
		int iXOR = 0;
		//Initialize return value
		String sReturn = "0x";
		//Initialize string to store Hex value
		String sHex = ""; 
  	
		//Create a loop that would go through each character in the password string, and perform appropriate operations on them
		for (int i = 0; i <= (sPassword.length()-1); i++) {
			//Take the current value of the "ROASTING_STRING", XOR it with the current value of the password, and store it
			iXOR = ROASTING_STRING[iROASTING_STRING_INDEX]^(int)sPassword.charAt(i);
			//Convert the above calculated value to Hex
			sHex = Integer.toHexString(iXOR);
			//Add a "0" if the number of characters in the Hex value is 1
			if (sHex.length() == 1) {
				sHex = "0" + sHex;
			}
			//Concatenate the Hex and the return values
			sReturn = sReturn + sHex;
			//Increment the "ROASTING_STRING" index
			iROASTING_STRING_INDEX++;
			//If the number of characters in the "ROASTING_STRING" is less than the number of characters in the password,
			//then start from the first character of the "ROASTING_STRING" string and continue the process.
			if(iROASTING_STRING_INDEX == ROASTING_STRING.length) {
				iROASTING_STRING_INDEX = 0;
			}
  		
		}
  	
		//Return the roasted password
		return sReturn;
  	
	}

	/**
	 * Return the login code
	 * @return sReturn --> the secret login code that will be generated based upon the screen name and password
	 * In this method, the secret code is created with the first letter of the screen name and
    	password. The following logic is implemented in this method to obtain the secret code:

      	sn = ascii value of the first letter of the screen name - 96 = 20
      	pw = ascii value of the first character of the password - 96 = 24
      
      	a = sn * 7696 + 738816 = 892736
      	b = sn * 746512 = 14930240
      	c = pw * a = 21425664
      
      secret code is "c - a + b + 71665152"
	 * Example --> If the screen name is "test" and the password is "x5435" then the secret code is "107128320". 
	 */
	public String generateCode() {
	
		//Declare string variables
		String sReturn = null;
  	
		//Declare integer variables
		int iUser, iPass, ia , ib, ic, iReturn;
  	
		//Convert the first character of the username to decimal, and store it
		iUser = sUsername.codePointAt(0) - 96;
  	
		//Convert the first character of the password to decimal, and store it
		iPass = sPassword.codePointAt(0) - 96;
  	
		//Calculate a. a = iUser * 7696 + 738816
		ia = (iUser * 7696) + 738816;
  	
		//Calculate b. b = iUser * 746512
		ib = iUser * 746512;
  	
		//Calculate c. c = iPass * a
		ic = iPass * ia;
  	
		if (ia != 0 && ib != 0 && ic != 0) {
			//Calculate the secret code
			iReturn = ic - ia + ib + 71665152;
			//Convert the code to string
			sReturn = String.valueOf(iReturn);
		}
		else {
			//If any of the values are 0, set return value to null
			sReturn = null;
		}
		
		toWireFormat();
		
		//Return the secret code value or null value if invalid characters are found
		return sReturn;
  	
	}

	@Override
	public String toWireFormat() {
	
		//Create a string that would concatenate all required parameters to be sent to the server
		String sReturn = "toc_signon" + " " + LOGIN_SERVER + " " + LOGIN_PORT + " " + sUsername + " " + sPassword + " " + language + " " + version;		
		return sReturn;
	}

}
