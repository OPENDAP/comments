
package org.opendap.feedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// This Spring boot annotation is == @Configuration, @EnableAutoConfiguration, and @ComponentScan
// The scanBasePackages sets the component path, enabling the application to find its controller
// classes.
@SpringBootApplication(scanBasePackages = {"org.opendap"})
public class FeedbackApplication extends SpringBootServletInitializer {

	/// Use this when running as a jar
	public static void main(String[] args) {
		SpringApplication.run(FeedbackApplication.class, args);
	}
	
	/// Use this when running as a war file inside tomcat
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FeedbackApplication.class);
    }

}
