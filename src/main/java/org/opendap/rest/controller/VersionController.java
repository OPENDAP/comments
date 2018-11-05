
package org.opendap.rest.controller;

import org.opendap.beans.Version;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Value;

/**
 * @brief Sprint boot Controller for the response to /feedback/version
 * 
 * @author jimg
 */
@Controller
public class VersionController {

	// Read feedback.version from the 'application.properties'. see
	// http://www.appsdeveloperblog.com/reading-application-properties-spring-boot/
	@Value("${feedback.version}")
	private String version_number;
	
	private static final String versionmsg = "Feedback, version %s";

	// This will inject an Environment object into this class. The Environment
	// object stores the info in application.properties as key:value pairs. I
	// used the @Value annotation. See the link above for more info.
	// @Autowired Environment env;
	
	@GetMapping("/feedback/version")
	@ResponseBody
	public Version versionResponse() {
		return new Version(String.format(versionmsg, version_number));
	}

}
