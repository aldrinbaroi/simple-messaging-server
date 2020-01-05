package net.baroi.messaging.server.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class SimpleMessagingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleMessagingServerApplication.class, args);
	}
}
