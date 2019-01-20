
/// This is the controller in the MVC example.

package org.opendap.rest.controller;

import net.sf.saxon.s9api.SaxonApiException;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.JDOMSource;
import org.opendap.beans.FeedbackData;
import org.opendap.beans.FeedbackForm;
import org.opendap.feedback.FeedbackRepository;
import org.opendap.servlet.PathBuilder;
import org.opendap.servlet.ServiceStringEncoding;
import org.opendap.servlet.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

//import org.opendap.feedback.FeedbackRepositoryCustom;
//import org.opendap.feedback.FeedbackRepositoryImpl;
// import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.ui.ModelMap;

@Controller
public class FeedbackFormController {

	private static final Logger _log = LoggerFactory.getLogger(FeedbackFormController.class);


	@Autowired
	private FeedbackRepository repository;

    private ServletContext servletContext;


    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }


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
			_log.error("Could not get info for the dataset url: {}", url);
			urlContent = "Could not get info for the dataset url";
		}
        String form =null;
        try {
            form = getFormForDataset(url);



        } catch (IOException | JDOMException | SaxonApiException e) {
            e.printStackTrace();
        }


        FeedbackForm ffb = new FeedbackForm(url, form);

		// Args: name of the view to render, name of the model in that view and the model. jhrg 11/9/18
		return new ModelAndView("feedback_form", "feedback_form_info", ffb);
	}
	
	@RequestMapping(value = "/feedback/form", method = RequestMethod.POST)
	public ModelAndView addFeedbackData(@ModelAttribute("FeedbackData") FeedbackData feedbackData) {

		_log.debug("addFeedbackData; feedbackData.getUrl(): {}\n", feedbackData.getUrl());
		_log.debug("addFeedbackData; FeedbackFormController.getUrl(): {}\n", getUrl());

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
		_log.debug("addFeedbackData; FeedbackData from form: {}\n", feedbackData.toString());

		return new ModelAndView("form_result", "form_info", feedbackData);
	}


    public String getFormForDataset(String datasetUrl) throws JDOMException, IOException, SaxonApiException {


        String xsltDocName = "dap2_feedback_form.xsl";
        File xsltFile;

        Resource xsltFileResource = new ClassPathResource(xsltDocName);
        if(xsltFileResource.exists()){
// @FIXME This never happens as the previous call to new ClassPathResource(xsltDocName) never actually finds the file.
            xsltFile = xsltFileResource.getFile();
        }
        else {
            // @FIXME this is a hack for development and MUST be replaced.
            String xslDir = "/Users/ndp/OPeNDAP/hyrax/feedback/src/main/resources/static/xsl";
            String xsltTransformFileName = PathBuilder.pathConcat(xslDir,xsltDocName);
            xsltFile =  new File(xsltTransformFileName);
        }

		_log.debug("getFormForDataset() - Retrieving DDX for dataset: {}",datasetUrl);

		XMLOutputter xmlo = new XMLOutputter(Format.getPrettyFormat());
		Document ddx = Transformer.getXMLDoc(datasetUrl + ".ddx");
		_log.debug(xmlo.outputString(ddx));

        try {
            // This Transformer class is an attempt at making the use of the saxon-9 API
            // a little simpler to use. It makes it easy to set input parameters for the stylesheet.
            // See the source code in opendap.xml.Transformer for more.
            Transformer transformer = new Transformer(xsltFile);
            // transformer.setParameter("serviceContext", request.getServletContext().getContextPath()); // This is ServletAPI-3.0
            transformer.setParameter("serviceContext", ""); // This is ServletAPI-2.5 (Tomcat 6 stopped here)
            transformer.setParameter("docsService", "");
            transformer.setParameter("HyraxVersion", "DUF");

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            // Transform the BES  showCatalog response into a HTML page for the browser
            transformer.transform(new JDOMSource(ddx), os);
            _log.debug("Transformed {} result size is {} bytes", datasetUrl, os.size());
            ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
            String s = new String(os.toByteArray(),ServiceStringEncoding.getCharset());
            return s;
        }
        finally {
            //_log.debug("Restoring working directory to " + originalDir);
           // System.setProperty("user.dir", originalDir);
            _log.debug("getFormForDataset() - fini");
        }
	}



}
