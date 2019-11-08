
**Setup**

 - Import project as maven project using any ide
 - Project can be run by Running main class TelecomApplication.java
 - Project will start on port 7080

**Steps to run**
 - mvn clean install
 - java -jar haud-telecommunication-operator-exec.jar
 
**Project Url**

 - base url: localhost:7080/haud/telecom
 - customer create url(method:post): localhost:7080/haud/telecom/customer
 Body: {
	     "email" : "abc@g.c",
	     "name" : "test"
       }
 - customer get url (method:get):localhost:7080/haud/telecom/customer/1
 - sim card url: localhost:7080/haud/telecom/simcard
 - Link simcard to specific customer(method:put): localhost:7080/haud/telecom/customer/1
 
**Database**

-  Database console: http://localhost:7080/haud/telecom/h2-console

**Points taken care**

- Code quality
- Naming convention
- Test coverage
