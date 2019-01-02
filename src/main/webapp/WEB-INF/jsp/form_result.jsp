<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

	<!-- Access the bootstrap Css like this, 
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<spring:url value="/css/contents.css" var="springCss" />
	<spring:url value="/css/treeView.css" var="treeViewCss" />
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
		<div class="medium" style="text-align: left;">Your feedback has been submitted.</div>
		<div class="medium" style="text-align: left;">Thank You!</div>

        <div class="small" style="margin-top: 5px;text-align: center;">
            <span><a href="/">Home</a></span>
            <!-- @FIXME This should use the spring:url CommentDumper but that is broken -->
            <span><a href="${DatabaseInspection}">DB-Dump</a></span>
            <span><a href="/about">About</a></span>
        </div>
	</td>
</tr></table>

<h1>Dataset Feedback Submission Summary</h1>
Here is the summery of all of your comments for the dataset.
<hr size="1" noshade="noshade"/>
<table width="100%">
    <tr>
        <td width="15%" style="text-align: right;vertical-align: top;">
            <span class="small_bold">dataset url:</span>
        </td>
        <td><span class="small"> ${form_info.url}</span></td>
    </tr>
    <tr>
        <td style="text-align: right;"><span class="small_bold">user id:</span></td>
        <td><span class="small"> ${form_info.user}</span></td>
    </tr>
    <tr>
        <td style="text-align: right;vertical-align: top;"><span class="small_bold">comments:</span></td>
        <td><pre>${form_info.comment}</pre></td>
    </tr>
</table>
<hr size="1" noshade="noshade"/>
<h3>OPeNDAP/ESIP Dataset Annotation System (@DUF_VERSION@)</h3>

<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>