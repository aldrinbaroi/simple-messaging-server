/**
 * 
 */
package net.baroi.messaging.server.simple.request.smtp;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.james.metrics.api.NoopMetricFactory;
import org.apache.james.protocols.api.Protocol;
import org.apache.james.protocols.netty.NettyServer;
import org.apache.james.protocols.smtp.SMTPConfigurationImpl;
import org.apache.james.protocols.smtp.SMTPProtocol;
import org.apache.james.protocols.smtp.SMTPProtocolHandlerChain;
import org.jboss.netty.util.HashedWheelTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Aldrin Baroi
 *
 */
@Component
@Slf4j
class SmtpServer {

	@Autowired
	private SmtpServerConfiguration smtpConfig;
	@Autowired
	private MessageForwarder messageForwarder;
	private NettyServer server;

	@PostConstruct
	public void start() throws Exception {
		log.info("SMTP server is starting...");
		SMTPConfigurationImpl smtpConfiguration = new SMTPConfigurationImpl();
		smtpConfiguration.setSoftwareName(smtpConfig.getSoftwareName());
		SMTPProtocolHandlerChain chain = new SMTPProtocolHandlerChain(new NoopMetricFactory());
		chain.add(messageForwarder);
		chain.wireExtensibleHandlers();
		Protocol protocol = new SMTPProtocol(chain, smtpConfiguration);
		server = new NettyServer.Factory(new HashedWheelTimer()).protocol(protocol).build();
		server.setListenAddresses(new InetSocketAddress(smtpConfig.getPort()));
		server.setTimeout(smtpConfig.getTimeout());
		server.bind();
		log.info("SMTP server started on port: {}", smtpConfig.getPort());
	}

	@PreDestroy
	public void stop() {
		log.info("Shutting down SMTP server...");
		server.unbind();
		log.info("SMTP server shutdown.");
	}
}
