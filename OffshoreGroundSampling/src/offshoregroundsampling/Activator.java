package offshoregroundsampling;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ConfigurableApplicationContext springContext;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		// Start Spring Boot application
        springContext = SpringApplication.run(OffshoreSpringBootApplication.class);
       
        // Set the java.awt.headless property based on some condition
        String headless = System.getProperty("java.awt.headless");
        if (headless == null || headless.equals("true")) {
            // Set to false to ensure the GUI works
            System.setProperty("java.awt.headless", "false");
        }
         
	}

	public void stop(BundleContext bundleContext) throws Exception {
		 // Close Spring context
        if (springContext != null) {
            springContext.close();
        }
		Activator.context = null;
	}

}
