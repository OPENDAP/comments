<!DOCTYPE html>
<html>
<head>
</head>
<body>
<!-- /login is the default supported by Spring Boot. See class HttpSecurity::oauth2Login().
	From the comments there:The default configuration provides an auto-generated 
	login page at /login and redirects to /login?error when an authentication error
	occurs. The login page will display each of the clients with a link that is 
	capable of initiating the 'authentication flow.' jhrg 12/31/18 -->
<a href="/login">Login with Google</a>
</body>
</html>