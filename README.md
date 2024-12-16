
# Digital Course Marketplace

A Kotlin-based Spring Boot application that allows users to browse and manage digital courses. This project is containerized using Docker.


## Technologies Used

- Kotlin
- Spring Boot
- H2 DB
- JWT (for security)
## Installation and Setup

### Prerequisites:
Before you begin, make sure you have the following installed on your local machine:

- Java Development Kit (JDK) 21
- Maven

#### Steps:

- #### Clone the Repository:
        git clone https://github.com/jishnu7561/Digital-Course-Marketplace.git

        cd digital-course-marketplace
- #### Build the Application
         mvn clean package
- ####  Docker Setup
        build : docker build -t digital-course-marketplace .
        run: docker run -p 8080:8080 digital-course-marketplace


  
