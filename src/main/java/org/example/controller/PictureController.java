package org.example.controller;


import org.apache.commons.io.IOUtils;
import org.example.Form;
import org.example.ftp.FtpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/picture")
@CrossOrigin(origins = "http://localhost:5500", maxAge = 3600)
public class PictureController {

    @Autowired
    FtpClient ftpClient;

    @GetMapping()
    public ResponseEntity<Form> getAdmin() {
        List<String> users = new ArrayList<>();
        Form form = new Form("Ivan", "9999999", "email");
        System.out.println("Поступил GET запрос на /api/form");
        System.out.println(form);
        return ResponseEntity.ok().body(form);
    }

    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        System.out.println("Поступил GET запрос на /picture/image");

//        InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
//        byte[] media = IOUtils.toByteArray(in);

//        String imagePath = "";
//        byte[] media = Files.readAllBytes(Paths.get(defaultImageFile.getPath()));

//        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource("/img/img2.png").getPath());

        File file = ResourceUtils.getFile("classpath:img/img2.png");
        InputStream in = new FileInputStream(file);
        byte[] media = IOUtils.toByteArray(in);

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);

        ResponseEntity response = ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .headers(headers)
                .body(media);

//        return responseEntity;
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public byte[] testPhoto() throws IOException {

        File file = ResourceUtils.getFile("classpath:img/img2.png");
        InputStream in = new FileInputStream(file);
        byte[] media = IOUtils.toByteArray(in);

//        Base64.getEncoder().encode(media);
//        return media;

        return Base64.getEncoder().encode(media);
    }

    @ResponseBody
    @RequestMapping(value = "/ftp/{value}", method = RequestMethod.GET)
    public byte[] testPhoto2(@PathVariable(value = "value") String path) throws IOException, InterruptedException {

//        File file = ResourceUtils.getFile("classpath:img/img2.png");
//        InputStream in = new FileInputStream(file);
//        byte[] media = IOUtils.toByteArray(in);

//        Base64.getEncoder().encode(media);
//        return media;
        Thread.sleep(100);

        return Base64.getEncoder().encode(IOUtils.toByteArray(ftpClient.download(path)));
    }

    @ResponseBody
    @RequestMapping(value = "/ftp2/{value}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> testPhoto3(@PathVariable(value = "value") String path) throws IOException, InterruptedException {

        byte[] media = IOUtils.toByteArray(ftpClient.download(path));
//        ftpClient.download(path);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(media);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initTest() throws IOException {
        File file = ResourceUtils.getFile("classpath:img/img2.png");
        InputStream in = new FileInputStream(file);
        byte[] media = IOUtils.toByteArray(in);

        System.out.println(file.exists());
        System.out.println(file.getName());


    }

}
