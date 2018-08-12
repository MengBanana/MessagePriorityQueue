/**
 * Message.java
 * 
 * @author Xinmeng Zhang
 */
package edu.northeastern.cs_5004;

/**
 * This class implements a Message with optional message string that can
 * be offered on a MesssageQueue.
 *
 */
public class Message {
	/**
	 * The message string
	 */
	private final String msgstr;
	
	/**
	 * Create a Message with no message string
	 */
	public Message() {
		msgstr = null;
	}
	
	/**
	 * Create a Message with the specified message string.
	 *
	 * @param msgstr the message string
	 */
	public Message(String msgstr) {
		this.msgstr = msgstr;
	}
	
	/**
	 * Get the message string for this message
	 *
	 * @return the message string for this message
	 */
	public String getMessage() {
		return msgstr;
	}

}
