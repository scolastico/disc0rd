FROM appinair/jdk11-maven
COPY . /app
RUN cd /app/ && mvn compile
CMD cd /app/ && mvn exec:java -Dexec.mainClass="disc0rd.Disc0rd"
