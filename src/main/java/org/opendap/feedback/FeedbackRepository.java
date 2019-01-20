
package org.opendap.feedback;

import org.opendap.beans.FeedbackData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

// No need to provide an implementation, just one interface, and you have CRUD, thanks Spring Data
// can add 'extends, FeedbackRepositoryCustom'
public interface FeedbackRepository extends MongoRepository<FeedbackData, Long> {

	/// We might want to find just the first comment.
	FeedbackData findFirstByUrl(String url);
	
	/// There can be many comments about a given dataset, each by different users.
	List<FeedbackData> findByUrl(String url);

	/// One User can comment on many datasets.
	List<FeedbackData> findByUser(String user);

	/// There can be only one comment per Url for each User.
	FeedbackData findByUrlAndUser(String url, String user);

	/// Using a regex on the URL could be interesting.
    @Query("{domain: { $regex: ?0 } })")
    List<FeedbackData> findCustomByRegExUrl(String url);
}
