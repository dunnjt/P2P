# 490Final

John Madsen

John Dunn

4/12/2016

Execution instructions for Client/server RDT


To run the client-server on one machine using loopback address 127.0.0.1:

	•	no ports need to be forwarded

	•	Run pkg490final.network.MainLoopback.java

	•	This class has a main method with the loopback address 127.0.0.1 hard coded to forward to the client class to be used to send packets.

To run the client-server on one machine using your public IP address:

	•	ports 33000 and 49000 need to be forwarded in the router to your local IP address.
	
	•	Run pk490final.network.Main.java

	•	This class has a main method that will detect your public IP address and forward that to the client class to use to send packets

To run the client-server on two separate machines on same network using public IP:

	•	forward port 33000 for the client and run pkg490final.network.Client.java

	•	forward port 49000 for the server and run pkg490final.network.Server.java

To run the client-server on two separate machines on different networks using public IP:

	•	In pk490final.network.Client.main change the parameters of the method call start(pkg490final.PacketUtilities.getPublicIP())to the string of 	the servers public IP.

	•	forward port 33000 for the client and run pkg490final.network.Client.java

	•	forward port 49000 for the server and run pkg490final.network.java


On sender side for any instance, when prompted, type in a directory that holds multiple files in order to test data being split into packets and reconstructed on other end.

If you would like the server to continue running after the last packet is received from the sender, remove line 81 (stopListening();) from RDT20Receiver.java 