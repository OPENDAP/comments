
/// The Model in the MVC example

package org.opendap.beans;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;

// This class comes from a Spring MVC example that uses JSP to build a
// HTML form.
public class FeedbackForm {
	private String url;
	private String datasetInfo;
	private ServletContext servletContext;

	/**
	 * @brief no-arg constructor.
	 * Defining the constructor with an argument suppresses the no-arg constructor,
	 * so I'm adding this in case Spring needs it.
	 */
	public FeedbackForm() {
		setUrl("");
		setDatasetInfo("");
	}



	/**
	 * @brief Build an instance with the value of url set.
	 * @param url The URL for the dataset that will be used to build the information 
	 * in the form.
	 */
	public FeedbackForm(String url) {
		setUrl(url);
		setDatasetInfo("");
	}

	public FeedbackForm(String url, String datasetInfo) {
		setUrl(url);
		setDatasetInfo(datasetInfo);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getDatasetInfo() {
		return datasetInfo;
	}

	public void setDatasetInfo(String datasetInfo) {
		this.datasetInfo = datasetInfo;
	}

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

}
   