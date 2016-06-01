
/**
 * Send File, is passed arguments for IP Address, port & file name.  It then encrypts the file & 
 * tranfers it to the Server at the specified IP Address & port.
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
 * @version 22/5/2016
 */
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.File;

public class SendFile {
    
    public long thisfilesize;
    public long thisencryptfilesize;
    public String filesize;
    public String encryptFileSize;
    private Socket s;
    public static String filename;
    static String key = "Mary has one cat";
    
    public SendFile(String host, int port, String file) {
        try {
            s = new Socket(host, port);
            sendFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    //Function to get the File Size, and return a Long type
    public static long getFileSize(String filename) {
      File file = new File(filename);
      if (!file.exists() || !file.isFile()) {
         System.out.println("File doesn\'t exist");
         return -1;
      }
      return file.length();
   }
    
    public void sendFile(String file) throws IOException {
        String encryptedFileName;
        File encryptedFile = new File("encrypted."+ filename);
        encryptedFileName = encryptedFile.getName();
        File inputFile = new File(filename);
        
        //Encrypt the file and add to the encryptedFile object
        try{
               CryptoUtils.encrypt(key, inputFile, encryptedFile);
                   
           } catch (CryptoException cEX) {
               System.out.println(cEX.getMessage());
               cEX.printStackTrace();
           }
        
        
        //First Send the Name + FileSize
        thisfilesize = getFileSize(filename); //File Size of un-encypted file is long type
        thisencryptfilesize = getFileSize(encryptedFileName); //File Size of Encryopted file
        filesize = String.valueOf(thisfilesize); //convert the filesize to a string
        encryptFileSize = String.valueOf(thisencryptfilesize); //convert encrypted file size to string
        
        //Create a new Dataoutput Stream
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        
        //Create a new FileInput stream
        FileInputStream fis = new FileInputStream(encryptedFile);
      
        byte[] byteArray = new byte[(int) encryptedFile.length()];
        
        //Send the File name + size
        String newString = encryptedFile.getName() + "|" + filesize + "|" + encryptFileSize;
        dos.writeUTF(newString);
        dos.flush();
        
        System.out.println("Now sending file: " + encryptedFileName);
        
        //Now Send the file
        while (fis.read(byteArray) > 0) {
            dos.write(byteArray);
        }
        
        //Get the path of the Encrypted file to perform a cleanup
        String tmpPath = encryptedFile.getAbsolutePath();
        
        //Close both File Input & Data Output Streams
        fis.close();
        dos.close();
        
        //Delete the un-needed encryption file.  
        boolean status = encryptedFile.delete();
        if (status)
            System.out.println ("Performing cleanup...");
        else
            System.out.println ("Opps, an error occured trying to deleted the Encrypted file...");
    }
    
    public static void main(String[] args) {
        filename = args[0];
        String ipAddress = args[1];
        int port = Integer.parseInt(args[2]);
        SendFile fc = new SendFile(ipAddress, port, filename);
    }

}
