
/// This is the controller in the MVC example.

package org.opendap.rest.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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



/*

    @Override
    public String getXmlBase(HttpServletRequest req){

        String requestUrl = ReqInfo.getRequestUrlPath(req);
        String xmlBase = Util.dropSuffixFrom(requestUrl, Pattern.compile(getCombinedRequestSuffixRegex()));
        _log.debug("getXmlBase(): @xml:base='{}'", xmlBase);
        return xmlBase;
    }
    */

/*
	public void sendNormativeRepresentation(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// String context = request.getContextPath();
		String requestedResourceId = org.opendap.servlet.ReqInfo.getLocalUrl(request);
		String xmlBase = getXmlBase(request);

		String resourceID = getResourceId(requestedResourceId, false);

		String collectionUrl = ReqInfo.getCollectionUrl(request);

		//QueryParameters qp = new QueryParameters(request);
		Request oreq = new Request(null,request);

		String constraintExpression = ReqInfo.getConstraintExpression(request);

		BesApi besApi = getBesApi();

		_log.debug("sendNormativeRepresentation() - Sending {} for dataset: {}",getServiceTitle(),resourceID);

		MediaType responseMediaType = getNormativeMediaType();

		// Stash the Media type in case there's an error. That way the error handler will know how to encode the error.
		RequestCache.put(OPeNDAPException.ERROR_RESPONSE_MEDIA_TYPE_KEY, responseMediaType);

		response.setContentType(responseMediaType.getMimeType());
		Version.setOpendapMimeHeaders(request, response, besApi);
		response.setHeader("Content-Description", getNormativeMediaType().getMimeType());
		// Commented because of a bug in the OPeNDAP C++ stuff...
		//response.setHeader("Content-Encoding", "plain");

		XMLOutputter xmlo = new XMLOutputter(Format.getPrettyFormat());
		Document ddx = new Document();
		besApi.getDDXDocument(resourceID,constraintExpression,"3.2",xmlBase,ddx);
		_log.debug(xmlo.outputString(ddx));

		OutputStream os = response.getOutputStream();
		ddx.getRootElement().setAttribute("dataset_id",resourceID);
		ddx.getRootElement().setAttribute("base", xmlBase, Namespace.XML_NAMESPACE);   // not needed - DMR has it

		String jsonLD = getDatasetJsonLD(collectionUrl,ddx);

		_log.error(jsonLD);

		String currentDir = System.getProperty("user.dir");
		_log.debug("Cached working directory: "+currentDir);


		String xslDir = new PathBuilder(_systemPath).pathAppend("xsl").toString();

		_log.debug("Changing working directory to "+ xslDir);
		System.setProperty("user.dir",xslDir);

		try {
			String xsltDocName = "dap2_ifh.xsl";

			// This Transformer class is an attempt at making the use of the saxon-9 API
			// a little simpler to use. It makes it easy to set input parameters for the stylesheet.
			// See the source code in opendap.xml.Transformer for more.
			Transformer transformer = new Transformer(xsltDocName);
			// transformer.setParameter("serviceContext", request.getServletContext().getContextPath()); // This is ServletAPI-3.0
			transformer.setParameter("serviceContext", request.getContextPath()); // This is ServletAPI-2.5 (Tomcat 6 stopped here)
			transformer.setParameter("docsService", oreq.getDocsServiceLocalID());
			transformer.setParameter("HyraxVersion", Version.getHyraxVersionString());
			transformer.setParameter("JsonLD", jsonLD);

			AuthenticationControls.setLoginParameters(transformer,request);

			// Transform the BES  showCatalog response into a HTML page for the browser
			transformer.transform(new JDOMSource(ddx), os);
			os.flush();
			_log.info("Sent {}", getServiceTitle());
		}
		finally {
			_log.debug("Restoring working directory to " + currentDir);
			System.setProperty("user.dir", currentDir);
		}
	}

*/
}
