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
	public long updateFeedbackData(FeedbackData fbd) {

		Query query = new Query(Criteria.where("url").is(fbd.getUrl()));
		Update update = new Update();
		update.set("url", fbd.getDatasetComment());

		UpdateResult result = mongoTemplate.updateFirst(query, update, FeedbackData.class);

		if (result != null)
			return result.getModifiedCount();
		else
			return 0;

	}

	@Override
	public void writeFeedbackData(FeedbackData fbd) {
		FeedbackData result = mongoTemplate.insert(fbd);

		// TODO Throw some kind of exception if (result != null)
			
	}
}
