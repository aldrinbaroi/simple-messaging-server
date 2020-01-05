/**
 * 
 */
package net.baroi.messaging.server.simple.request.rest;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aldrin Baroi
 *
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestStatus {

	private String status;
}
