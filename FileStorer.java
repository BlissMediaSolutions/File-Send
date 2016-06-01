
/**
 * FileStorer listens on a particular port, to receive an encrypted file from the client, 
 * which it then decrypts.  After decrypting file, it performs a 'Cleanup' & delete's the encrypted file.
 * 
 * Code Source for File Transfer:
 * https://gist.github.com/CarlEkerot/2693246
 * 
 * Code Source for Encryption:
 * http://www.codejava.net/coding/file-encryption-and-decryption-simple-example
 * 
 * Code Source for returning the File size as Long
 * http://www.tutorialspoint.com/javaexamples/file_size.htm
 * 
 * Additional Code used from COS20012 Lab Sessions.
 * 
 * NOTE: This program is limited to transferring a file of approx 50MB in size.
 * 
 * @author Danielle Walker 
 * @version 21/5/2015
 */
import java.util.regex.Pattern;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.InputStream;
import java.net.Socket;
import java.io.File;
import java.io.*;

public class FileStorer extends Thread {
    
    private ServerSocket ss;
    public String filename;
    static String key = "Mary has one cat";
    
    public FileStorer(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
                System.out.println ("Connection Established...");
                saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        int filesize;
        int encryptfilesize;
        String clientSentence;
        DataInputStream dos = null;
        FileOutputStream fos = null;
                     
        //Receive the filename + size
        dos = new DataInputStream(clientSock.getInputStream());
        clientSentence = dos.readUTF();
        
        //Split the received filename & size, and parse the size back to an Integer
        String[] splitString = clientSentence.split(Pattern.quote("|")); 
        String newSize = splitString[1];
        String tmpEncFileSize = splitString[2];
        filesize = Integer.parseInt(newSize);
        encryptfilesize = Integer.parseInt(tmpEncFileSize);
        String EncryptedFilename = splitString[0];                                      
                
        //Create a new FileOutputStream & save it to the current directory
        fos = new FileOutputStream(EncryptedFilename);
        byte[] byteArray = new byte[encryptfilesize];
        
        int read = 0;
        int totalRead = 0;
        int remaining = encryptfilesize;
        
        //Receive the data of the FileInputStream & write it to the file.
        while((read = dos.read(byteArray, 0, Math.min(byteArray.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(byteArray, 0, read);
        }
        System.out.println ("File Transfer Successful...");
        System.out.println ("Encrypted file received: " + EncryptedFilename);
        
        //Create file Object of the Encrypted file
        File encryptedFile = new File(EncryptedFilename);
        
        //Split the Encrypted Filename, so we can remove "encrypted", and create a Decrypted File object
        String[] thisSplit = EncryptedFilename.split(Pattern.quote("."));
        String tmpName = thisSplit[1] + "." + thisSplit[2];
                
        //Create a Decrypted File Object to hold the decrypted file;
        File decryptedFile = new File(tmpName);
                
        //Try to decrypt the file.
        try {
                CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
                System.out.println ("Successfully Decrypted file...");
                System.out.println ("The new file is: " + decryptedFile.getName());
            
                //If an exception occurs during decryption, output a message
            } catch (CryptoException cEX) {
                System.out.println(cEX.getMessage());
                cEX.printStackTrace();
            }
        
        //Get the Path of the Encrypted file so we can delete it.    
        String tmpPath = encryptedFile.getAbsolutePath();
               
        //Close the FileInputStream & DataInputStream
        fos.close();
        dos.close();
        
        //Delete the un-needed encryption file.  
        boolean status = encryptedFile.delete();
        if (status)
            System.out.println ("Performing cleanup...");
        else
            System.out.println ("Opps, an error occured trying to deleted the Encrypted file...");
        
    }
    
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        
        FileStorer fs = new FileStorer(port);
        fs.start();
    }

}
