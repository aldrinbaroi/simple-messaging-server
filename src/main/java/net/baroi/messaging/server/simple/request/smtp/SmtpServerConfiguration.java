package net.baroi.messaging.server.simple.request.smtp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "simple-messaging-service.smtp-server")
@Data
class SmtpServerConfiguration {

	private String softwareName;
	private int port = 10025;
	private int timeout = 120;
}
