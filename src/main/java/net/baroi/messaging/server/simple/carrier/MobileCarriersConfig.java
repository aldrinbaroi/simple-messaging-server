/**
 * 
 */
package net.baroi.messaging.server.simple.carrier;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author Aldrin Baroi
 *
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "simple-messaging-service")
@Data
public class MobileCarriersConfig {

	private Map<String, MobileCarrier> carriers = new HashMap<>();

	@Data
	public static class MobileCarrier {

		private String id;
		private String name;
		private String smsDomain;
		private String mmsDomain;
	}

	/**
	 * 
	 * @param carrierId
	 * @return
	 * @throws MobileCarrierNotSetupException
	 */
	public MobileCarrier getCarrier(String carrierId) throws MobileCarrierNotSetupException {
		if (carriers.containsKey(carrierId)) {
			return carriers.get(carrierId);
		}
		throw new MobileCarrierNotSetupException("Carrier [" + carrierId + "] not setup.");
	}

	/**
	 * 
	 * @param carrierId
	 * @return
	 * @throws MobileCarrierNotSetupException
	 * @throws MobileCarrierSmsDoaminNotSetupException
	 */
	public String getCarrierSmsDomain(String carrierId)
			throws MobileCarrierNotSetupException, MobileCarrierSmsDoaminNotSetupException {
		if (carriers.containsKey(carrierId)) {
			MobileCarrier carrier = carriers.get(carrierId);
			String smsDomain = carrier.getSmsDomain();
			if ("NOT_SETUP".equals(smsDomain))
				throw new MobileCarrierSmsDoaminNotSetupException(
						"SMS doamin not setup or not supported by carrier [" + carrierId + "]");
			else
				return smsDomain;
		}
		throw new MobileCarrierNotSetupException("Carrier [" + carrierId + "] not setup.");
	}

	/**
	 * 
	 * @param carrierId
	 * @return
	 * @throws MobileCarrierNotSetupException
	 * @throws MobileCarrierMmsDoaminNotSetupException
	 */
	public String getCarrierMmsDomain(String carrierId)
			throws MobileCarrierNotSetupException, MobileCarrierMmsDoaminNotSetupException {
		if (carriers.containsKey(carrierId)) {
			MobileCarrier carrier = carriers.get(carrierId);
			String mmsDomain = carrier.getMmsDomain();
			if ("NOT_SETUP".equals(mmsDomain))
				throw new MobileCarrierMmsDoaminNotSetupException(
						"MMS doamin not setup or not supported by carrier [" + carrierId + "]");
			else
				return mmsDomain;
		}
		throw new MobileCarrierNotSetupException("Carrier [" + carrierId + "] not setup.");
	}

	/**
	 * 
	 * @param mobileCarrierId
	 * @param mobileNumber
	 * @return
	 * @throws MobileCarrierNotSetupException
	 * @throws MobileCarrierSmsDoaminNotSetupException
	 */
	public String getSmsAddress(String mobileCarrierId, String mobileNumber)
			throws MobileCarrierNotSetupException, MobileCarrierSmsDoaminNotSetupException {
		return mobileNumber + "@" + getCarrierSmsDomain(mobileCarrierId);
	}

	/**
	 * 
	 * @param mobileCarrierId
	 * @param mobileNumber
	 * @return
	 * @throws MobileCarrierNotSetupException
	 * @throws MobileCarrierMmsDoaminNotSetupException
	 */
	public String getMmsAddress(String mobileCarrierId, String mobileNumber)
			throws MobileCarrierNotSetupException, MobileCarrierMmsDoaminNotSetupException {
		return mobileNumber + "@" + getCarrierMmsDomain(mobileCarrierId);
	}
}
