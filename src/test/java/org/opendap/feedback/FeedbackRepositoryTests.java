package org.opendap.feedback;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendap.beans.FeedbackData;
//import org.opendap.rest.controller.FeedbackFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackRepositoryTests {
    @Autowired
    private FeedbackRepository repository;
    
	private static final Logger log = LoggerFactory.getLogger(FeedbackRepositoryTests.class);

	// This user name is intended to not conflict with real names in a running mongo
	// instance.
	private final String user = "org.opendap.junit.test";

	private String url1 = "http;//junit.test.opendap.org/dap/junit_test_data_17.txt";
	private String url2 = "http;//junit.test.opendap.org/dap/data/junit_test_data_42.txt";
    
    @Before
    public void setUp() throws Exception {
		// this.repository.deleteAll();

		FeedbackData fdb1 = new FeedbackData(url1, user, "Comment1");
		FeedbackData fdb2 = new FeedbackData(url2, user, "Comment2");
        //save product, verify has ID value after save
        assertNull(fdb1.getId());
        assertNull(fdb2.getId());//null before save
        this.repository.save(fdb1);
        log.debug("FeedbackData 1: {}", fdb1.toString());
        
        this.repository.save(fdb2);
        log.debug("FeedbackData 2: {}", fdb2.toString());

        assertNotNull(fdb1.getId());
        assertNotNull(fdb2.getId());
    }

    @Test
    public void testFetchData(){
		// Test data retrieval
		FeedbackData fbd = repository.findFirstByUrl(url2);
        assertNotNull(fbd);
        assertEquals("Comment2", fbd.getComment());

		// Get all products, list should only have at least two
		List<FeedbackData> fdb_i = repository.findAll();
		assertTrue(fdb_i.size() >= 2);
    }

    @Test
    public void testDataUpdate(){
        /*Test update*/
		FeedbackData fdb = repository.findFirstByUrl(url2);
        assertNotNull(fdb);
        fdb.setComment("Stuff");
        repository.save(fdb);
		FeedbackData fdb_2 = repository.findFirstByUrl(url2);
        assertNotNull(fdb_2);
        assertEquals("Stuff", fdb_2.getComment());
    }

    @After
	public void tearDown() throws Exception {
		// Clean the collection of the test documents.
		// but do not run deleteAll since that will remove all the documents
		// from the collection.
		repository.deleteAll(repository.findByUser(user));
	}

}