/**
 * 
 */
package net.baroi.messaging.server.simple.message.sender;

/**
 * @author Aldrin Baroi
 *
 */
public class MessageSenderException extends Exception {

	private static final long serialVersionUID = -5377086811539802734L;

	/**
	 * 
	 */
	public MessageSenderException() {
		super();
	}

	/**
	 * @param message
	 */
	public MessageSenderException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MessageSenderException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MessageSenderException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MessageSenderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
