package offshoregroundsampling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "offshoregroundsampling", exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class EmbeddedSpringBootApplication {

    public static void main(String[] args) {
    	ConfigurableApplicationContext springContext =  SpringApplication.run(EmbeddedSpringBootApplication.class, args);
    }
}