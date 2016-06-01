##### Title:
File-Send

##### Purpose:
A simple Java program which Ecrypts a file with AES 128bit, then transfers it over the network using TCP & decrypts on the receiving end.

##### Date Developed:
22/5/2016

##### To Run This Project:
First start up the Server (receiver / listener) end from the Command line via;   
```java FileStorer xxxx``` where *`xxxx`* is the Port Number   
Then start up the client (sender) from the command line & specify the file to encrypt/transfer, the IP of the Server & the Port the Server is listening on;    
`java SendFile aaa.jpg 192.168.0.0 xxxx` where *`aaa.jpg`* is the file to send, *`192.168.0.0`* is the IP address of the Server, and *`xxxx`* is the Port the Server is listening on 

##### Authors:
Danielle Walker

##### Additional Notes:
- Developed / compiled for Java SE Runtime 1.8.0.77   
- Package is for the BlueJ IDE   
- AES Encryption uses a predefined 16byte Key specified in the Code.
- Due to Java VM Memory limits, it will transfer a max file size of approx 50MB (most likely less than 50MB though)


==========================
"File-Send" Copyright (C) 2016, Danielle Walker

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. 

