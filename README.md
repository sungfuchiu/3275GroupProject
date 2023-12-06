# 3275GroupProject
## Introduction
The Tech Assist Application seeks to bridge the gap between individuals facing immediate household or automotive issues and skilled technicians. By leveraging video calls, customers can connect with technicians for guidance on mechanical repairs or consultations. This introduction provides a brief background of the company's motivation behind developing the Tech Assist Application.
## Technologies
- Spring Boot
  - thymeleaf
  - JPA
- WebRTC
- Socket.IO
- MySQL
- NGINX
- Docker

## How to deploy
1) **Get local IP**

We can get our IP by calling ipconfig in command line.

2) **Get SSL certificate**

We can use OpenSSL downloaded with Git and using following command to create certificate.

`mkdir ssl && C:\Program Files\Git\usr\bin\openssl.exe req -x509 -nodes -days 365 -newkey rsa:2048 -keyout ssl/private_key.pem -out ssl/certificate.pem -subj "//C=US//ST=California//L=San Francisco//O=MyOrganization//OU=MyDepartment//CN=<YOUR_LOCAL_IP>"`

3)	**Set IP for nginx configuration**

We need to set our certificate for users to create HTTPS connection with out website and we need to use socketIO to help two devices connect with each other so we host nginx.
We need to set our IP to nginx.conf file.
 
4)	**Set IP for socketIO in JS**
   
In order to connect to web servers from client’s device through socketIO, we need to use JavaScript to communicate with the server, so we also need to modify the IP in videoCall.js.
 
6)	**Application properties settings**
   
Comment out the original data source which is used for local environment, and uncomment the data source below it which is used to connect to MySQL in docker.
 
7)	**Build the project in docker**

The last step is building the application in docker.

`docker-compose up -d --build`

While the application finishes building, we can connect to the application through the URL https://<YOUR_LOCAL_IP>/login.

8)	**Insert data into database**

We didn’t add dummy data into MySQL through JPA. We insert data into MySQL through SQL scripts, so we need to connect to MySQL in Docker with the following command.

`mysql -P 8083 --protocol=tcp -u root -p`

Then, we can key in the password root while the cmd quotes. When we get into the database, we can execute create_database.sql in \SQLscript folder.
