package org.opendap.feedback;

import org.opendap.beans.FeedbackData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * @brief Implementation for the FeedbackRepositoryCustom interface
 * 
 * This interface provides a way to write data to the database.
 * 
 * @author jimg
 *
 */
public class FeedbackRepositoryImpl implements FeedbackRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public long updateFeedbackData(String url, String comment) {

		Query query = new Query(Criteria.where("url").is(url));
		Update update = new Update();
		update.set("comment", comment);

		UpdateResult result = mongoTemplate.updateFirst(query, update, FeedbackData.class);

		if (result != null)
			return result.getModifiedCount();
			// FIXMEreturn result.getN();
		else
			return 0;

	}
}
