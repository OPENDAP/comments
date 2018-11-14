
package org.opendap.feedback;

import org.opendap.beans.FeedbackData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

// No need to provide an implementation, just one interface, and you have CRUD, thanks Spring Data
public interface FeedbackRepository extends MongoRepository<FeedbackData, Long>, FeedbackRepositoryCustom {

	FeedbackData findFirstByUrl(String url);

	// TODO FeedbackData findByDomainAndUser(String url, String user);

    //Supports native JSON query string
	
	/*
	 * maybe these will be useful someday? jhrg

    @Query("{domain:'?0'}")
    FeedbackData findCustomByDomain(String domain);

    @Query("{domain: { $regex: ?0 } })")
    List<FeedbackData> findCustomByRegExDomain(String domain);
	*/
}
