
package org.opendap.feedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This Spring boot annotation is == @Configuration, @EnableAutoConfiguration, and @ComponentScan
// The scanBasePackages sets the component path, enabling the application to find its controller
// classes.
@SpringBootApplication(scanBasePackages = {"org.opendap"})
public class FeedbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackApplication.class, args);
	}
	
}
