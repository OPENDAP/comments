
/// The Model in the MVC example

package org.opendap.beans;

// This class comes from a Spring MVC example that uses JSP to build a 
// HTML form.
public class FeedbackForm {
	private String url;
	
	/**
	 * @brief no-arg constructor.
	 * Defining the constructor with an argument suppresses the no-arg constructor,
	 * so I'm adding this in case Spring needs it.
	 */
	public FeedbackForm() {
		this.url = "";
	}
	
	/**
	 * @brief Build an instance with the value of url set.
	 * @param url The URL for the dataset that will be used to build the information 
	 * in the form.
	 */
	public FeedbackForm(String url) {
		this.url = url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
   