/**
 * 
 */
package net.baroi.messaging.server.simple.encryption;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aldrin Baroi
 *
 */
@Configuration
public class EncryptorFactory {

	@Autowired
	private ApplicationArguments args;

	@Bean(("jasyptStringEncryptor"))
	public StringEncryptor getStringEncryptor() {
		final String PROPERTY_NAME = "JASYPT_ENCRYPTOR_PASSWORD";
		String password = (args.containsOption(PROPERTY_NAME)) ? args.getOptionValues(PROPERTY_NAME).get(0) : null;
		if (password == null) {
			password = System.getenv(PROPERTY_NAME);
		}
		if (password == null) {
			password = System.getProperty(PROPERTY_NAME);
		}
		if (password == null) {
			String msg = "[" + PROPERTY_NAME
					+ "] value must be provided as 1) program argument or 2) environment variable or 3) system property";
			throw new EncryptorError(msg);
		}
		StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
		enc.setPassword(password);
		return enc;
	}
}
