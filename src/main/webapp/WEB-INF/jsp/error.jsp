<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    String contextPath = request.getContextPath();
    String serviceUrl = ReqInfo.getServiceUrl(request);
    String request_url = request.getRequestURL().toString();
    String protocol= request.getProtocol();
    String server= request.getServerName();
    int port = request.getServerPort();
    String forwardRequestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");


%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <!-- Access the bootstrap CSS like this,
    Spring boot will handle the resource mapping automcatically -->
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    <spring:url value="/images/logo.png" var="logoImage" />
    <spring:url value="/css/contents.css" var="springCss" />

    <!-- @FIXME This should use the spring:url springCss but that is broken -->
    <link href="/css/contents.css" rel="stylesheet" />
    <title>Hyrax: OUCH!</title>
</head>
<body>
<div class=small style="border-width: 1px;border-color: black">logoImage: ${logoImage}; springCss: ${springCss}</div>

<table width="100%"><tr>
    <td>
        <div style="text-align: left;">
            <!-- @FIXME This should use the spring:url logoImage but that is broken -->
            <a href="http://www.opendap.org"><img  alt="OPeNDAP Logo" src="/images/logo.png"/></a>
        </div>
    </td>
    <td>
        <div style='float: right;vertical-align:top;font-size: x-small;'>
            uid: <b><%=userId%></b>
        </div>
        <div class="medium" style="margin-top: 15px;text-align: center;">Welcome to the <em>ESIP Dataset Feedback Error Page</em></div>
        <div class="small" style="text-align: center;">
            <span><a href="#">Home</a></span>
            <span><a href="${CommentDumper}">DB-Dump</a></span>
            <span><a href="#about">About</a></span>
        </div>
    </td>
</tr></table>

<h2>It seems that your request has been beset by daemons.</h2>
<hr size="1" noshade="noshade"/>

<div style="border-color: black;border-width: 1px">
    <div class="medium_bold" style="text-align: left">Request Information</div>
    <table class="small" style="width: 100%;">
        <tr>
            <td style=" text-align: right; font-weight: bold;">contextPath - </td>
            <td><pre><%=contextPath%></pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">serviceUrl - </td>
            <td><pre><%=serviceUrl%></pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">request_url - </td>
            <td><pre><%=request_url%></pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">protocol - </td>
            <td><pre><%=protocol%></pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;;">server - </td>
            <td><pre><%=server%></pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">port - </td>
            <td><pre><%=port%></pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">javax.servlet.forward.request_uri - </td>
            <td><pre><%=forwardRequestUri%></pre></td>
        </tr>
    </table>
</div>
<hr size="1" noshade="noshade"/>

<div style="border-color: black;border-width: 1px">
    <div class="medium_bold"  style="text-align: left">Error Information</div>
    <table class="small" style="width: 100%;">
        <tr>
            <td style=" text-align: right; font-weight: bold;">Date - </td>
            <td ><pre>${timestamp}</pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">Error Description - </td>
            <td><pre>${error}</pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">http status - </td>
            <td><pre>${status}</pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">error message - </td>
            <td><pre>${message}</pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;;">exception - </td>
            <td><pre>${exception}</pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">stack trace - </td>
            <td><pre>${trace}</pre></td>
        </tr>
        <tr>
            <td style=" text-align: right; font-weight: bold;">spring.profiles.active - </td>
            <td><pre><%=System.getProperty("spring.profiles.active")%></pre></td>
        </tr>
    </table>
</div>
<hr size="1" noshade="noshade"/>
<h3>OPeNDAP/ESIP Dataset Annotation System (@DUF_VERSION@)</h3>

</body>
</html>
