# A Leatherback Integration Service 

# Purpose 

An Integration Service to Leatherback for GBP and Canada Collection for a multi-tenant 
use as created payment links are attached to external ids which is attached to a callback url.

This callback URL is trigged when Leatherback sends a webhook event, and in turn the corresponding
alias is selected from the database.

A Retry Policy is in place for callback failures for up to a configured number of tries

````
api.retry=${LEATHERBACK_API_RETRY}
````

# Enironmental Variables

````
api.key=${LEATHERBACK_API_KEY}
````

# Stack

![](https://img.shields.io/badge/mysql-✓-blue.svg) 
![](https://img.shields.io/badge/java_17-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/acuator-✓-orange.svg)
![](https://img.shields.io/badge/swagger_3-✓-green.svg)

The following Technology stack was used in this project :

**Java 22** : Core language used

**SpringBoot 3.2.3** :

**MySql** : Database access

# Steps To Run

**1** - Do a git clone of the project : 

**2** - Open application.properties , set the default profile to run { dev / live / uat }

**3** - Edit the configuration of application-{profile} to suit the appropriate environment eg port , database url , database username & password

**4** - Run the project from the IDE 


# Steps To Development

**1** - For every controller or resource created identify testable units and test them appropriately in any of the test classes JpaTests, IntegrationTests or ApplicationTests



# Service Documentation is via Spring-doc Swagger URL

Swagger is used to expose the rest Endpoints via the following link : 

**http://{host}:{port}/api/swagger-ui.html** 


**NOTE** :  you need java installed on your environment to be able to run this project.

