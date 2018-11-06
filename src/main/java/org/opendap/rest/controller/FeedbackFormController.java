
/// This is the controller in the MVC example.

package org.opendap.rest.controller;

import org.opendap.beans.FeedbackForm;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

@Controller
public class FeedbackFormController {

	@RequestMapping(value = "/feedback/form", method = RequestMethod.GET)
	public ModelAndView feedbackForm(@RequestParam(name = "url", required = false, defaultValue = "http://localhost:8080/opendap/") String url) {
		FeedbackForm ffb = new FeedbackForm();
		ffb.setUrl(url);
		System.out.println("Before calling ModelAndView");
		return new ModelAndView("feedback_form", "feedback_form_info", ffb);
	}
	
}
