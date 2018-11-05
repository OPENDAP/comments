
/// The Model in the MVC example

package org.opendap.beans;

// This class comes from a Spring MVC example that uses JSP to build a 
// HTML form.
public class FeedbackData {
   private Integer age;
   private String name;
   private Integer id;

   public void setAge(Integer age) {
      this.age = age;
   }
   public Integer getAge() {
      return age;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getName() {
      return name;
   }
   public void setId(Integer id) {
      this.id = id;
   }
   public Integer getId() {
      return id;
   }
}
   