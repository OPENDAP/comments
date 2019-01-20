package org.opendap.feedback;

import com.mongodb.client.result.UpdateResult;
import org.opendap.beans.FeedbackData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @brief Implementation for the FeedbackRepositoryCustom interface
 * 
 * This interface provides a way to write data to the database.
 * 
 * @author jimg
 *
 */
public class FeedbackRepositoryImpl implements FeedbackRepositoryCustom {

	// private static final Logger log =
	// LoggerFactory.getLogger(FeedbackFormController.class);

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
}
