package org.opendap.feedback;

import org.opendap.beans.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackRepositoryTests {
    @Autowired
    private FeedbackRepository feedbackRepository;

    private String url1 = "http;//test.opendap.org/dap/data/nc/fnoc1.nc";
    private String url2 = "http;//test.opendap.org/dap/data/nc/coads_climatology.nc";
    
    @Before
    public void setUp() throws Exception {
        FeedbackData fdb1= new FeedbackData(url1, "Comment1");
        FeedbackData fdb2= new FeedbackData(url2, "Comment2");
        //save product, verify has ID value after save
        assertNull(fdb1.getId());
        assertNull(fdb2.getId());//null before save
        this.feedbackRepository.save(fdb1);
        this.feedbackRepository.save(fdb2);
        assertNotNull(fdb1.getId());
        assertNotNull(fdb2.getId());
    }

    @Test
    public void testFetchData(){
        /*Test data retrieval*/
        FeedbackData fbd = feedbackRepository.findByUrl(url2);
        assertNotNull(fbd);
        assertEquals("Comment2", fbd.getComment());
        /*Get all products, list should only have two*/
        Iterable<FeedbackData> fdb_i = feedbackRepository.findAll();
        int count = 0;
        for(FeedbackData p : fdb_i){
            count++;
        }
        assertEquals(count, 2);
    }

    @Test
    public void testDataUpdate(){
        /*Test update*/
        FeedbackData fdb = feedbackRepository.findByUrl(url2);
        assertNotNull(fdb);
        fdb.setComment("Stuff");
        feedbackRepository.save(fdb);
        FeedbackData fdb_2= feedbackRepository.findByUrl(url2);
        assertNotNull(fdb_2);
        assertEquals("Stuff", fdb_2.getComment());
    }

    @After
    public void tearDown() throws Exception {
      this.feedbackRepository.deleteAll();
    }

}