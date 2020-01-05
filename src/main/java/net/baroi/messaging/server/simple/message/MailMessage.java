package net.baroi.messaging.server.simple.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Aldrin Baroi
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailMessage implements Message {

	private String from;
	private String to;
	private String subject;
	private String text;
}
