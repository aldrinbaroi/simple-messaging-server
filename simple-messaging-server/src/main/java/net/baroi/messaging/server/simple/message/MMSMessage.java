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
public class MMSMessage implements Message {

	private String from;
	private String mobileNumber;
	private String mobileCarrierId;
	private String text;
	private String mediaName;
	/**
	 * Media MIME type
	 */
	private String mediaType;
	/**
	 * Base64 encoded media byte stream
	 */
	private String mediaContent;
}
