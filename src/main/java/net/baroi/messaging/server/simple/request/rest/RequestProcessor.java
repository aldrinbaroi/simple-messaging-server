/**
 * 
 */
package net.baroi.messaging.server.simple.request.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.baroi.messaging.server.simple.message.MMSMessage;
import net.baroi.messaging.server.simple.message.MailMessage;
import net.baroi.messaging.server.simple.message.MailMessageValidator;
import net.baroi.messaging.server.simple.message.SMSMessage;
import net.baroi.messaging.server.simple.message.queue.MessageQueue;

/**
 * @author Aldrin Baroi
 *
 */
@RestController
@RequestMapping(path = "/api/messages")
@Slf4j
public class RequestProcessor {

	@Autowired
	private MessageQueue messageQueue;
	@Autowired
	private MailMessageValidator messageValidator;

	/**
	 * 
	 * @param message
	 * @return
	 */
	@PostMapping(path = "/mail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public RequestStatus sendMmail(@RequestBody MailMessage message) {
		log.info("Received request to send mail message: " + message.toString());
		if (messageValidator.isMessageValid(message)) {
			messageQueue.queueMessage(message);
			return new RequestStatus("Message queued for delivery");
		} else
			return new RequestStatus("Invalid message. Message is NOT queued for delivery");
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	@PostMapping(path = "/sms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public RequestStatus sendSMS(@RequestBody SMSMessage message) {
		log.info("Received request to send SMS message: " + message.toString());
		if (messageValidator.isMessageValid(message)) {
			messageQueue.queueMessage(message);
			return new RequestStatus("Message queued for delivery");
		} else
			return new RequestStatus("Invalid message. Message is NOT queued for delivery");
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	@PostMapping(path = "/mms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public RequestStatus sendMMS(@RequestBody MMSMessage message) {
		log.info("Received request to send MMS message: " + message.toString());
		if (messageValidator.isMessageValid(message)) {
			messageQueue.queueMessage(message);
			return new RequestStatus("Message queued for delivery");
		} else
			return new RequestStatus("Invalid message. Message is NOT queued for delivery");
	}
}
