package net.baroi.messaging.server.simple.message.queue;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.baroi.messaging.server.simple.message.sender.MessageSender;

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
	private AtomicBoolean sleeping;

	/**
	 * 
	 */
	MessageQueueProcessor() {
		process = new AtomicBoolean(false);
		sleeping = new AtomicBoolean(false);
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
	 * @return
	 */
	public boolean isSleeping() {
		return sleeping.get();
	}

	/**
	 * 
	 */
	public void run() {
		log.info("Message queue processor is running.");
		while (process.get() == true) {
			sleeping.set(false);
			for (int count = 0; count < 5; count++) {
				if (!messageQueue.isEmpty()) {
					log.debug("Sending message...");
					messageSender.sendMessage(messageQueue.remove());
				}
				takeShortSleep();
			}
			sleeping.set(true);
			takeDeepSleep();
		}
		log.info("Message queue processor is shutdown.");
	}

	private void takeShortSleep() {
		try {
			log.debug("Message queue processor going into short sleep.");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// ignored
			log.debug("Received interrupt signal during short sleep");
		}
	}

	private void takeDeepSleep() {
		try {
			log.debug("Message queue processor going into deep sleep mode");
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// ignored
			log.debug("Received interrupt signal during deep sleep.");
		}
	}
}
