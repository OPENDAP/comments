<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>

	<!-- Access the bootstrap CSS like this, 
		Spring boot will handle the resource mapping automcatically -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />


	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />

</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring Boot</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">

		<div class="starter-template">
			<h1>Dataset Feedback form</h1>
			<h2>URL: ${feedback_form_info.url}</h2>
		</div>

        <form action="" method="post">
        	<p>
	        	<textarea style="font-family:courier;
	            	margin-left: 5px;
	                margin-right: 5px;
	                max-width: 50%;
	                background: rgba(255, 0, 0, 0.03);"
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
                    background: rgba(255, 0, 0, 0.03);"
                    id="comment"
                    name="comment"
                    rows="20"
                    cols="80">Add your comment here...</textarea>
            </p>
            <input type = "submit" value = "Submit" />
        </form>
        <!-- h2>Dataset information</h2 -->
        ${feedback_form_info.datasetInfo}

	</div>
	
	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>