# Log Paser

Run:
mvn package && java -jar file /log_example.log

To solve the question, I used:
* Spring-Boot 
* Factory design pattern for choosing the input type (http or file for now)
* Depedency deisgn pattern for injecting the beans
* Univocity parser to parse the file while skipping and parsing desired column only (cs-host)
* HashMap with MutableInt to reduce redudant call to put
* Sorting list
