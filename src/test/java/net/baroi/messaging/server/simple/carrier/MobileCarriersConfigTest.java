package net.baroi.messaging.server.simple.carrier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.baroi.messaging.server.simple.carrier.MobileCarrierMmsDoaminNotSetupException;
import net.baroi.messaging.server.simple.carrier.MobileCarrierNotSetupException;
import net.baroi.messaging.server.simple.carrier.MobileCarrierSmsDoaminNotSetupException;
import net.baroi.messaging.server.simple.carrier.MobileCarriersConfig;

@SpringBootTest
class MobileCarriersConfigTest {

	@Autowired
	private MobileCarriersConfig carriers;

	@Test
	void testGetCarriers() {
		assertThat(carriers.getCarriers().keySet().size()).isEqualTo(19);
	}

	@Test
	void testGetSmsAddress() {
		try {
			assertThat(carriers.getSmsAddress("verizon", "818212404")).isEqualTo("818212404@vtext.com");
		} catch (MobileCarrierNotSetupException | MobileCarrierSmsDoaminNotSetupException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetMmsAddress() {
		try {
			assertThat(carriers.getMmsAddress("verizon", "818212404")).isEqualTo("818212404@vzwpix.com");
		} catch (MobileCarrierNotSetupException | MobileCarrierMmsDoaminNotSetupException e) {
			fail(e.getMessage());
		}
	}
}
