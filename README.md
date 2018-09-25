# Playground Management Library
This is a playground utility library to perform general operations like 
adding equipment/sites to playground, adding kids to playground(normal/vip feature 
to skip queue by 3 normal kids) and calculate no of visitors , utilization snapshot to name a few. 

## Overview of the Library 
The library supports loading sites and kids in bulk using CSV or single entry via different API endpoints.

The Library is build using Spring boot 2.0.5 release and supports accessing the functionality via REST API. 

The Library can be also be accessed by normally adding as a maven dependency using local repo and
wiring the Playground service as a spring dependency injection. 

A simple UI is provided and can be accessed at [http://localhost:8080/index.html](http://localhost:8080/index.html). Follow the steps on the link 
to perform the operation on the library.

Alternately API can be accessed via Swagger UI at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html), to understand the 
controller exposed for different API functionality. 

API supports multithreading for any bulk operations such the start play kids which allows simultaneous 
operation such as enrolling kid into the site, add kids to the queue if site is full, if kid is VIP then 
allow skip queue by 3 normal entries. 

The is a spring scheduler which continuosly monitors the play site management system for any new entries,
utilization % , time spent by each kid at the site and site used informations. This is seamless to the user 
happens as a background process without intefering normal API operations. 
  
## Prerequisites
   - Windows/Linux Operating System, Java 8, Maven 3, Java IDE(Eclipse/IntelliJ Idea).   

### Getting Started
   
   - To setup this project and start running, clone the github url/extract the zip on your local workspace. Import into a IDE of your choice(Eclipse/IntelliJ Idea). 
   
   After downloading/cloning project into a local IDE, run the below command.
   
   `$ mvn clean install spring-boot:run`
   
   The above command generate necessary artifacts target/ directory and run the unit tests against the rest controller. 
   Once the application is started you can use the url http://localhost:8080/index.html to upload the [CSV to upload](./csv) at root path.
   
   Step 1: Upload csv for sites, [CSV to upload](./playground_sites.csv) and click on submit. check if the new 
   page returns true. 
   
   Step 2: Upload csv for sites, [CSV to upload](./playground_kids.csv). Check if the new page 
   returns true
    
   Step 3: Press the Start Play button to enroll kids to the sites and begin the play, queue etc.
   
   Step 4: Get Total visitor count button, to check the total number visitor accessed by the system.
   
   Step 5: Get the Site Utilization snapshot at the configured interval. Currently this is returned as JSON data.
   
### API Details. 
    Mainly there are 8 endpoints on this application. Details as below: 
    1. To upload the site data in bulk using csv.  
         - POST /playground/upload-sites    
    2. To upload the kids information into the system in bulk.
         - POST /playground/upload-kids
    3. To add the site information as single entry
         - POST /playground/add-site
    4. To add the kid information as single entry
         - POST /playground/add-siteuser
    5. To start the operation of the library, enroll kids to sites for play execution, queueing up 
       kids in the sites, backkground process running analytics such as visitor count, site utilization
        snapshots. 
         - GET playground/start-play
    6. Fetch the total visitor count.
         - GET /playground/visitors-count
    7. Fetch the site utilization snapshot.
         - GET /playground/utilization-snapshot
    8. Fetch the site visited by each kid and time spent at each site. 
         - GET /playground/duration-per-site-by-kids           
    9. Fetch the active kids count for each site. 
         - GET /playground/active-kids
    10. Fetch the waiting kids count for each site. 
         - GET /playground/active-kids              
         
#### Analytics 
The Library supports analytics like site utilization, kid duration at each site, total visitor count and Active/Waiting kids on the site runtime information. The links can be accessed via 
the main page [http://localhost:8080/index.html](http://localhost:8080/index.html). 

To analytics information can be accessed below: 
1. Active/Waiting kids on the site. [http://localhost:8080/kids_report.html](http://localhost:8080/kids_report.html)
2. Site Utilization snapshot. [http://localhost:8080/utilization.html](http://localhost:8080/utilization.html)
3. Duration kid spent per site basis. [http://localhost:8080/duration.html](http://localhost:8080/duration.html)

Kindly note that the links will contain relavent data only when the application is initialised with site and kid data and start playing. 

#### Author

   - Anupam Shrivastava 
   - shanupam@gmail.com

#### License
   NA

####  Built With
   Java 8, Maven 3, Spring boot, Junit, Swagger-UI

#### Resources 
   [Site Data CSV file sample:](./playground_sites.csv)
   [Kids/Site User Data CSV file sample:](./playground_kids.csv)