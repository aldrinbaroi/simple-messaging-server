/**
 * 
 */
package net.baroi.messaging.server.simple.encryption;

/**
 * @author Aldrin Baroi
 *
 */
public class EncryptorError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 27691350575268571L;

	/**
	 * @param message
	 */
	public EncryptorError(String message) {
		super(message);
	}
}
