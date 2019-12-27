API - /traffic-incidents GET requestParam=mapArea (Specifies the area to search for traffic incident information.)

Description:
The API is internally calling three APIs to retrive the traffic incidents with weather information. ForkAndJoin along with Java 8 Parallel stream is being used in implementation to call the externals APIs asynchroniously for better performance.

The three external APIs are:
traffic-incidents: http://dev.virtualearth.net/REST/v1/Traffic/Incidents/{mapArea}
geographic: https://www.metaweather.com/api/location/search/
temperature: https://www.metaweather.com/api/location/{woeId}

Technologies: Java 1.8, Spring Boot
DevOps: Maven, Git

Source Code: https://github.com/hackrepo/traffic-incidents

Steps to build, deploy and test:
1. Clone the project and go to the project folder in CMD.
2. Execute the command "mvn clean install" which will create a Jar file of the above source code (As it is a Spring Boot based project, Internal tomcat is being to deploy this web application)
3. Run the jar file by this command: "java -jar target\{project-name}.jar [Replace the project name in place holder]
   It will deploy the application with the Spring Boot based default tomcat server with post 8080
   
 
 Call the API to test
 http://localhost:8080/traffic-weather?mapArea=37,-105,45,-94
