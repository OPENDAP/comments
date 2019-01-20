
package org.opendap.rest.controller;

import org.opendap.beans.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.beans.factory.annotation.Value;

/**
 * @brief Sprint boot Controller for the response to /feedback/version
 * 
 * @author jimg
 */
@Controller
public class VersionController {

	// Added to test SLF4j.
	private static final Logger log = LoggerFactory.getLogger(FeedbackFormController.class);
	private static final String version_key = "feedback.version";
	
	// Read feedback.version from the 'application.properties'. see
	// http://www.appsdeveloperblog.com/reading-application-properties-spring-boot/
	// NB: JUnit4 does not support @Value
	// @Value("${feedback.version}")
	// private String version_number;
	
	private static final String version_msg = "Feedback, version %s";

	// This will inject an Environment object into this class. The Environment
	// object stores the info in application.properties as key:value pairs. I
	// used the @Value annotation. See the link above for more info.
	@Autowired 
	private Environment env;
	
	@GetMapping("/feedback/version")
	@ResponseBody
	public Version versionResponse() {
		String version_number = env.getProperty(version_key);
		log.debug("About to return version info, version: {}", version_number);	// Here to test slf4j
		return new Version(String.format(version_msg, version_number));
	}

}
