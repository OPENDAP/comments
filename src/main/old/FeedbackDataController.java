
/// This is the controller in the MVC example.

package org.opendap.rest.controller;

import org.opendap.beans.FeedbackData;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

@Controller
public class FeedbackDataController {

	@RequestMapping(value = "/feedback/add", method = RequestMethod.POST)
	public ModelAndView addFeedbackData(@ModelAttribute("FeedbackData") FeedbackData feedbackData, ModelMap model) {
		model.addAttribute("url", feedbackData.getUrl());
		model.addAttribute("datasetComment", feedbackData.getDatasetComment());

		return new ModelAndView("form_result", "form_info", feedbackData);
	}
	
}
