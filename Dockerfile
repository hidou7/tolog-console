FROM openjdk:11.0.16
ADD tolog-console.jar /tolog-console.jar
EXPOSE 19123
CMD java -jar tolog-console.jar