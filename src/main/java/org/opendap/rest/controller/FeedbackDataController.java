
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

	@RequestMapping(value = "/feedback/form", method = RequestMethod.GET)
	public ModelAndView feedbackForm() {
		return new ModelAndView("feedback", "command", new FeedbackData());
	}

	@RequestMapping(value = "/feedback/add", method = RequestMethod.POST)
	public String addFeedbackData(@ModelAttribute("SpringWeb") FeedbackData feedbackData, ModelMap model) {
		model.addAttribute("name", feedbackData.getName());
		model.addAttribute("age", feedbackData.getAge());
		model.addAttribute("id", feedbackData.getId());

		return "result";
	}
	
}
