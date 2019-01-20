<%@ page import="java.security.Principal" %>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String userId = null;
    Principal userPrinciple = request.getUserPrincipal();
    if (request.getRemoteUser() != null) {
        userId = request.getRemoteUser();

    } else if (userPrinciple != null) {
        userId = userPrinciple.getName();
    }

%>
<html lang="en">
<head>

	<!-- Access the bootstrap CSS like this, 
		Spring boot will handle the resource mapping automatically -->
	<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

    <spring:url value="/css/contents.css" var="springCss" />
    <!-- spring:url value="/css/treeView.css" var="treeViewCss" / -->
    <spring:url value="/images/logo.png" var="logoImage" />
    <spring:url value="/feedback/database" var="DatabaseInspection" />

	<link href="${springCss}" rel="stylesheet" />

</head>
<body>

<!-- BANNER -->
<table width="100%"><tr>
<td>
    <div style="text-align: left;">
        <a href="http://www.opendap.org"><img  alt="OPeNDAP Logo" src="${logoImage}"/></a>
    </div>
</td>
    <td>
        <div style='float: right;vertical-align:top;font-size: x-small;'>
            uid: <b><%=userId%></b>
        </div>
        <div class="medium" style="margin-top: 15px;text-align: center;">Welcome to the <em>ESIP Dataset Feedback Form</em></div>
        <div class="small" style="margin-top: 5px;text-align: center;">
            <span><a href="/">Home</a></span>
            <span><a href="${DatabaseInspection}">DB-Dump</a></span>
            <span><a href="/about">About</a></span>
        </div>
    </td>
</tr></table>


<!-- Main Page Body -->

<h2>Welcome to the <em>ESIP Dataset Feedback Form</em></h2>
<div class="medium">Here you can provide feedback about the dataset:</div>
 <div class="large" style="margin-left: 10px;margin-top: 10px">${feedback_form_info.url}</div>
<h2>Feedback Form</h2>

	<div class="container">
        <form action="" method="post">
        	<p>
	        	<textarea style="font-family:courier;
	            	margin-left: 5px;
	                margin-right: 5px;
	                max-width: 50%;
                    background: rgba(82, 124, 193, 0.03);"
	                id="user"
	                name="user"
	                rows="1"
	                cols="80">Username</textarea>
        	</p>
            <p>
            	<textarea style="font-family:courier;
                	margin-left: 5px;
                    margin-right: 5px;
                    max-width: 99%;
                    width: 99%;
                    background: rgba(82, 124, 193, 0.03);"
                    id="comment"
                    name="comment"
                    rows="20"
                    cols="80">Add your comment here...</textarea>
            </p>
            <input type = "submit" value = "Submit" />
        </form>
    </div>
    <hr size="1" noshade="noshade"/>

    <h3>DAP2 ".info" response for: ${feedback_form_info.url}</h3>
    <!-- div style="margin-top: 5px;margin-left: 10%;width: 80%;border: 10px groove  rgba(82, 124, 193, 1.0);">
        <div style="margin: 0px;border: 10px groove  rgba(82, 124, 193, .8);">
            .
        </div >
    </div -->
<div style="margin: 5px;border: 10px double darkred;border-radius: 25px;">
    ${feedback_form_info.datasetInfo}
</div>

<hr size="1" noshade="noshade"/>
<h3>OPeNDAP/ESIP Dataset Annotation System (@DUF_VERSION@)</h3>


<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>