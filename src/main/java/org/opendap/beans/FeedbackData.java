
/// The Model in the MVC example

package org.opendap.beans;

// This class comes from a Spring MVC example that uses JSP to build a 
// HTML form.
public class FeedbackData {
	private String url;
	private String datasetComment;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getDatasetComment() {
		return datasetComment;
	}

	public void setDatasetComment(String datasetComment) {
		this.datasetComment = datasetComment;
	}

}
   