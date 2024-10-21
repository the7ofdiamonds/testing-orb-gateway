package tech.orbfin.api.gateway;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest(classes = GatewayApplication.class) // Specify the main application class
public class GatewayApplicationTests extends AbstractTestNGSpringContextTests {

	@Test
	public void contextLoads() {
		// This will pass if the context loads successfully
	}
}