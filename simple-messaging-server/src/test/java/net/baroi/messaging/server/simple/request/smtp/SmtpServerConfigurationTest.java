package net.baroi.messaging.server.simple.request.smtp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.baroi.messaging.server.simple.request.smtp.SmtpServerConfiguration;

@SpringBootTest
class SmtpServerConfigurationTest {

	@Autowired
	private SmtpServerConfiguration config;

	@Test
	void testGetSoftwareName() {
		assertThat(config.getSoftwareName()).isEqualTo("Simple SMTP Server");
	}

	@Test
	void testGetPort() {
		assertThat(config.getPort()).isEqualTo(1025);
	}

	@Test
	void testGetTimeout() {
		assertThat(config.getTimeout()).isEqualTo(120);
	}
}
