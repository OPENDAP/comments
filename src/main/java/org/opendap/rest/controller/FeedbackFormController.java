
/// This is the controller in the MVC example.

package org.opendap.rest.controller;

import org.opendap.beans.FeedbackData;
import org.opendap.beans.FeedbackForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.ui.ModelMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class FeedbackFormController {

	private static final Logger log = LoggerFactory.getLogger(FeedbackFormController.class);
	
	private String url;
	 
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@RequestMapping(value = "/feedback/form", method = RequestMethod.GET)
	public ModelAndView feedbackForm(@RequestParam(name = "url", required = false, defaultValue = "http://localhost:8080/opendap/") String url) {
		setUrl(url);	// Save for later
		FeedbackForm ffb = new FeedbackForm(url);
		
		// Args: name of the view to render, name of the model in that view and the model. jhrg 11/9/18
		return new ModelAndView("feedback_form", "feedback_form_info", ffb);
	}
	
	@RequestMapping(value = "/feedback/form", method = RequestMethod.POST)
	public ModelAndView addFeedbackData(@ModelAttribute("FeedbackData") FeedbackData feedbackData, ModelMap model) {
		log.debug("in addFeedbackData URL: {}\n", url);
		model.addAttribute("url", getUrl());
		model.addAttribute("datasetComment", feedbackData.getDatasetComment());

		return new ModelAndView("form_result", "form_info", feedbackData);
	}
	
}
