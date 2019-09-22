FROM appinair/jdk11-maven
CMD cd /app/ && mvn compile exec:java -Dexec.mainClass="disc0rd.Disc0rd"
