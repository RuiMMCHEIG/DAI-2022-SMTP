# SMTP Server setup

In this part, we will see how to install a SMTP Server in a Docker container, in this case this will be a MockMock Server(https://github.com/DominiqueComte/MockMock)


## Prerequisites
There are multiple prerequisites before installing the server:

- You need to have Docker (Desktop or WSL), follow these instructions to install it (https://docs.docker.com/get-docker/)

- Install "openjdk" from the docker hub (https://hub.docker.com/_/openjdk) using the command (in terminal): docker pull openjdk

 
## Docker

### Configuration


Once openjdk is installed, we will configure our Docker image using our Dockerfile and .jar file "*MockMock-1.4.1-SNAPSHOT.one-jar.jar*".

These files are located in the "*Docker*" folder.

The Dockerfile is already prepared and you don't need to modify it except if you want to change ports numbers for SMTP/Web Interface, check [Change ports](#change-ports)


### Build

Once the Dockerfile is ready, you will need to open a terminal(cmd) and execute theses commands:

Access the Docker folder:
- cd pathToTheDockerFolder

Build the Docker image:
- docker build -t mockmockserver ./

The results should be like this:

 ![](https://i.imgur.com/ND3Cjeg.png)
 
### Run

At this stage, the mockmockserver Docker image has been created. Now we need to use the Docker run command to create a new container based on our Docker image.

In a terminal, execute this following command:
```
docker run -p 8282:8282 -t mockmockserver
```


If everything is working correctly, you should see something like this:

**In terminal:**

![](https://i.imgur.com/XoiFqJO.png)


### How to check Docker images & containers:

**In terminal:**
Use the command ```docker image ls```:

![](https://i.imgur.com/RJefXvz.png)


In Docker desktop(if you have this version of Docker):
**Containers:**

![](https://i.imgur.com/fAFSVs9.png)

**Images:**

![](https://i.imgur.com/xQ91Whp.png)




Now, the MockMock server is running and can be used !

## MockMock

This section contains some useful information about MockMock.

Basically, the default port 25 is used for the SMTP part and port 8282 for the web interface. It is important to note that on some systems, root permissions are required to listen on port 25. 

### Change ports
If port 8282 is already in use on your machine, it is also possible to change this in the Dockerfile configuration.

In the Dockerfile, replace these line with new ports numbers:
```
EXPOSE newWebInterfacePort 
EXPOSE newSmtpPort
ENTRYPOINT ["java", "-jar", "MockMock-1.4.1-SNAPSHOT.one-jar.jar", "-p", "newSmtpPort", "-h", "newWebInterfacePort"]
```
Don't forget to only use unsused ports to avoid any issues.

After every changes in the Dockerfile, you will need to rebuild the image. Follow instructions from [Docker Image Build](#build)

And then, in a terminal, execute this following command to run the container:

Change NewWebInterfacePort with the port number you want.
```
docker run -p NewWebInterfacePort:NewWebInterfacePort -t mockmockserver
```


