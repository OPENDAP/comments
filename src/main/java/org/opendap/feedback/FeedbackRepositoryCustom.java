package org.opendap.feedback;

import org.opendap.beans.FeedbackData;

/**
 * @brief modify the comment database
 * 
 * @author jimg
 * @see FeedbackRepositoryImpl
 */
public interface FeedbackRepositoryCustom {

	/// Update an existing comment entry
    long updateFeedbackData(FeedbackData fbd);
    
}

