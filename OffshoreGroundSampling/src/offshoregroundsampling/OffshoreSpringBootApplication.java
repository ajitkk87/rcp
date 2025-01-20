package offshoregroundsampling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "offshoregroundsampling", exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class OffshoreSpringBootApplication {

    public static void main(String[] args) {
    	SpringApplication.run(OffshoreSpringBootApplication.class, args);
    }
}