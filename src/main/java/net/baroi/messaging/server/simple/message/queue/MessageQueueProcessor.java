package net.baroi.messaging.server.simple.message.queue;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.baroi.messaging.server.simple.message.Message;
import net.baroi.messaging.server.simple.message.sender.MessageSender;
import net.baroi.messaging.server.simple.message.sender.MessageSenderException;
import net.baroi.util.exception.ExceptionUtil;

/**
 * 
 * @author Aldrin Baroi
 *
 * @param <T> Message type (MailMessage, SMSMessage, MMSMessage)
 */
@Component
@Slf4j
class MessageQueueProcessor extends Thread {

	@Autowired
	private MessageSender messageSender;
	@Autowired
	private MessageQueue messageQueue;
	private AtomicBoolean process;
	private int sendFailCount;
	private final static long SHORT_SLEEP_DURATION = 1000;
	private final static long CIRCUIT_BREAKER_SLEEP_DURATION = 30 * 1000;
	private final static long LONG_SLEEP_DURATION = Long.MAX_VALUE;

	/**
	 * 
	 */
	MessageQueueProcessor() {
		process = new AtomicBoolean(false);
		this.sendFailCount = 0;
	}

	/**
	 * 
	 */
	@PostConstruct
	@Override
	public void start() {
		if (process.compareAndSet(false, true) == true) {
			log.info("Starting message queue processor...");
			super.start();
		} else
			log.info("Message queue processing already started...");
	}

	/**
	 * 
	 */
	@PreDestroy
	public void shutdown() {
		log.info("Shutting down message queue processor.");
		process.set(false);
		this.interrupt();
	}

	/**
	 * 
	 */
	public void run() {
		log.info("Message queue processor is running.");
		while (process.get() == true) {
			while (!messageQueue.isEmpty()) {
				log.debug("Sending message...");
				Message message = messageQueue.remove();
				try {
					messageSender.sendMessage(message);
				} catch (MessageSenderException e) {
					log.error("Failed to send message.");
					log.error("Requeuing message...");
					messageQueue.requeueMessage(message);
					if (++sendFailCount > 3) {
						sendFailCount = 0;
						takeCircuitBreakerSleep();
					}
					log.error(ExceptionUtil.getStackTrace(e));
				}
				takeShortSleep();
			}
			takeDeepSleep();
		}
		log.info("Message queue processor is shutdown.");
	}

	private void takeShortSleep() {
		log.debug("Message queue processor going into short sleep.");
		gotoSleep(SHORT_SLEEP_DURATION);
	}

	private void takeCircuitBreakerSleep() {
		log.debug("Message queue processor going into circuit breaker sleep mode");
		gotoSleep(CIRCUIT_BREAKER_SLEEP_DURATION);
	}

	private void takeDeepSleep() {
		log.debug("Message queue processor going into deep sleep mode");
		gotoSleep(LONG_SLEEP_DURATION);
	}

	private void gotoSleep(long milliSec) {
		try {
			Thread.sleep(milliSec);
		} catch (InterruptedException e) {
			// ignored
			log.debug("Received interrupt signal during sleep.");
		}
	}
}
