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
public class SMSMessage implements Message {

	private String from;
	private String mobileNumber;
	private String mobileCarrierId;
	private String text;
}
