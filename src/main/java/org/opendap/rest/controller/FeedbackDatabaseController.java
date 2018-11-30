package org.opendap.rest.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.opendap.beans.FeedbackData;
import org.opendap.beans.FeedbackForm;
import org.opendap.feedback.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeedbackDatabaseController {

	private static final Logger log = LoggerFactory.getLogger(FeedbackDatabaseController.class);
	
	@Autowired
	private FeedbackRepository repository;

	List<FeedbackData> feedbackEntries;
	
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

		/***
		if (url != "" && user != "") {
			
		}
		else if (url != "") {
			
		}
		else if (user != "") {
			
		}
		else {
			feedbackEntries = repository.findAll();
		}
		***/
		
		feedbackEntries = repository.findAll();
		
		log.debug("feedbackDatabaseDump; Number of entries found: {}", feedbackEntries.size());
		
		// Args: name of the view to render, name of the model in that view and the model. jhrg 11/9/18
		return new ModelAndView("comment_dump", "feedback_entries", feedbackEntries);

		/***
		// Get the info response for the dataset. This shows that the form can be
		// customized
		// for each dataset.
		String urlContent = "";
		try {
			urlContent = getUrlText(url + ".info");
		} catch (Exception e) {
			log.error("Could not get info for the dataset url: {}", url);
			urlContent = "Could not get info for the dataset url";
		}
		
		FeedbackForm ffb = new FeedbackForm(url, urlContent);

		// Args: name of the view to render, name of the model in that view and the model. jhrg 11/9/18
		return new ModelAndView("feedback_form", "feedback_form_info", ffb);
		***/
	}
	
	/***
	@RequestMapping(value = "/feedback/form", method = RequestMethod.POST)
	public ModelAndView addFeedbackData(@ModelAttribute("FeedbackData") FeedbackData feedbackData) {

		log.debug("addFeedbackData; Saved URL: {}\n", getUrl());

		feedbackData.setUrl(getUrl());

		// Write/Update data to MongoDB here...
		FeedbackData existing = repository.findByUrlAndUser(getUrl(), feedbackData.getUser());
		if (existing != null) {
			existing.setComment(existing.getComment() + "\n" + feedbackData.getComment());
			repository.save(existing); // an update operation

			feedbackData = existing; // hack so the result form shows what was put in the DB
		} else {
			repository.save(feedbackData); // writes a new record
		}
		
		log.debug("addFeedbackData; FeedbackData from form: {}\n", feedbackData.toString());

		return new ModelAndView("form_result", "form_info", feedbackData);
	}
	***/
}
