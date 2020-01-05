/**
 * 
 */
package net.baroi.messaging.server.simple.message;

import org.springframework.stereotype.Component;

/**
 * @author Aldrin Baroi
 *
 */
@Component
public class MailMessageValidator {

	/**
	 * All fields are required
	 * 
	 * @param message
	 * @return
	 */
	public boolean isMessageValid(MailMessage message) {
		if (message == null)
			return false;
		String to = message.getTo();
		String from = message.getFrom();
		String subject = message.getSubject();
		String text = message.getText();
		if (!(to == null) && !(from == null) && !(subject == null) && !(text == null))
			return true;
		return false;
	}

	/**
	 * 
	 * All fields are required
	 * 
	 * @param message
	 * @return
	 */
	public boolean isMessageValid(SMSMessage message) {
		if (message == null)
			return false;
		String from = message.getFrom();
		String mobileCarrierId = message.getMobileCarrierId();
		String mobileNumber = message.getMobileNumber();
		String text = message.getText();
		if (!(from == null) && !(mobileCarrierId == null) && !(mobileNumber == null) && !(text == null))
			return true;
		return false;
	}

	/**
	 * Text message & media name are optional
	 * 
	 * @param message
	 * @return
	 */
	public boolean isMessageValid(MMSMessage message) {
		if (message == null)
			return false;
		String from = message.getFrom();
		String mobileCarrierId = message.getMobileCarrierId();
		String mobileNumber = message.getMobileNumber();
		// (OPTIONAL) String text = message.getText();
		// (OPTIONAL) String mediaName = message.getMediaName();
		String mediaType = message.getMediaType();
		String mediaContent = message.getMediaContent();
		if (!(from == null) && !(mobileCarrierId == null) && !(mobileNumber == null) && !(mediaType == null)
				&& !(mediaContent != null))
			return true;
		return false;
	}
}
