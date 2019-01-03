<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.opendap.servlet.ReqInfo" %>
<%@ page import="java.security.Principal" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page session="false" %>

<%
    String userId = null;
    Principal userPrinciple = request.getUserPrincipal();
    if (request.getRemoteUser() != null) {
        userId = request.getRemoteUser();

    } else if (userPrinciple != null) {
        userId = userPrinciple.getName();
    }
    /*
    String contextPath = request.getContextPath();
    String serviceUrl = ReqInfo.getServiceUrl(request);
    String request_url = request.getRequestURL().toString();
    String protocol= request.getProtocol();
    String server= request.getServerName();
    int port = request.getServerPort();
    String forwardRequestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
    */

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <!-- Access the bootstrap CSS like this,
    Spring boot will handle the resource mapping automatically -->
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    <spring:url value="/images/logo.png" var="logoImage" />
    <spring:url value="/css/contents.css" var="springCss" />
    <spring:url value="/feedback/database" var="DatabaseInspection" />
    <spring:url value="/index" var="home" />
    <spring:url value="/about" var="about" />

    <link href="${springCss}" rel="stylesheet" />

    <title>ESIP Dataset Feedback System</title>
</head>
<body>

<table width="100%"><tr>
    <td>
        <div style="text-align: left;">
            <a href="http://www.opendap.org"><img  alt="OPeNDAP Logo" src="${logoImage}"/></a>
        </div>
    </td>
    <td>
        <div style='float: right;vertical-align:top;font-size: x-small;'>
            <% if(userId==null || userId.length()==0){ %>
            <a href="/login"><b>login</b></a>

            <% } else { %>
            <a href="/profile"><b><%=userId%></b></a>
            <% } %>

        </div>
        <div class="medium" style="margin-top: 15px;text-align: center;">Welcome to the <em>ESIP Dataset Feedback</em> System</div>
        <div class="small" style="margin-top: 5px;text-align: center;">
            <span><a href="${home}">Home</a></span>
            <span><a href="${DatabaseInspection}">DB-Dump</a></span>
            <span><a href="${about}">About</a></span>
        </div>
    </td>
</tr></table>

<h2>Dataset Feedback</h2>
<hr size="1" noshade="noshade"/>
<div class="small">We've got a lot of stuff to say about all this noise...</div>
<div class="small">Let's put it here, shall we?</div>
<div style="margin-top: 10px;"><a href="/feedback/form">Find the Dataset Feedback Form here.</a></div>
<hr size="1" noshade="noshade"/>
<h3>OPeNDAP/ESIP Dataset Annotation System (@DUF_VERSION@)</h3>

</body>
</html>
