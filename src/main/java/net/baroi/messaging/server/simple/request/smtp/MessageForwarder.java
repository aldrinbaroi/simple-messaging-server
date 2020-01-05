/**
 * 
 */
package net.baroi.messaging.server.simple.request.smtp;

import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.james.protocols.smtp.MailEnvelope;
import org.apache.james.protocols.smtp.SMTPSession;
import org.apache.james.protocols.smtp.hook.HookResult;
import org.apache.james.protocols.smtp.hook.MessageHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.baroi.util.exception.ExceptionUtil;

/**
 * @author Aldrin
 *
 */
@Component
@Slf4j
class MessageForwarder implements MessageHook {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void init(Configuration config) throws ConfigurationException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public HookResult onMessage(SMTPSession session, MailEnvelope mail) {
		try {
			log.debug("Procssing SMTP request...");
			MimeMessage mimeMessage = mailSender.createMimeMessage(mail.getMessageInputStream());
			mailSender.send(mimeMessage);
		} catch (IOException e) {
			log.error("ERROR: " + ExceptionUtil.getStackTrace(e));
		}
		return HookResult.OK;
	}
}
