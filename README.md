
# Simple Spring Boot web application

# Builds with maven
use _./mvnw package_ to build

and _java -jar target/demo-0.0.1-SNAPSHOT.jar_ to run.

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
    "name": "Abhijit!",
    "age": 25
}

using the _-d @<filename>_ option of curl and the _-H_ option to add the _Content-Type:_ header value _application/json_. The post data is in _src/test/resources/post_doc_1.json_

_curl -H 'Content-Type: application/json' -d @src/test/resources/post_doc_1.json http://localhost:8083/register/student_

# TODO

* move from maven to gradle
* compare this to a Spring MVC web application
* Design Web API
  
## Entry points:
1. version  
GET: Return the version of the service. This will serve also as a 'heart beat'
2. comment  
POST: Takes a URL '{"url": <URL>}' and returns the comment form
3. info  
GET: Return information from the comment database for the given _url_
   
   
