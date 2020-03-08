package za.co.bvr.forth.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import lombok.extern.java.Log;

/**
 *
 * @author nicm
 */

@Log
public class FileUtils {
      
    public StringBuffer readFile(String fileIoFileName) throws Exception {
        int eofCharacter = -1;
        char c;
        InputStream in;
        String st;

        StringBuffer fileContentBuffer = new StringBuffer();
        //boolean fileOpperationFailed=false;

        try {
            System.out.println("Reading File " + fileIoFileName);
            URL newURL = this.getClass().getClassLoader().getResource(fileIoFileName);
            System.out.println("url : " + newURL);
            in = newURL.openStream();

            do {
                c = (char) in.read();
                fileContentBuffer.append(c);
            } while (c != eofCharacter); //EOF -1
            in.close();
        } catch (IOException e) {
            
            log.log(Level.SEVERE, "Failed to read file:" + fileIoFileName,e);
            throw new Exception("Failed to read file:" + fileIoFileName, e);

        }

        return fileContentBuffer;
    }
    
    public String lineReadFile(String fileSpec){
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fis = null;
        byte[] buffer = new byte[10];  
        
        try {
            fis = new FileInputStream(fileSpec);
            
            while (fis.read(buffer) != -1) {
                stringBuilder.append(new String(buffer));
                buffer = new byte[10];
            }            
            
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "FileNotFoundException caught while trying to read file : " + fileSpec,ex);
            
        } catch (IOException ex) {
            log.log(Level.SEVERE, "IOException caught while trying to read file : " + fileSpec,ex);
            
         }finally{
            
            try {
                if(fis!=null){
                    fis.close();
                }
            } catch (IOException ex) {
                log.log(Level.SEVERE, "Failed to read file:" + fileSpec,ex);
            }            
        }
        
        return stringBuilder.toString();        
    } 
    
/*
    public void writeFile(String fileIoFileName, StringBuffer fileContentBuffer) throws Exception {
        String str = "";

        try {

            System.out.println("writing File " + fileIoFileName);
            FileWriter out = new FileWriter(fileIoFileName);

            int len = 0;

            //fileContentBuffer.append(eofCharacter);
            if (fileContentBuffer != null) {
                char[] buf = new char[fileContentBuffer.length()];
                len = fileContentBuffer.length();
                for (int j = 0; j < len; j++) {
                    buf[j] = fileContentBuffer.charAt(j);
                }

                out.write(buf, 0, fileContentBuffer.length());
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to write file:" + fileIoFileName,e);
            throw new Exception("Failed to write file:" + fileIoFileName, e);
        }
    }
*/    
    public void writeFile(String fileSpec, String fileContent) {
            FileWriter fileWriter =null;
            File file =null;
            
        try {
            file = new File(fileSpec);
            fileWriter =new FileWriter(file);
            fileWriter.write(fileContent);
                log.info("file written: "+fileSpec);
            
        } catch (IOException ex) {
            log.log(Level.SEVERE, "IOException to write file: " + fileSpec,ex);
            
        }finally{
            
            try {
                if(fileWriter!=null){
                    fileWriter.close();
                }
                log.info("file closed: "+fileSpec);
                
            } catch (IOException e) {
               log.log(Level.SEVERE, "IOException to close file: " + fileSpec,e);
            }
        } 
    }
/*
    public void appendFile(String fileIoFileName, StringBuffer fileContentBuffer) throws Exception {
        String str = "";
        char[] charBuffer = null;

        try {
            System.out.println("writing File " + fileIoFileName);
            FileWriter out = new FileWriter(fileIoFileName, true);

            int len = 0;
            //fileContentBuffer.append(eofCharacter);

            if (fileContentBuffer != null) {
                char[] buf = new char[fileContentBuffer.length()];
                charBuffer = buf;
                len = fileContentBuffer.length();
                for (int j = 0; j < len; j++) {
                    buf[j] = fileContentBuffer.charAt(j);
                }
                out.write(buf, 0, fileContentBuffer.length());
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to write append:" + fileIoFileName,e);
            throw new Exception("Failed to append file:" + fileIoFileName, e);
        }
    }
*/     
    public void appendFile(String fileSpec, String fileContent) {
            FileWriter fileWriter =null;
            File file =null;
            
        try {
            file = new File(fileSpec);
            fileWriter =new FileWriter(file, true);
            fileWriter.write(fileContent);
                log.info("file written: "+fileSpec);
            
        } catch (IOException ex) {
            log.log(Level.SEVERE, "IOException to write file: " + fileSpec,ex);
            
        }finally{
            
            try {
                fileWriter.close();
                log.info("file closed: "+fileSpec);
                
            } catch (IOException e) {
               log.log(Level.SEVERE, "IOException to close file: " + fileSpec,e);
            }
        } 
    }
    
    void deleteFile(File newFile) {
        if (newFile.delete()) {
            log.info(newFile.getName() + " is deleted!");
        } else {
            log.info("Delete operation is failed.");
        }
    }
}
