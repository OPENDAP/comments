
/// This is the controller in the MVC example.

package org.opendap.rest.controller;

import org.opendap.beans.FeedbackData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeedbackDataController {

	@RequestMapping(value = "/feedback/add", method = RequestMethod.POST)
	public ModelAndView addFeedbackData(@ModelAttribute("FeedbackData") FeedbackData feedbackData, ModelMap model) {
		model.addAttribute("url", feedbackData.getUrl());
		model.addAttribute("comment", feedbackData.getComment());

		return new ModelAndView("form_result", "form_info", feedbackData);
	}
	
}
