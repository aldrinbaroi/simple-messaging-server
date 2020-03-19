package net.baroi.messaging.server.simple.message.queue;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.baroi.messaging.server.simple.message.Message;

/**
 * 
 * @author Aldrin Baroi
 *
 * @param <T> Message class (MailMessage, SMSMessage, or MMSMessage)
 */
@Component
@Scope("singleton")
@Slf4j
public class MessageQueue {

	private String queueType;
	private ConcurrentLinkedQueue<Message> messageQueue;
	@Autowired
	private MessageQueueProcessor messageQueueProcessor;

	/**
	 * 
	 * 
	 */
	MessageQueue() {
		Random r = new Random();
		queueType = "" + (r.nextInt() % 101);
		messageQueue = new ConcurrentLinkedQueue<>();
	}

	public void queueMessage(Message message) {
		if (queueType == null) {
			log.error("ERROR!!! This should not happen!!! queue type is not set!!!  Not queuing message!");
		}
		messageQueue.add(message);
		log.debug("Message queued.");
		log.debug("Sending interrupt signal to message queue processor.");
		messageQueueProcessor.interrupt();
	}
	
	void requeueMessage(Message message) {
		messageQueue.add(message);
	}

	boolean isEmpty() {
		return messageQueue.isEmpty();
	}

	Message remove() {
		return messageQueue.remove();
	}
}
