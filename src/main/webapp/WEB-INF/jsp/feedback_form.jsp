<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>

	<!-- Access the bootstrap CSS like this, 
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />


    <spring:url value="/css/contents.css" var="springCss" />
    <spring:url value="/css/treeView.css" var="treeViewCss" />
    <spring:url value="/images/logo.png" var="logoImage" />
    <spring:url value="/comment_dump" var="CommentDumper" />
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
        <div class="medium" style="text-align: center;">Welcome to the <em>ESIP Dataset Feedback Form</em></div>
        <div class="small" style="text-align: center;">
            <span><a href="#">Home</a></span>
            <span><a href="${CommentDumper}">DB-Dump</a></span>
            <span><a href="#about">About</a></span>
        </div>
    </td>
</tr></table>


<h2>Dataset URL: ${feedback_form_info.url}</h2>


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

    <div>
        Dataset information from the origin server:
        <div style="margin-top: 5px;margin-left: 10%;width: 80%;border: 2px solid;">
            ${feedback_form_info.datasetInfo}
        </div>
    </div>

<hr size="1" noshade="noshade"/>
<h3>OPeNDAP/ESIP Dataset Annotation System (@DUF_VERSION@)</h3>


<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>