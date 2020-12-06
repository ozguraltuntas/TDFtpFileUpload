package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.TimerTask;

public class ProcessStartPoint extends TimerTask{

    private static Logger logger = LogManager.getLogger(ProcessStartPoint.class);

    void UnzipFileToSameFolder(String zipFilePath, String destDirectory){
        UnzipUtility unzipper = new UnzipUtility();
        try{
            unzipper.unzip(zipFilePath, destDirectory);
            logger.info("Zip file path: " + zipFilePath + " extract this path " + destDirectory);
        } catch (Exception ex){
            logger.error("ex.printStackTrace()");
            ex.printStackTrace();
        }
    }

    void ProcessStartPoint(){
        String zipFilePath = "C:/Projects/AppWideShared/Us/Td/tdpartsmart.zip";
        String destDirectory = "C:/Projects/AppWideShared/Us/Td/tdpartsmart";

        UnzipFileToSameFolder(zipFilePath, destDirectory);
        FilesUploadToFTP filesUploadToFTP = new FilesUploadToFTP(destDirectory);
    }

    @Override
    public void run() {
        ProcessStartPoint();
    }
}
