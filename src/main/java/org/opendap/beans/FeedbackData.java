
/// The Model in the MVC example

package org.opendap.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @brief Holds the data read from the form for a given dataset
 * 
 * The Dataset is identified using a URL. Currently, this supports only
 * text commentary, but a more sophisticated web interface could support
 * information about every variable/attribute in the dataset.
 * 
 * This is the 'model' for the data in the database.
 * 
 * @author jimg
 */
 
@Document(collection = "feedback")
public class FeedbackData {
    @Id
    private String id;
    @Indexed
	private String url;
    
    // TODO private String user;
    
	private String comment;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getDatasetComment() {
		return comment;
	}

	public void setDatasetComment(String datasetComment) {
		this.comment = datasetComment;
	}

    @Override
    public String toString() {
        return "FeedbackData{" +
                "Url='" + url + '\'' +
                ", datasetComment=" + comment +
                '}';
    }

}
   