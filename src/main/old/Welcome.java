package org.opendap.beans;

/**
 * @brief Response from the /welcome/user end point
 * @author jimg
 */
public class Welcome {

	public Welcome(String message) {
		super();
		this.message = message;
	}

	private final String message;

	public String getMessage() {
		return message;
	}

}
