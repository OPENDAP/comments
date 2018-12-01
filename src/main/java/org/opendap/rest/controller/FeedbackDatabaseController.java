package org.opendap.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.opendap.beans.FeedbackData;
import org.opendap.feedback.FeedbackRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeedbackDatabaseController {

	private static final Logger log = LoggerFactory.getLogger(FeedbackDatabaseController.class);
	
	@Autowired
	private FeedbackRepository repository;
	
	/**
	 * @brief dump information from the database
	 * 
	 * If either of the optional parameters 'url' or 'user' are given, limit
	 * the database entries show to those that the given values. If those 
	 * parameters are empty, show all values.
	 * 
	 * @param url
	 * @param user
	 * @return A ModelAndView that triggers a JSP page response
	 */
	@RequestMapping(value = "/feedback/database", method = RequestMethod.GET)
	public ModelAndView feedbackDatabaseDump(
			@RequestParam(name = "url", required = false, defaultValue = "") String url,
			@RequestParam(name = "user", required = false, defaultValue = "") String user) {

		List<FeedbackData> feedbackEntries = null;

		if (!url.isEmpty() && !user.isEmpty()) {
			FeedbackData fbd = repository.findByUrlAndUser(url, user);
			feedbackEntries = new ArrayList<FeedbackData>();
			feedbackEntries.add(fbd);
		}
		else if (!url.isEmpty()) {
			feedbackEntries = repository.findByUrl(url);	
		}
		else if (!user.isEmpty()) {
			feedbackEntries = repository.findByUser(user);
		}
		else {
			feedbackEntries = repository.findAll();
		}
		
		// if nothing was found, use an empty list.
		if (feedbackEntries == null) {
			feedbackEntries = new ArrayList<FeedbackData>();
		}
		
		log.debug("feedbackDatabaseDump; Number of entries found: {}", feedbackEntries.size());
		
		// Args: name of the view to render, name of the model in that view and the model. jhrg 11/9/18
		return new ModelAndView("comment_dump", "feedback_entries", feedbackEntries);
	}
}
