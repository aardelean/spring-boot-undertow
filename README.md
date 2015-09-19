# spring-boot-undertow
Fast project exemplifying different utilities from spring-boot. 
This project is meant as a template for future microservices projects, deleting what is not necessary.

Needs: JDK 8, Maven (3).

Technologies used: 
 - spring boot
 - undertow
 - mysql
 - mongoDb with async driver
 - JPA 2.1 with hibernate
 - infinispan caching (maybe the fastest grid cache)
 - jpa data rest (exposing database to rest directly, very fast and easy)
 - spring cache wrapper over infinispan (useful for when some information rarely changes, often read. One minute lifetime)
 - jpa level 2 cache cache wrapper over infinispan (useful for caching in cluster of servers, to ease up mysql load)
 - spring controller (custom web operations)
 - async connectors to io.
 
 Example publishes a few endpoints. I also did tests with jmeter for all over endpoints. 
 The tests have the result in responses per second.
 The setup: jmeter, server, mongo, mysql all running on the same machine,
 intel xeon E31235 8 core 3.2 Ghz, 8 GB DDR3 - 2000MHZ:
 - blocking  
    - mysql
        - pool of connections  - 3.300 
        - without pool of connections   - 650
        - mysql jpa level 2 cache   - 4.000 (could prove more useful in cluster than just one server)
         - http://localhost:8080/employees/1
        - mysql spring cache   - 6.000 (not much mysql since spring caches and serves back based on the parameters)
         - http://localhost:8080/blocking/employee/cached/1
    - simple json serving - 10.000
      - http://localhost:8080/blocking/json
    - static file serving - 1.275 (I believe the jmeter is not properly configured as it never varies here in the tests, 
    and the result seems to be way to low compared to others)
      - http://localhost:8080/blocking/index
  - non blocking (nio)
    - mongo db - 10.000
      - http://localhost:8080/mongo
    - mysql 
        - pool of connections - 5.200 (mysql connections are still blocking, the difference is that the connection to
         the server is non-blocking, however the big difference is not explainable for me. I was expecting around 3.000-4.000, 
         not 5.200).
          - http://localhost:8080/nio/employee
        - no connection pool - 650
        - spring cache - 12.000 (rarely refreshes from mysql, once a minute expires the cache)
          - http://localhost:8080/nio/caching/employee
    - static file serving - 1275 (same jmeter problem probably, to low numbers here)
      - http://localhost:8080/file/index.html
    - json serving - 12.000.
      - http://localhost:8080/json
    
    
