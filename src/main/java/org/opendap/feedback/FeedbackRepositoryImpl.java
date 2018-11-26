package org.opendap.feedback;

import org.opendap.beans.FeedbackData;
import org.opendap.rest.controller.FeedbackFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(FeedbackFormController.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public long updateFeedbackData(FeedbackData fbd) {

		Query query = new Query(Criteria.where("url").is(fbd.getUrl()));
		Update update = new Update();
		update.set("url", fbd.getComment());

		UpdateResult result = mongoTemplate.updateFirst(query, update, FeedbackData.class);

		if (result != null)
			return result.getModifiedCount();
		else
			return 0;

	}

	@Override
	public void writeFeedbackData(FeedbackData fbd) {
		FeedbackData result = mongoTemplate.insert(fbd);
		
		log.debug("FeedbackData inserted: {}", result.toString());
		
		// TODO Throw some kind of exception if (result == null)
			
	}
}
