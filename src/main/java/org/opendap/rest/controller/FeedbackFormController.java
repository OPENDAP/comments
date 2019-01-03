
/// This is the controller in the MVC example.

package org.opendap.rest.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.opendap.beans.FeedbackData;
import org.opendap.beans.FeedbackForm;
import org.opendap.feedback.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.opendap.feedback.FeedbackRepositoryCustom;
//import org.opendap.feedback.FeedbackRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.ui.ModelMap;

@Controller
public class FeedbackFormController {

	private static final Logger log = LoggerFactory.getLogger(FeedbackFormController.class);
	
	@Autowired
	private FeedbackRepository repository;

	private String url;
	 
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	String getUrlText(String url) throws Exception {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return response.toString();
	}

	@RequestMapping(value = "/feedback/form", method = RequestMethod.GET)
	public ModelAndView feedbackForm(
			@RequestParam(name = "url", required = false, defaultValue = "http://test.opendap.org/opendap/data/nc/coads_climatology.nc") String url) {
		setUrl(url);	// Save for later

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
	}
	
	@RequestMapping(value = "/feedback/form", method = RequestMethod.POST)
	public ModelAndView addFeedbackData(@ModelAttribute("FeedbackData") FeedbackData feedbackData) {

		log.debug("addFeedbackData; feedbackData.getUrl(): {}\n", feedbackData.getUrl());
		log.debug("addFeedbackData; FeedbackFormController.getUrl(): {}\n", getUrl());

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
	
}
