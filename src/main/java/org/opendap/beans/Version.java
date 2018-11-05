package org.opendap.beans;

/**
 * @brief Response from the /feedback/version end point
 * @author jimg
 */
public class Version {

	private final String message;

	public Version(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
