
# Simple Spring Boot web application

# Builds with maven
use _./mvnw package_ to build

and _java -jar target/Feedback-0.0.1-SNAPSHOT.jar_ to run.

# Builds with gradle
use _gradle build_ to build

and _gradle bootRun_ to run

The _bootRun_ task runs the web app in an embedded instance of Tomcat. The _curl_ calls in **Testing** work for both.

# Testing
There are two API calls, one that uses GET and one that uses POST

## Test the GET call

use curl to access the URL _http://localhost:8083/feedback/version_

The returned 'form' can be altered using the query string parameter _url_
using

_curl 'http://localhost:8083/feedback/form?url=http://test.opendap.org/opendap'_

## Test using tomcat

Install the war file (_gradle war_, then look in _build/libs_) in Tomcat (v. 8.x) and
use the same URLs as above except add the basename of the war file and change the port like so,
(making edits as appropriate if the basename or port numebr are different):

_curl 'http://localhost:8080/Feedback-0.0.1-SNAPSHOT/feedback/form?url=http://test.opendap.org/opendap'_

## Test the POST call - not in the code now 

use curl to post the information for a StudentRegistration bean. The (json) info looks like:

{
    "name": "James",
    "age": 102
}

using the _-d @<filename>_ option of curl and the _-H_ option to add the _Content-Type:_ header value _application/json_. The post data is in _src/test/resources/post_doc_1.json_

*curl -H 'Content-Type: application/json' -d @src/test/resources/post\_doc\_1.json http://localhost:8083/register/student*

# TODO

- [x] Move from maven to gradle  
  Easy to do: _gradle init_ makes the needed stuff, edited to update and added spring-boot plugin.
- [ ] Compare this to a Spring MVC web application
- [x] Design Web API  
  See below
- [x] Rename to _feedback_
  
## Web API Entry points:
1. version  
   GET: Return the version of the service. This will serve also as a 'heart beat.' Implement this using 
   _/feedback/version_ DONE. Reads version number from _application.properties_
2. comment  
   POST: Takes a URL '{"url": <URL>}' and returns the comment form: /feedback/form.
   For the response, use /feedback/add. This is partly working. The response returns a jsp page.
   I still have to code the form. 
3. info  
   GET: Return information from the comment database for the given _url_. Use /feedback/read
   
  
