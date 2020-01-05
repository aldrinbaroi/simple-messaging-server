package net.baroi.messaging.server.simple.message.sender;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import net.baroi.messaging.server.simple.carrier.MobileCarrierMmsDoaminNotSetupException;
import net.baroi.messaging.server.simple.carrier.MobileCarrierNotSetupException;
import net.baroi.messaging.server.simple.carrier.MobileCarrierSmsDoaminNotSetupException;
import net.baroi.messaging.server.simple.carrier.MobileCarriersConfig;
import net.baroi.messaging.server.simple.media.Media;
import net.baroi.messaging.server.simple.media.MediaUtil;
import net.baroi.messaging.server.simple.message.MMSMessage;
import net.baroi.messaging.server.simple.message.MailMessage;
import net.baroi.messaging.server.simple.message.SMSMessage;
import net.baroi.messaging.server.simple.message.sender.MessageSender;

@SpringBootTest
class MessageSenderTest {

	private static final String mailUser = "MailUser@localhost.com";
	private static final String mailPassword = "mail";
	private static final String mailFrom = "from@localhost.com";
	private static final String mailSubject = "Testing Mail";
	private static final String mailText = "Test mail body content";
	//
	private static final String smsUser = "SmsUer";
	private static String smsUser_email;
	private static final String smsPassword = "sms";
	private static final String smsFrom = mailFrom;
	private static final String smsCarrier = "verizon";
	private static final String smsText = "Test sms body content";
	//
	private static final String mmsUser = "MmsUser";
	private static String mmsUser_email;
	private static final String mmsPassword = "mms";
	private static final String mmsFrom = mailFrom;
	private static final String mmsCarrier = smsCarrier;
	private static final Media media = new Media();
	@Autowired
	private MessageSender messageSender;
	private static GreenMail mailServer;
	@Value("classpath:images/Test.png")
	private Resource resourceFile;
	@Autowired
	private MobileCarriersConfig carrierConfig;
	// NEED TO MATCH smtp host & port setup in the application-test.yml
	private static final String SMTP_IMAP_HOST = "localhost";
	private static final int SMTP_PORT = 2025;
	private static final int IMAP_PORT = 2143; // <-- not in application-test.yml

	@BeforeAll
	public static void setUp() throws IOException, MobileCarrierNotSetupException {
		mailServer = new GreenMail(new ServerSetup[] { new ServerSetup(SMTP_PORT, SMTP_IMAP_HOST, "smtp"),
				new ServerSetup(IMAP_PORT, SMTP_IMAP_HOST, "imap") });
		mailServer.setUser(mailUser, mailPassword);
		mailServer.start();
		String mediaPath = MessageSenderTest.class.getClassLoader().getResource("./images/Test.png").getFile();
		Media _media = MediaUtil.laodMediaContent(mediaPath);
		media.setMediaName(_media.getMediaName());
		media.setMediaType(_media.getMediaType());
		media.setMediaContent(_media.getMediaContent());
	}

	@AfterAll
	public static void tearDown() {
		mailServer.stop();
	}

	@Test
	void testSendMessage_mail() throws MessagingException, IOException {
		MailMessage mailMessage = new MailMessage();
		mailMessage.setFrom(mailFrom);
		mailMessage.setTo(mailUser);
		mailMessage.setSubject(mailSubject);
		mailMessage.setText(mailText);
		messageSender.sendMessage(mailMessage);
		//
		Session imapSession = mailServer.getImap().createSession();
		Store store = imapSession.getStore("imap");
		store.connect(mailUser, mailPassword);
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		assertThat(inbox.getMessageCount()).isEqualTo(1);
		Message msgReceived = inbox.getMessage(1);
		assertThat(msgReceived.getSubject()).isEqualTo(mailSubject);
		assertThat(msgReceived.getContent().toString()).contains(mailText);
	}

	@Test
	void testSendMessage_sms() throws MessagingException, IOException, MobileCarrierNotSetupException,
			MobileCarrierSmsDoaminNotSetupException {
		smsUser_email = carrierConfig.getSmsAddress(smsCarrier, smsUser);
		mailServer.setUser(smsUser_email, smsPassword);
		//
		SMSMessage message = new SMSMessage();
		message.setFrom(smsFrom);
		message.setMobileNumber(smsUser);
		message.setMobileCarrierId(smsCarrier);
		message.setText(smsText);
		messageSender.sendMessage(message);
		//
		Session imapSession = mailServer.getImap().createSession();
		Store store = imapSession.getStore("imap");
		store.connect(smsUser_email, smsPassword);
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		assertThat(inbox.getMessageCount()).isEqualTo(1);
		Message msgReceived = inbox.getMessage(1);
		assertThat(msgReceived.getContent().toString()).contains(smsText);
	}

	// TODO Need to figure out how to test media (not working with Greenmail)
	// @Test
	void testSendMessage_mms() throws IOException, MobileCarrierNotSetupException, MessagingException,
			MobileCarrierMmsDoaminNotSetupException {
		mmsUser_email = carrierConfig.getMmsAddress(smsCarrier, smsUser);
		mailServer.setUser(mmsUser_email, mmsPassword);
		//
		final String mediaContent = media.getMediaContent();
		MMSMessage message = new MMSMessage();
		message.setFrom(mmsFrom);
		message.setMobileNumber(mmsUser);
		message.setMobileCarrierId(mmsCarrier);
		message.setMediaName(media.getMediaName());
		message.setMediaType(media.getMediaType());
		message.setMediaContent(mediaContent);
		messageSender.sendMessage(message);
		//
		Session imapSession = mailServer.getImap().createSession();
		Store store = imapSession.getStore("imap");
		store.connect(mmsUser_email, mmsPassword);
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		assertThat(inbox.getMessageCount()).isEqualTo(1);
		Message msgReceived = inbox.getMessage(1);
		assertThat(msgReceived.getContent().toString()).isEqualTo(mediaContent);
	}
}
