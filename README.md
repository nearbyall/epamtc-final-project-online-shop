# ONLINE SHOP final project
# Java Web Development. Group 34

## [DATA BASE INFO](https://github.com/nearbyall/epamtc-final-project-online-shop/tree/master/db) 
- [scheme.png](https://github.com/nearbyall/epamtc-final-project-online-shop/blob/master/db/scheme.png)
- [script.sql](https://github.com/nearbyall/epamtc-final-project-online-shop/blob/master/db/script.sql)
- [model.mwb](https://github.com/nearbyall/epamtc-final-project-online-shop/blob/master/db/model.mwb)

###### Website visitors are provided with the following services:
***
### *Guest:*
- log in by the email\password or login by remember me command next time
  if checkbox had being activated (cookie token)
- forget password function (with sending email, which contains single-use auth-token)
- registration
- view categories
- view catalog list (with pagination context)
- view catalog list by specific category (with pagination context)
- search catalog (in process)
- switch language (with saving chosen lang into the cookies) 
***
### *User:*
- switch language (with saving chosen lang into the cookies) 
- view catalog list (with pagination context)
- view catalog list by specific category (with pagination context)
- search catalog (in process)
- construct cart
- make order
- view all own orders list
- log out
- update profile data
- write reviews 
- top up balance
***
### *Admin:*
- __all user's allowed services__
- add the product and product categories
- edit products
- delete reviews (in process)
- approve or reject user's orders
- ban or unban users
- view all users list 

***
### Components used for the project:
- Java 11
- Maven
- Git
- JavaEE: Servlet, JSP, JSTL, JavaMail
- Server / Servlet container: Tomcat 9
- Database: MySQL
- JDBC
- Logger: Log4J2
- Tests: JUnit 4
- JavaDoc

***
### CREATED BY VLADISLAV MELNIKOV 2022