# Multi-threaded Client Server File Sharing
The file sharing clients will connect to a central server, which will respond to a single client command, and
then disconnect. Each time the client needs to issue another command, it will re-connect before sending
the command. 

Each incoming client connection is handled with a separate thread.

#### Screenshot:
![screenshot](https://cloud.githubusercontent.com/assets/1751112/24529909/8609f294-157c-11e7-8e73-e9717891d4f7.JPG)

### Instructions:

#### Pull:
    * https://github.com/adwansyed/Client-Server-FileSharingSystem.git
	
#### Setup Modules:
	* Client module - client.Main 
	* Server module - server.Server
	
#### Main Success Scenario:
	* Run Server module to listen for incoming clients.
	* Run Client module
	* Select file from Client and click upload to share file on Server
	* Select file from Server and click download to share file on Client. 
	* Repeat
	* Exit through menu or accelerator CTRL+E
