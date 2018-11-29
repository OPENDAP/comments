
/// The Model in the MVC example

package org.opendap.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * This is the 'model' for the data in the database. The annotation
 * @Document is not needed. It provides a way to name the MongoDB 
 * collection something other than the class name (but in all lower case).
 * 
 * @author jimg
 */
 
@Document(collection = "FeedbackData")
public class FeedbackData {
	private static final Logger log = LoggerFactory.getLogger(FeedbackData.class);

    @Id
    private String id;
    @Indexed
	private String url;
	@Indexed
	private String user;
    
	private String comment;

	public FeedbackData(String url, String user, String comment) {
		setUrl(url);
		setUser(user);
		setComment(comment);

		log.debug("FeedbackData ctor: {}", this.toString());
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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
				",user='" + user + '\'' +
                ", comment=" + comment +
                '}';
    }

}
   