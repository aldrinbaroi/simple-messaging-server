/**
 * 
 */
package net.baroi.messaging.server.simple.message.sender;

import java.util.Base64;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.baroi.messaging.server.simple.carrier.MobileCarriersConfig;
import net.baroi.messaging.server.simple.message.MMSMessage;
import net.baroi.messaging.server.simple.message.MailMessage;
import net.baroi.messaging.server.simple.message.Message;
import net.baroi.messaging.server.simple.message.SMSMessage;
import net.baroi.util.exception.ExceptionUtil;

/**
 * @author Aldrin Baroi
 *
 */
@Service
@Slf4j
public class MessageSender {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private MobileCarriersConfig mobileCarriersConfig;

	/**
	 * 
	 * @param message
	 */
	public void sendMessage(Message message) {
		if (message instanceof MailMessage)
			sendMailMessage((MailMessage) message);
		else if (message instanceof SMSMessage)
			sendSMSMessage((SMSMessage) message);
		else if (message instanceof MMSMessage)
			sendMMSMessage((MMSMessage) message);
	}

	/**
	 * 
	 * @param message
	 */
	private void sendMailMessage(MailMessage message) {
		log.debug("Sending email...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			mimeMessage.setFrom(new InternetAddress(message.getFrom()));
			mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(message.getTo()));
			mimeMessage.setSubject(message.getSubject());
			mimeMessage.setText(message.getText());
			javaMailSender.send(mimeMessage);
			log.debug("Email sent.");
		} catch (MessagingException e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 
	 * @param message
	 */
	private void sendSMSMessage(SMSMessage message) {
		log.debug("Sending SMS...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			mimeMessage.setFrom(new InternetAddress(message.getFrom()));
			mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(
					mobileCarriersConfig.getSmsAddress(message.getMobileCarrierId(), message.getMobileNumber())));
			mimeMessage.setText(message.getText());
			javaMailSender.send(mimeMessage);
			log.debug("SMS sent.");
		} catch (Exception e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 
	 * @param message
	 */
	private void sendMMSMessage(MMSMessage message) {
		log.debug("Sending MMS...");
		String text = message.getText();
		if (text != null) {
			SMSMessage smsMessage = new SMSMessage(message.getFrom(), message.getMobileNumber(),
					message.getMobileCarrierId(), text);
			sendSMSMessage(smsMessage);
		}
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			mimeMessage.setFrom(new InternetAddress(message.getFrom()));
			mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(
					mobileCarriersConfig.getMmsAddress(message.getMobileCarrierId(), message.getMobileNumber())));
			mimeMessage.setContent(Base64.getDecoder().decode(message.getMediaContent()), message.getMediaType());
			javaMailSender.send(mimeMessage);
			log.debug("MMS sent.");
		} catch (Exception e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}
	}
}
