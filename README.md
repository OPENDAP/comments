
# Simple Spring Boot web application

# Builds with maven
use _./mvnw package_ to build

and _java -jar target/demo-0.0.1-SNAPSHOT.jar_ to run.

# Builds with gradle
use _gradle build_ to build

and _gradle bootRun_ to run

The _bootRun_ task runs the web app in an embedded instance of Tomcat. The _curl_ calls in **Testing** work for both.

# Testing
There are two API calls, one that uses GET and one that uses POST

## Test the GET call

use curl to access the URL _http://localhost:8083/welcome/user_

The returned message can be altered using the query string parameter _name_
using

_curl 'http://localhost:8083/welcome/user?name=James'_

## Test the POST call

use curl to post the information for a StudentRegistration bean. The (json) info looks like:

{
    "name": "James",
    "age": 102
}

using the _-d @<filename>_ option of curl and the _-H_ option to add the _Content-Type:_ header value _application/json_. The post data is in _src/test/resources/post_doc_1.json_

_curl -H 'Content-Type: application/json' -d @src/test/resources/post\_doc\_1.json http://localhost:8083/register/student_

# TODO

- [x] Move from maven to gradle  
  Easy to do: _gradle init_ makes the needed stuff, edited to update and added spring-boot plugin.
- [ ] Compare this to a Spring MVC web application
- [x] Design Web API  
  See below
- [ ] Rename to _feedback_
  
## Web API Entry points:
1. version  
   GET: Return the version of the service. This will serve also as a 'heart beat.' Implement this using 
   _/comments/version_
2. comment  
   POST: Takes a URL '{"url": <URL>}' and returns the comment form: /comments/form.
   For the response, use /comments/add
3. info  
   GET: Return information from the comment database for the given _url_. Use /comments/read
   
   
