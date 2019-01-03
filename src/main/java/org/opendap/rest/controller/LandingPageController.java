package org.opendap.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import java.security.Principal;

@Controller
public class LandingPageController {

    private static final Logger log = LoggerFactory.getLogger(LandingPageController.class);



    // this mapping associates the jsp file "index.jsp" with the relative URL "/index" and with "/"
    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public void landingPage(){
        log.debug("landingPage() - Has arrived.");
    }


    /**
     * Return the index page, which has one link that returns the /login page. That page
     * does not actually exist, but given the configuration in ApplicationSecurity, will
     * be subject o OAuth2 authentication. That will authentication will redirect back to
     * the /feedback/form URL.
     *
     * @return A string that matches the index jsp page.
     */
    @RequestMapping("/login")
    public String login() {
        Principal principle = null;
        SecurityContext sc = SecurityContextHolder.getContext();
        if(sc!=null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth !=null)
                principle  = (java.security.Principal) auth.getPrincipal();
        }
        log.debug("feedbackForm: user: {}", principle);
        return "/login";
    }

    @RequestMapping("/callback")
    public String callback() {
        log.debug("redirecting to home page");
        Principal principle = null;
        SecurityContext sc = SecurityContextHolder.getContext();
        if(sc!=null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                log.debug((auth.getDetails().toString()));
            }

        }
        return "/index";
    }


}
