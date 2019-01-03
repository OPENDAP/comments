package org.opendap.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LandingPageController {

    private static final Logger log = LoggerFactory.getLogger(LandingPageController.class);

    // this mapping associates the jsp file "index.jsp" with the relative URL "/index" and with "/"
    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public void landingPage(){

        log.debug("landingPage() - Has arrived.");
    }


}
