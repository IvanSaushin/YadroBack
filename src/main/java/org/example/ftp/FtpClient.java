package org.example.ftp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class FtpClient {

    @Value("${ftp.server}")
    private String server;

//    @Value("ftp.port:21")
    @Value("${ftp.port:21}")
    private int port;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.password}")
    private String password;

    private FTPClient ftp;

//    @PostConstruct
//    private void init() throws IOException {
//        open();
//    }

    Object synsc = new Object();

    AtomicBoolean active = new AtomicBoolean(false);


    // constructor

//    @EventListener(ApplicationReadyEvent.class)
//    public void initTest() throws IOException {
//        System.out.println("ftp user = " + user);
//    }

    void open() throws IOException, InterruptedException {
        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        Thread.sleep(100);
        ftp.connect(server, port);

//        ftp.setFileType(FTP.BINARY_FILE_TYPE);
//        ftp.enterLocalPassiveMode();
//        ftp.setRemoteVerificationEnabled(false);

        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftp.login(user, password);
    }

    void close() throws IOException {
        ftp.disconnect();
    }


    public InputStream download(String path) throws InterruptedException, IOException {
//    public byte[] download(String path) throws InterruptedException, IOException {
//        InputStream is = null;
        System.out.println(active.get());
        while (active.get()) {
            System.out.println("wait");
            Thread.sleep(500);
        }
//        Thread.sleep(1000);
        active.set(true);

        try {
            System.out.println("обращение в ftp по адресу: /" + path);

//             active.set(false);
             open();
//            FTPClient ftpClient = new FTPClient();
//            ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            ftpClient.connect(server, port);
//            ftpClient.login(user, password);

            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            ftp.setRemoteVerificationEnabled(false);

//                System.out.println("обращение в ftp по адресу: /" + path);

//            InputStream is = ftp.retrieveFileStream("/"+path);
                InputStream is = ftp.retrieveFileStream("/" + path);

//                System.out.println("is retrieve? " + is);
//                return IOUtils.toByteArray(is);

                return is;
            } catch (IOException e) {
//                e.printStackTrace();
                active.set(false);
                throw new RuntimeException(e);
//                return null;
        } finally {
            close();
            active.set(false);
            }

        }

//        close();


    public void download2(String path) throws IOException, InterruptedException {
        open();

//        ftp.setFileType(FTP.BINARY_FILE_TYPE);
//        ftp.enterRemotePassiveMode();
        ftp.enterLocalPassiveMode();
//        ftp.enterLocalActiveMode();
        ftp.setRemoteVerificationEnabled(false);

//        ftp.configure(new FTPClientConfig("UseRemoteHostAddressForPassive", true));


//        String remoteFile1 = "/img2.png";
//        String remoteFile1 = "/img2.png";
        System.out.println("обращение в ftp по адресу: " + path);

        ftp.getStatus("/" + path);

        String filename = "test1.txt";
        FileOutputStream fos = new FileOutputStream("1_"+filename);
//        ftp.retrieveFile("/" + filename, fos);
        Boolean retr = ftp.retrieveFile("/test1.txt", fos);
        System.out.println("is retrieve? "+ retr);

        System.out.println(Arrays.asList(ftp.listNames()));


        System.out.println(".retr");
        System.out.println(ftp.retr("/test1.txt"));
        System.out.println(".restFileSys");
        System.out.println(ftp.retrieveFileStream("/test1.txt"));
        System.out.println(".remoteRetrieve");
        System.out.println(ftp.remoteRetrieve("/test1.txt"));

//        InputStream is = ftp.retrieveFileStream("/"+path);
//        System.out.println(is);

        close();

//        byte[] bytes = IOUtils.toByteArray(is);
//        return bytes;

//        return ;
    }
}
