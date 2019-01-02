<%@ page import="java.security.Principal" %>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<spring:url value="/css/contents.css" var="springCss" />
	<spring:url value="/css/treeView.css" var="treeViewCss" />
	<spring:url value="/images/logo.png" var="logoImage" />


	<spring:url value="/database" var="CommentDumper" />

	<link href="${springCss}" rel="stylesheet" />

</head>
<body>
<div class=small style="border-width: 1px;border-color: black">logoImage: ${logoImage}; springCss: ${springCss}</div>

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
		<div class="medium" style="margin-top: 15px;text-align: center;">Welcome to the <em>ESIP Dataset Feedback Database Dumper</em></div>
        <div class="small" style="margin-top: 5px;text-align: center;">
            <span><a href="/">Home</a></span>
            <!-- @FIXME This should use the spring:url CommentDumper but that is broken -->
            <span><a href="/feedback/database">DB-Dump</a></span>
            <span><a href="/about">About</a></span>
        </div>
	</td>
</tr></table>


<!-- Main Page Body -->

<h1>Dataset Feedback Database Entries</h1>
<hr size="1" noshade="noshade"/>

<table width="100%">
<th>user id</th><th>dataset url</th><th>Comments</th>
<c:forEach items="${feedback_entries}" var="entry">
	<tr>
		<td class="small" style="text-align: center;vertical-align: top"><pre><c:out value="${entry.user}"/></pre></td>
		<td class="small" style="text-align: center;vertical-align: top;"><pre><a href="<c:out value="${entry.url}.html"/>"><c:out value="${entry.url}"/></a></pre></td>
		<td class="small" style="text-align: left;vertical-align: top;margin-left: 25px"><pre><c:out value="${entry.comment}"/></pre></td>
	</tr>
</c:forEach>
</table>


<hr size="1" noshade="noshade"/>
<h3>OPeNDAP/ESIP Dataset Annotation System (@DUF_VERSION@)</h3>

<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>