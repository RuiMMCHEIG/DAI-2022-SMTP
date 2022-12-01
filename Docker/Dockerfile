FROM openjdk:11
ADD MockMock-1.4.1-SNAPSHOT.one-jar.jar MockMock-1.4.1-SNAPSHOT.one-jar.jar
EXPOSE 8282
EXPOSE 2525
ENTRYPOINT ["java", "-jar", "MockMock-1.4.1-SNAPSHOT.one-jar.jar", "-p", "2525", "-h", "8282"]