package org.example;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilesUploadToFTP {
    private static Logger logger = LogManager.getLogger(FilesUploadToFTP.class);

    public void FTPUpload(String firstRemoteFile, String firstRemoteFilePath) {
        String server = "ftp.moontech.co";
        int port = 21;
        String user = "techdata";
        String pass = "Moontech@1";
        logger.info("    FTPUpload starting ...    ");

        FTPClient ftpClient = new FTPClient();
        logger.info("    FTPClient ftpClient = new FTPClient();    ");

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(firstRemoteFilePath);
            InputStream inputStream = new FileInputStream(firstLocalFile);
            logger.info("    Start uploading files...    ");

            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                logger.info("The file " + firstRemoteFile + " is uploaded successfully");
            }
        } catch (IOException ex) {
            logger.error("This is error message..." + ex.getMessage());
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    logger.info("ftpClient.disconnect()");
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                logger.info("ex.printStackTrace()");
                ex.printStackTrace();
            }
        }
    }

    public void countFiles(String destDirectory){
        int MAX_FILES = 4;
        String rootFolderPath = destDirectory + "/";

        Path dir = Paths.get(rootFolderPath);
        int i = 1;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream) {
                //larger than max files, exit
                FTPUpload(p.getFileName().toString(), rootFolderPath+p.getFileName().toString());
                if (++i > MAX_FILES) {
                    return;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return;
    }

    public FilesUploadToFTP(String destDirectory) {
        countFiles(destDirectory);
    }
}
