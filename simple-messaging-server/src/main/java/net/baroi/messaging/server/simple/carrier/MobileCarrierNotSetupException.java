/**
 * 
 */
package net.baroi.messaging.server.simple.carrier;

/**
 * @author Aldrin Baroi
 *
 */
public class MobileCarrierNotSetupException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2907187657367110755L;

	/**
	 * 
	 * @param message
	 */
	public MobileCarrierNotSetupException(String message) {
		super(message);
	}
}
