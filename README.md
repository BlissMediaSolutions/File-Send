#### Title:
File-Send

#### Purpose:
A simple Java program which Ecrypts a file with AES 128bit, then transfers it over the network using TCP & decrypts on the receiving end.

#### Date Developed:
22/5/2016

#### To Run This Project:
First start up the Server (receiver / listener) end from the Command line via;
`java FileStorer xxxx` where xxxx is the Port Number
Then start up the client (sender) from the command line & specify the file to encrypt/transfer, the IP of the Server & the Port the Server is listening on; 
'java SendFile aaa.jpg 192.168.0.0 xxxx` where aaa.jpg is the file to send, 192.168.0.0 is the IP address of the Server, and xxxx is the Port the Server is listening on 

#### Authors:
Danielle Walker

#### Additional Notes:
Developed / compiled for Java SE Runtime 1.8.0.77
Package is for the BlueJ IDE
