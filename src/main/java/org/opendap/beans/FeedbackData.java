
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
 
@Document(collection = "FeedbackData")
public class FeedbackData {
    @Id
    private String id;
    @Indexed
	private String url;
    
    // TODO Add: 'private String user;' once athentication works
    
	private String comment;

	public FeedbackData(String url1, String comment) {
		setUrl(url);
		setComment(comment);
	}

	public void setId(String id) {
        this.id = id;
    }
	
	public String getId() {
		return id;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

    @Override
    public String toString() {
        return "FeedbackData{" +
        		"id='" + id + '\'' +
                ",url='" + url + '\'' +
                ", comment=" + comment +
                '}';
    }

}
   