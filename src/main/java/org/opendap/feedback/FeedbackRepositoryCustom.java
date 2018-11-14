package org.opendap.feedback;

/// @see FeedbackRepositoryImpl
public interface FeedbackRepositoryCustom {

    long updateFeedbackData(String url, String comment);

}

